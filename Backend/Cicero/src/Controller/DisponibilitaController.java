package Controller;

import Actors.Amministrazione;
import DBManagers.*;
import Entity.Disponibilita;

import java.sql.SQLException;
import java.util.Scanner;

public class DisponibilitaController {
    private static DisponibilitaController disponibilitaController;
    private Scanner scannerDisponibilitaController = new Scanner(System.in);

    private DisponibilitaController(){}

    public static DisponibilitaController getInstance() {
        if (disponibilitaController == null) {
            disponibilitaController = new DisponibilitaController();
        }
        return disponibilitaController;
    }


    public boolean eseguiAggiungiDisponibilita(String email) {
        int anno;
        int mese;
        int giorno;
        System.out.println("Inserire l'id del tour al quale vuoi aggiungere una disponibilita': ");
        String nomeTour = TourController.getInstance().selezionaTour(email);
        String data = ritornaData();
        int postiDisponibili;
        String nomeGuida;
        try {
            String ruolo = DBManagerStampa.prendiRuolo(email);
            while(DBManagerConfronti.esistenzaDiUnTourInQuellaData(data, email)){
                if(ruolo.equalsIgnoreCase("Associazione")){
                    if(DBManagerConfronti.esistenzaDelTourNellaDataSpecificata(data,email,nomeTour)){
                        System.out.println("Il giorno: "+data+" è già presente una disponibilita. Inserire una nuova data.");
                        data = ritornaData();
                    }else{
                        break;
                    }
                }else{
                    System.out.println("Il tour "+nomeTour+" ha già una disponibilita' il: "+data+". Inserire una nuova data.");
                    data = ritornaData();
                }
            }
            if(ruolo.equalsIgnoreCase("Associazione")){
                nomeGuida = GuidaController.getInstance().eseguiAssegnaGuida(email,data);
                if(nomeGuida == null){
                    System.out.println("Non è stato possibile aggiungere la disponibilita'.");
                    return false;
                }
            }else{ //essendo un Cicerone, non ha guide.
                nomeGuida = null;
            }
            String nomeCicerone = DBManagerStampa.prendiUsername(email);
            postiDisponibili = DBManagerStampa.prendiNumeroMassimoTour(email, nomeTour);
            Disponibilita disponibilita = new Disponibilita(nomeTour,nomeGuida,nomeCicerone,email,data,postiDisponibili);
            DBManagerInsert.InserisciNellaTabellaDisponibilita(nomeTour, nomeGuida, nomeCicerone, email, data, postiDisponibili);
        }catch(SQLException | ClassNotFoundException e){
            System.out.println("Non è possibile accedere al database.");
            return false;
        }
        return true;
    }

    public boolean eseguiRimuoviDisponibilita(String emailCicerone){
        int indiceDisponibilita;
        String nomeTour;
        String[] emailTurista;
        String dataTour;
        String avviso;
        int indicePrenotazione;
        try{
            indiceDisponibilita = selezionaDisponibilitaCicerone(emailCicerone);
            System.out.println("Hai scelto di eliminare: ");
            DBManagerStampa.visualizzaTourDaIndice(indiceDisponibilita);
            nomeTour = DBManagerStampa.prendiNomeTourDaTabellaDisponibilita(emailCicerone,indiceDisponibilita);
            dataTour = DBManagerStampa.prendiDataTourDaTabellaDisponibilita(emailCicerone, indiceDisponibilita);
            avviso = "ATTENZIONE: il tour "+nomeTour+" non è piu' disponibile. La sua prenotazione e' stata annullata";
            emailTurista = DBManagerStampa.prendiEmailTurista(emailCicerone, nomeTour, dataTour);
            for (int i = 0; i< emailTurista.length; i++) {
                indicePrenotazione = DBManagerStampa.prendiIdDellaPrenotazione(dataTour,nomeTour,emailTurista[i]);
                while(!DBManagerConfronti.esistenzaPrenotazioneDatoId(indicePrenotazione)){
                    System.out.println("Id non valido. Inserisci un altro id.");
                    indicePrenotazione = scannerDisponibilitaController.nextInt();
                }
                PrenotazioniController.getInstance().eseguiRimuoviPrenotazione(indicePrenotazione, avviso);
            }
            DBManagerDelete.RimuoviDisponibilitaDallaTabella(indiceDisponibilita);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
            return false;
        }
        return true;
    }

