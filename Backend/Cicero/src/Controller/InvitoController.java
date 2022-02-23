package Controller;

import DBManagers.DBManagerConfronti;
import DBManagers.DBManagerDelete;
import DBManagers.DBManagerInsert;
import DBManagers.DBManagerUpdate;
import Entity.Invito;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InvitoController {
    private static InvitoController invitoController;
    private static Scanner scannerInvitoController = new Scanner(System.in);

    private InvitoController(){}

    public static InvitoController getInstance(){
        if(invitoController == null){
            invitoController = new InvitoController();
        }
        return invitoController;
    }

    public boolean eseguiCreaInvito(String dataTour, String nomeTour, String emailCicerone, int idDisponibilita){
        System.out.println("Inserire il nome dell'invitato: ");
        String nomeInvitato = scannerInvitoController.nextLine();
        System.out.println("Inserire il cognome dell'invitato: ");
        String cognomeInvitato = scannerInvitoController.nextLine();
        String rispostaTurista;
        while (!controlloStringa(nomeInvitato) || !controlloStringa(cognomeInvitato)) {
            if (!controlloStringa(nomeInvitato)  &&  !controlloStringa(cognomeInvitato)){
                System.out.println("Formato del nome e cognome non valido. Inserire un nuovo nome e cognome:");
                System.out.println("Inserisci nome");
                nomeInvitato = scannerInvitoController.nextLine();
                if(nomeInvitato.equalsIgnoreCase("")){
                    nomeInvitato = scannerInvitoController.nextLine();
                }
                System.out.println("Inserisci cognome");
                cognomeInvitato=scannerInvitoController.nextLine();
                if(cognomeInvitato.equalsIgnoreCase("")){
                    cognomeInvitato = scannerInvitoController.nextLine();
                }
            }  else if (!controlloStringa(nomeInvitato)) {
                System.out.println("Formato del nome non valido. Inserire un nuovo nome:");
                nomeInvitato = scannerInvitoController.nextLine();
                if(nomeInvitato.equalsIgnoreCase("")){
                    nomeInvitato = scannerInvitoController.nextLine();
                }
            }
            else  {
                System.out.println("Formato del cognome non valido. Inserire un nuovo cognome:");
                cognomeInvitato = scannerInvitoController.nextLine();
                if(cognomeInvitato.equalsIgnoreCase("")){
                    cognomeInvitato = scannerInvitoController.nextLine();
                }
            }
        }
        System.out.println("Inserire email dell'invitato: ");
        String emailInvitato = scannerInvitoController.nextLine();
        if(emailInvitato.equalsIgnoreCase("")){
            emailInvitato = scannerInvitoController.nextLine();
        }
        while (!controlloStringaEmail(emailInvitato)) {
            System.out.println("Email non valida:");
            System.out.println("Inserisci email");
            emailInvitato = scannerInvitoController.nextLine();
            if(emailInvitato.equalsIgnoreCase("")){
                emailInvitato = scannerInvitoController.nextLine();
            }
        }
        int tentativiCoglione = 0;
        System.out.println("Inserire il numero di posti che si vuole riservare per l'invito: ");
        int postiDaPrenotare = scannerInvitoController.nextInt();
        try {
            while (!DBManagerConfronti.verificaPerIPostiDaPrenotare(idDisponibilita, postiDaPrenotare)) {
                System.out.println("I posti da lei inseriti non sono disponibili.");
                System.out.println("Vuole ancora invitare qualcuno cambiando il numero di posti da riservare? Inserire SI o NO");
                rispostaTurista = scannerInvitoController.nextLine();
                if(rispostaTurista.equalsIgnoreCase("")){
                    rispostaTurista = scannerInvitoController.nextLine();
                }
                while (!rispostaTurista.equalsIgnoreCase("SI") && !rispostaTurista.equalsIgnoreCase("NO") && tentativiCoglione <= 5) {
                    System.out.println("Risposta non valida. Inserire SI o NO");
                    rispostaTurista = scannerInvitoController.nextLine();
                    if(rispostaTurista.equalsIgnoreCase("")){
                        rispostaTurista = scannerInvitoController.nextLine();
                    }
                    tentativiCoglione++;
                }
                if (rispostaTurista.equalsIgnoreCase("si")) {
                    System.out.println("Inserire un numero minore di " +postiDaPrenotare);
                    postiDaPrenotare = scannerInvitoController.nextInt();
                } else if (rispostaTurista.equalsIgnoreCase("no")) {
                    System.out.println("Operazione di invito annullata");
                    return false;
                } else {
                    System.out.println("Superato il numero massimo di tentativi. L'operazione di Invito e' stata annullata");
                    return false;
                }
            }
            Invito invito = new Invito(nomeInvitato,cognomeInvitato,emailInvitato,postiDaPrenotare,emailCicerone,nomeTour,dataTour);
            DBManagerUpdate.aggiornaPostiDisponibili(idDisponibilita,postiDaPrenotare);
            DBManagerInsert.InserisciNellaTabellaInviti(nomeInvitato,cognomeInvitato,emailInvitato,emailCicerone,dataTour,nomeTour,postiDaPrenotare);
            eseguiInviaInvito(emailInvitato, nomeTour, dataTour);
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
        return true;
    }

    private boolean controlloStringaEmail (String stringa) {
        return Pattern.matches(".+@.+\\.[a-zA-Z0-9]+",stringa);
    }

    private boolean controlloStringa(String stringa){
        return Pattern.matches("[A-Za-z àèéòùì']*", stringa);
    }

    public void eseguiInviaInvito(String emailInvitato, String nomeTour, String dataTour) {
        try{
            if(DBManagerConfronti.confrontaEmailDaTabellaTuristi(emailInvitato)){
                String avviso = "Sei stato invitato a partecipare al tour: "+nomeTour+" in data: "+dataTour;
                AvvisoController.getInstance().eseguiInviaAvviso(emailInvitato,avviso);
            };
            System.out.println ("L'invito e' stato inoltrato al contatto da lei indicato.");
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
    }

    public void eseguiEliminaInvito(String nomeTour, String emailCicerone, String emailInvitato){
        try{
            DBManagerDelete.RimuoviInvitoDallaTabella(nomeTour, emailCicerone, emailInvitato);
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
    }

}
