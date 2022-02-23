package Controller;

import Actors.Amministrazione;
import DBManagers.*;
import Entity.PrenotazioneTour;

import java.sql.SQLException;
import java.util.Scanner;

public class PrenotazioniController {

    private static PrenotazioniController prenotazioniController;
    private Scanner scannerPrenotazioniController = new Scanner(System.in);

    private PrenotazioniController(){}

    public static PrenotazioniController getInstance() {
        if (prenotazioniController == null) {
            prenotazioniController = new PrenotazioniController();
        }
        return prenotazioniController;
    }

    public boolean eseguiCreaPrenotazione(String email){
        int tentativiCoglione = 0;
        int idDisponibilita = DisponibilitaController.getInstance().selezionaDisponibilitaTurista();
        if(idDisponibilita == -1){
            return false;
        }
        int postiDaPrenotare;
        String rispostaTurista;
        System.out.println("Inserire il numero di posti che si vuole prenotare: ");
        postiDaPrenotare = scannerPrenotazioniController.nextInt();
        try {
            while (!DBManagerConfronti.verificaPerIPostiDaPrenotare(idDisponibilita, postiDaPrenotare)) {
                System.out.println("I posti da lei inseriti non sono disponibili.");
                System.out.println("Vuole ancora prenotare cambiando il numero di posti da prenotare? Inserire SI o NO");
                rispostaTurista = scannerPrenotazioniController.nextLine();
                if (rispostaTurista.equalsIgnoreCase("")) {
                    rispostaTurista = scannerPrenotazioniController.nextLine();
                }
                while (!rispostaTurista.equalsIgnoreCase("SI") && !rispostaTurista.equalsIgnoreCase("NO") && tentativiCoglione <= 5) {
                    System.out.println("Risposta non valida. Inserire SI o NO");
                    rispostaTurista = scannerPrenotazioniController.nextLine();
                    tentativiCoglione++;
                }
                if (rispostaTurista.equalsIgnoreCase("si")) {
                    System.out.println("Inserire un numero minore di " + postiDaPrenotare);
                    postiDaPrenotare = scannerPrenotazioniController.nextInt();
                } else if (rispostaTurista.equalsIgnoreCase("no")) {
                    System.out.println("Operazione di prenotazione annullata");
                    return false;
                } else {
                    System.out.println("Superato il numero massimo di tentativi. L'operazione di Prenotazione e' stata annullata");
                    return false;
                }
            }
            tentativiCoglione = 0;
            PrenotazioneTour prenotazione = new PrenotazioneTour(idDisponibilita, email, postiDaPrenotare);
            DBManagerUpdate.aggiornaPostiDisponibili(idDisponibilita, postiDaPrenotare);
            DBManagerUpdate.aggiornaTotalePostiPrenotati(idDisponibilita, postiDaPrenotare);
            String emailCicerone = DBManagerStampa.selezionaEmailCiceroneDaDisponibilita(idDisponibilita);
            String nomeTour = DBManagerStampa.prendiNomeTourDaTabellaDisponibilita(emailCicerone, idDisponibilita);
            String data = DBManagerStampa.prendiDataTourDaTabellaDisponibilita(emailCicerone, idDisponibilita);
            DBManagerInsert.InserisciNellaTabellaPrenotazioni(nomeTour, data, email, emailCicerone, postiDaPrenotare);
            System.out.println("Vuole proporre il tour a un amico? Inserire SI o NO");
            rispostaTurista = scannerPrenotazioniController.nextLine();
            if (rispostaTurista.equalsIgnoreCase("")) {
                rispostaTurista = scannerPrenotazioniController.nextLine();
            }
            if (rispostaTurista.equalsIgnoreCase("no")) {
                return true;
            }
            while (!rispostaTurista.equalsIgnoreCase("SI") && !rispostaTurista.equalsIgnoreCase("NO") && tentativiCoglione <= 5) {
                System.out.println("Risposta non valida. Inserire SI o NO");
                rispostaTurista = scannerPrenotazioniController.nextLine();
                tentativiCoglione++;
            }
            if (InvitoController.getInstance().eseguiCreaInvito(data, nomeTour, emailCicerone, idDisponibilita)) {
                System.out.println("Invito creato con successo.");
            }
            }catch(SQLException | ClassNotFoundException e){
                System.out.println("Non è possibile accedere al database.");
            }
        return true;
    }

