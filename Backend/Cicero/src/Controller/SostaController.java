package Controller;

import DBManagers.DBManagerConfronti;
import DBManagers.DBManagerDelete;
import DBManagers.DBManagerInsert;
import DBManagers.DBManagerStampa;
import Entity.Sosta;

import java.sql.SQLException;
import java.util.Scanner;

public class SostaController {
    private static SostaController sostaController;
    private static Scanner scannerSostaController = new Scanner(System.in);

    private SostaController() {
    }

    public static SostaController getInstance(){
        if(sostaController == null){
            sostaController = new SostaController();
        }
        return sostaController;
    }

    public boolean eseguiAggiungiSosta(String emailCicerone, String nomeTour){
            if(creaSosta(emailCicerone,nomeTour)){
                System.out.println("Sosta aggiunta al tour.");
                return true;
            }else{
                System.out.println("Non è stato possibile aggiungere la sosta al tour.");
                return false;
            }
    }

    private boolean creaSosta(String emailCicerone, String nomeTour){
        System.out.println("Inserisci il nome della sosta che vuoi aggiungere al tour: "+nomeTour);
        String nomeSosta = scannerSostaController.nextLine();
        if(nomeSosta.equalsIgnoreCase("")){
            nomeSosta = scannerSostaController.nextLine();
        }
        System.out.println("Inserisci una descrizione per la sosta: "+nomeSosta+", del tour: "+nomeTour);
        String descrizione = scannerSostaController.nextLine();
        if(descrizione.equalsIgnoreCase("")){
            descrizione = scannerSostaController.nextLine();
        }
        try {
            if(DBManagerConfronti.verificaEsistenzaSosta(nomeSosta, emailCicerone, nomeTour)){
                System.out.println("Il tour: "+nomeTour+", presenta già una sosta di nome: "+nomeSosta);
                System.out.println("Vuoi creare un'altra sosta? Inserisci 'si' per creare una sosta differente o 'no' per annullare l'operazione.");
                String risposta = scannerSostaController.nextLine();
                if(risposta.equalsIgnoreCase("")){
                    risposta = scannerSostaController.nextLine();
                }
                while(!risposta.equalsIgnoreCase("si") && !risposta.equalsIgnoreCase("no")){
                    System.out.println("Risposta non valida. Inserisci 'si' per creare una nuova sosta o 'no' per annullare l'operazione.");
                    risposta = scannerSostaController.nextLine();
                    if(risposta.equalsIgnoreCase("")){
                        risposta = scannerSostaController.nextLine();
                    }
                }
                if(risposta.equalsIgnoreCase("si")){
                    creaSosta(emailCicerone,nomeTour);
                }else if(risposta.equalsIgnoreCase("no")){
                    System.out.println("Annullamento operazione...");
                    return false;
                }
            }
            Sosta sosta = new Sosta(nomeSosta,descrizione,nomeTour,emailCicerone);
            DBManagerInsert.InserisciNellaTabellaSoste(nomeSosta,descrizione,emailCicerone,nomeTour);
            return true;
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
            return false;
        }
    }

    public boolean eseguiEliminaSosta(String email, String nomeTour){
        try{
            if(!DBManagerConfronti.esistenzaSosteNelTour(nomeTour,email)){
                System.out.println("Il tour ha solo una sosta, non è quindi possibile eliminare la sosta senza eliminare il tour.");
                System.out.println("Seleziona 1 per eliminare il tour. Qualsiasi altro valore annullerà l'operazione.");
                String ris = scannerSostaController.nextLine();
                if(ris.equalsIgnoreCase("")){
                    ris = scannerSostaController.nextLine();
                }
                if(ris.equalsIgnoreCase("1")){
                    TourController.getInstance().eseguiEliminaDefinitivamenteTourDatoIlNome(email,nomeTour);
                }else{
                    return false;
                }
            }else{
                int idSosta = eseguiSelezionaSosta(email,nomeTour);
                if(idSosta == -1){
                    return false;
                }
                DBManagerDelete.RimuoviSostaDallaTabella(idSosta);
                String avviso = "Una sosta è stata rimossa dal tour "+nomeTour;
                int [] idDisponibilita = DBManagerStampa.prendiIndiceDisponibilita(email,nomeTour);
                if(idDisponibilita[0] == -1){
                }else{
                    for (int i = 0; i < idDisponibilita.length; i++) {
                        DisponibilitaController.getInstance().eseguiInviaAvvisoAiTuristiCheHannoPrenotato(email, idDisponibilita[i], avviso);
                    }
                }
            }
            return true;
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
            return false;
        }
    }

    public void eseguiVisualizzaSosta(String nomeTour, String emailCicerone){
        try{
            System.out.println("Il tour: "+nomeTour+", ha le seguenti soste: ");
            DBManagerStampa.visualizzaSoste(nomeTour,emailCicerone);
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
    }

    public int eseguiSelezionaSosta(String email, String nomeTour){
        System.out.println("Inserisci l'id della sosta che vuoi eliminare dal tour: "+nomeTour);
        eseguiVisualizzaSosta(nomeTour, email);
        int risposta = scannerSostaController.nextInt();
        try {
            while(!DBManagerConfronti.verificaEsistenzaSostaDatoIdSosta(risposta)){
                System.out.println("Non esiste una sosta con id: "+risposta+". Inserisci un'altro id.");
                risposta = scannerSostaController.nextInt();
            }
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
            return -1;
        }
        return risposta;
    }
}