    /**
     * Metodo che elimina una disponibilita' e tutte le sue prenotazioni, avvisando i turisti che hanno effettuato la
     * prenotazione dell'accaduto.
     * @param emailCicerone
     * @param indiceDisponibilita
     * @return
     */
    public boolean eseguiRimuoviDisponibilita2(String emailCicerone, int indiceDisponibilita){
        String nomeTour;
        String[] emailTurista;
        String dataTour;
        String avviso;
        int idPrenotazione;
        try{
            nomeTour = DBManagerStampa.prendiNomeTourDaTabellaDisponibilita(emailCicerone,indiceDisponibilita);
            dataTour = DBManagerStampa.prendiDataTourDaTabellaDisponibilita(emailCicerone, indiceDisponibilita);
            avviso = "ATTENZIONE: il tour "+nomeTour+" non è piu' disponibile. La sua prenotazione e' stata annullata";
            emailTurista = DBManagerStampa.prendiEmailTurista(emailCicerone, nomeTour, dataTour);
            for (int i = 0; i< emailTurista.length; i++) {
                idPrenotazione = DBManagerStampa.prendiIdDellaPrenotazione(dataTour,nomeTour,emailTurista[i]);
                if(!DBManagerStampa.prendiStatoDellaPrenotazione(nomeTour,dataTour,emailCicerone,emailTurista[i])){
                    while(!DBManagerConfronti.esistenzaPrenotazioneDatoId(idPrenotazione)){
                        System.out.println("Id non valido. Inserisci un altro id.");
                        idPrenotazione = scannerDisponibilitaController.nextInt();
                    }
                    PrenotazioniController.getInstance().eseguiRimuoviPrenotazione(idPrenotazione, avviso);
                }else{
                    Amministrazione.getInstance().rimborsoSoldi(idPrenotazione);
                }
            }
            DBManagerDelete.RimuoviDisponibilitaDallaTabella(indiceDisponibilita);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
            return false;
        }
        return true;
    }