    public boolean eseguiAnnullaPrenotazione(String emailTurista){
        System.out.println("Seleziona l'id della prenotazione che vuoi eliminare: ");
        int idPrenotazione = selezionaPrenotazioneTurista(emailTurista);
        if(idPrenotazione == -1){
            return false;
        }
        int tentativiCoglione = 0;
        String rispostaTurista;
        try {
            if (!DBManagerStampa.visualizzaAnnullagratis(idPrenotazione)) {
                System.out.println("Il tempo per annullare la prenotazione gratis e' scaduto. Devi pagare una  mora.");
                //Pagamento mora non implementato.
            }
            if(DBManagerStampa.visualizzaStatoPrenotazione(idPrenotazione)){
                Amministrazione.getInstance().rimborsoSoldi(idPrenotazione);
            }
            String avviso = null;
            return eseguiRimuoviPrenotazione(idPrenotazione, avviso);
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
            return false;
        }
    }

    /**
     * Metodo che rimuove una prenotazione e invia un avviso al turista.
     * @param idPrenotazione id della prenotazione da eliminare
     * @param avviso da inviare al turista o al cicerone in base a chi chiama il metodo.
     * @return true se la prenotazione è stata eliminata, false altrimenti.
     */
    public boolean eseguiRimuoviPrenotazione(int idPrenotazione, String avviso){
        try {
            String emailTurista = DBManagerStampa.prendiEmailTuristaDaPrenotazione(idPrenotazione);
            String emailCicerone = DBManagerStampa.prendiEmailCiceroneDaPrenotazione(idPrenotazione);
            String nomeTour = DBManagerStampa.prendiNomeTourDaPrenotazione(idPrenotazione);
            String dataTour = DBManagerStampa.prendiDataTourDaPrenotazione(idPrenotazione);
            int index_disponibilita = DBManagerStampa.prendiIndiceSingolaDisponibilita(emailCicerone, nomeTour, dataTour);
            int postiPrenotati = DBManagerStampa.visualizzaPostiPrenotati(nomeTour,dataTour,emailCicerone,emailTurista);
            DBManagerDelete.RimuoviPrenotazioneDallaTabella(nomeTour, dataTour, emailTurista);
            DBManagerUpdate.aggiungiPostiAPostiDisponibili(index_disponibilita, postiPrenotati);
            DBManagerUpdate.eliminaPostiDaTotalePostiPrenotati(index_disponibilita,postiPrenotati);
            if(avviso != null){
                AvvisoController.getInstance().eseguiInviaAvviso(emailTurista,avviso);
            }
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Non e' possibile accedere al database.");
            return false;
        }
    }

    private int selezionaPrenotazioneTurista(String emailTurista){
        try{
            DBManagerStampa.visualizzaPrenotazioniDelTurista(emailTurista);
            int id = scannerPrenotazioniController.nextInt();
            while(!DBManagerConfronti.esistenzaPrenotazioneDatoId(id)){
                System.out.println("Id non valido. Inserisci un altro id.");
                id = scannerPrenotazioniController.nextInt();
            }
            return id;
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
            return -1;
        }
    }

    public boolean eseguiVisualizzaPrenotazioni(String emailTurista){
        try {
            if(DBManagerStampa.visualizzaPrenotazioniDelTurista(emailTurista)) {
                return true;
            }else{
                return false;
            }
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
            return false;
        }
    }
}