    /**
     * Metodo che avvisa i turisti che hanno prenotato una disponibilita di un tour del cambio prezzo avvenuto.
     * @param emailCicerone
     * @param indiceDisponibilita
     * @return
     */
    public boolean eseguiInviaAvvisoAiTuristiCheHannoPrenotato(String emailCicerone, int indiceDisponibilita, String avviso){
        String nomeTour;
        String[] emailTurista;
        String dataTour;
        try{
            nomeTour = DBManagerStampa.prendiNomeTourDaTabellaDisponibilita(emailCicerone,indiceDisponibilita);
            dataTour = DBManagerStampa.prendiDataTourDaTabellaDisponibilita(emailCicerone, indiceDisponibilita);
            emailTurista = DBManagerStampa.prendiEmailTurista(emailCicerone, nomeTour, dataTour);
            for (int i = 0; i< emailTurista.length; i++) {
                AvvisoController.getInstance().eseguiInviaAvviso(emailTurista[i],avviso);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
            return false;
        }
        return true;
    }

    public void eseguiMostraDisponibilita(String email) {
        try {
            String ruolo = DBManagerStampa.prendiRuolo(email);
            if(ruolo.equals("Cicerone")){
                DBManagerStampa.visualizzaLeTueDisponibilitaOrdinateSenzaGuida(email);
            }else{
                DBManagerStampa.visualizzaLeTueDisponibilitaOrdinate(email);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println ("Non e' possibile accedere al database");
        }
    }

    public void eseguiVisualizzaDisponibilitaDiUnTour(int indiceTour){
        try {
            String email = DBManagerStampa.prendiEmailCiceroneDaTour(indiceTour);
            String nomeTour = DBManagerStampa.prendiNomeTourDaTabellaTour(indiceTour);
            DBManagerStampa.visualizzaDisponibilitaDiUnTour(nomeTour);
        }catch(SQLException | ClassNotFoundException e){
            System.out.println("Non è stato possibile accedere al database. Ci scusiamo per il disagio.");
        }
    }

    private String ritornaData(){
        int anno;
        int mese;
        int giorno;
        System.out.println ("Inserire l'anno: ");
        anno = scannerDisponibilitaController.nextInt();
        while (anno < 2022) {
            System.out.println ("Non puoi inserire un anno precedente al 2022. Inserire anno:");
            anno = scannerDisponibilitaController.nextInt();
        }
        System.out.println ("Inserire il mese:");
        mese = scannerDisponibilitaController.nextInt();
        while (mese > 12 || mese <= 0) {
            System.out.println ("Non puoi inserire un mese maggiore di 12 o minore di 0. Inserire mese:");
            mese = scannerDisponibilitaController.nextInt();
        }
        System.out.println ("Inserire il giorno: ");
        giorno = scannerDisponibilitaController.nextInt();

        while (giorno > 31 || giorno <= 0)  {
            System.out.println ("Non puoi inserire un giorno maggiore di 31 o minore di 0. Inserire giorno:");
            giorno = scannerDisponibilitaController.nextInt();
        }
        /*
         * Controllo per il mese di Febbraio riguardo l'anno bisestile */
        if (mese == 2) {
            if(!annoBisestile(anno)){
                while(giorno>28){
                    System.out.println ("Anno non bisestile, il numero massimo dei giorni di Febbraio è 28. Inserisci giorno:");
                    giorno = scannerDisponibilitaController.nextInt();
                }
            } else{
                while (giorno > 29) {
                    System.out.println ("Anno  bisestile, il numero massimo dei giorni di Febbraio è 98. Inserisci giorno:");
                    giorno = scannerDisponibilitaController.nextInt();
                }
            }
        }
        int [] arrayMesi = {4, 6, 9, 11}; //mesi che hanno 30 giorni
        for (int i = 0; i < arrayMesi.length; i++) {
            if (mese == arrayMesi[i]) {
                while(giorno > 30) {
                    System.out.println ("Anno non bisestile. il mese ha solo 30 giorni. Inserisci giorno:");
                    giorno = scannerDisponibilitaController.nextInt();
                }

            }
        }
        String stringaMese;
        String stringaGiorno;
        if(mese<10){
            stringaMese = "0"+mese;
        }else{
            stringaMese = ""+mese;
        }
        if(giorno<10){
            stringaGiorno = "0"+giorno;
        }else{
            stringaGiorno = ""+giorno;
        }
        String data = anno+"/"+stringaMese+"/"+stringaGiorno;
        return data;
    }

    /**
     * Metodo che ritorna il numero massimo di posti prenotati nelle varie disponibilita' di un tour.
     * @param nomeTour
     * @param emailCicerone
     * @return
     */
    public int ritornaPostiMassimiPrenotatiNelleDisponibilitaDiUnTour (String nomeTour, String emailCicerone) {
        try {
           int postiPrenotati = DBManagerStampa.prendiValoreMassimoPostiPrenotati(nomeTour,emailCicerone);
           return postiPrenotati;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Non e' possibile accedere al databse. Riprova piu' tardi.");
            return -1;
        }
    }

    /**
     * Modifica la data di una disponibilita
     * @param email
     * @return
     */
    public boolean eseguiModificaData(String email) {
        String nomeTour = TourController.getInstance().selezionaTour(email);
        int indice = selezionaDisponibilitaDaTour(email, nomeTour);
        try {
            String vecchiaData = DBManagerStampa.visualizzaDataDiUnTour( nomeTour, email, indice);
            System.out.println("La disponibilita del tour: "+nomeTour+" ha data: "+vecchiaData);
            System.out.println("Inserisci la nuova data per il tour: ");
            String nuovaData = ritornaData();
            while(DBManagerConfronti.esistenzaDiUnTourInQuellaData(nuovaData,email)){
                System.out.println("Data non disponibile. Inserisci la nuova data per il tour: ");
                nuovaData = ritornaData();
            }
            DBManagerUpdate.cambiaDataDelTour(email,nomeTour,nuovaData,vecchiaData);
            return true;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Non e' possibile accedere al database. Riprova piu' tardi.");
            return false;
        }
    }

    private int selezionaDisponibilitaDaTour(String email, String nomeTour){
        try {
            System.out.println("Il tour: "+nomeTour+" ha le seguenti disponibilita':");
            DBManagerStampa.visualizzaLeDisponibilitaDiUnTourSpecifico(email, nomeTour);
            int indiceDisponibilita = scannerDisponibilitaController.nextInt();
            while (!DBManagerConfronti.confrontaDisponibilita(indiceDisponibilita)) {
                System.out.println ("Id non valido. Inserire nuovo id:");
                indiceDisponibilita = scannerDisponibilitaController.nextInt();
            }
            return indiceDisponibilita;

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Non e' possibile accedere al database.");
            return -1;
        }
    }

    /**
     *Metodo che controlla se l'anno e' bisestile oppure no
     * @param anno che si vuole controllare
     * @return true se l'anno e' bisestile, false atrimenti
     */
    private boolean annoBisestile(int anno){
        if(anno > 1584 && (anno%400==0 || (anno%4==0 && anno%100!=0))){
            return true;
        }
        return false;
    }

    public int selezionaDisponibilitaTurista(){
        int disponibilita = -1;
        try {
            if(!DBManagerStampa.visualizzaSoloTourDisponibili()){
                System.out.println("Non ci sono tour disponibili per la prenotazione.");
                return -1;
            }
            System.out.println("Inserisci l'id del tour che ti inseressa per vederne le date disponibili.");
            int risposta = scannerDisponibilitaController.nextInt();
            while(!DBManagerConfronti.confrontaIdTour(risposta)){
                System.out.println("Id non valido. Inserisci un nuovo id.");
                risposta = scannerDisponibilitaController.nextInt();
            }
            String nomeTour = DBManagerStampa.prendiNomeTourDaTabellaTour(risposta);
            String emailCicerone = DBManagerStampa.prendiEmailCiceroneDaTour(risposta);
            disponibilita = selezionaDisponibilitaDaTour(emailCicerone,nomeTour);
            if (disponibilita == -1) {

            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
        return disponibilita;
    }

    private int selezionaDisponibilitaCicerone(String email){
        int risposta = 0;
        try{
            DBManagerStampa.visualizzaLeTueDisponibilita(email);
            System.out.println("Inserisci l'id della disponibilità che vuoi eliminare.");
            risposta = scannerDisponibilitaController.nextInt();
            while(!DBManagerConfronti.confrontaDisponibilita(risposta)){
                System.out.println("Inserisci un nuovo id.");
                risposta = scannerDisponibilitaController.nextInt();
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
        return risposta;
    }


}
