package Actors;

import Controller.GuidaController;
import DBManagers.DBManagerConfronti;
import DBManagers.DBManagerInsert;
import Entity.*;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;


public class Associazione extends Cicerone{

    private String nome;
    private String email;
    private String password;
    private String partitaIVA;
    private final Scanner scanner = new Scanner (System.in);
    private static final String stringaIdentificatore = "Associazione";

    public Associazione(String nome, String email, String partitaIVA){
       String password;
       while (!controlloStringa(nome)) {
           System.out.println("Formato del nome non valido.");
           System.out.println("Inserisci il nome dell'Associazione");
           nome = scanner.nextLine();
       }
       while (!controlloStringaIVA(partitaIVA)) {
           System.out.println("Inserisci partita IVA");
           partitaIVA = scanner.nextLine();
       }
       //controllo username
       try{
           while(!DBManagerConfronti.confrontaUsername(nome)){
               System.out.println("Esiste gia' un'associazione con questo nome. Inserisci un nuovo nome.");
               nome = scanner.nextLine();
           }
       }catch (SQLException | ClassNotFoundException e){
           System.out.println("Esiste gia' un'associazione con questo nome.");
       }
       //confronto email
       while (!controlloStringaEmail(email)) {
           System.out.println("Email non valida:");
           System.out.println("Inserisci email");
           email = scanner.nextLine();
       }
       try{
           while(!DBManagerConfronti.confrontaEmail(email)){
               System.out.println("Esiste gia' un account con questa mail. Inserisci una nuova mail.");
               email = scanner.nextLine();
           }
       }catch (SQLException | ClassNotFoundException e){
           System.out.println("Esiste gia' un account con questa mail.");
       }
       do {
           System.out.println("Inserisci una password ");
           password = scanner.next();
       } while (!controlloPassword(password));
      try{
          DBManagerInsert.InserisciDatiRegistrazione(email, nome, password, stringaIdentificatore);
          DBManagerInsert.InserisciNellaTabellaAssociazioni(email, nome, partitaIVA, stringaIdentificatore);
      }catch (SQLException | ClassNotFoundException e){
          System.out.println("Errore: non è possibile inserire i dati nel database.");
      }
       String risposta = "si";
       System.out.println("Inserisci le guide che lavorano per la tua associazione: ");
       while(risposta.equalsIgnoreCase("si")) {
           System.out.println("Inserisci i dettagli della guida: ");
           if(aggiungiGuida(email, nome)){
               System.out.println ("Vuoi inserire un' altra guida alla tua lista delle guide? Rispondere con si o no");
               risposta = scanner.nextLine();
               if(risposta.equalsIgnoreCase("")){
                   risposta = scanner.nextLine();
               }
               while (!risposta.equalsIgnoreCase("si") && !risposta.equalsIgnoreCase("no")) {
                   System.out.println ("Risposta non valida, inserire si o no: ");
                   risposta = scanner.nextLine();
               }
               this.password = password;
               this.nome = nome;
               this.email = email;
               this.partitaIVA = partitaIVA;
           }else{
                risposta = "no";
           }
       }
   }

    public Associazione(){}

    public boolean eliminaGuida (String email) {
      boolean esito = GuidaController.getInstance().eseguiRimuoviGuidaDallaLista(email);
      if (esito == true) {
          System.out.println("La guida è stata eliminata correttamente.");
          return true;
      }
      else {
          System.out.println("Operazione annullata.");
          return false;
      }
    }

    public boolean aggiungiGuida(String email, String nome){
        if(GuidaController.getInstance().eseguiCreaGuida(email, nome)){
          System.out.println("La guida è stata aggiunta correttamente.");
          return true;
        }else{
            System.out.println("Operazione annullata");
            return false;
        }
    }

    @Override
    public boolean aggiungiSosta(String email) {
        return super.aggiungiSosta(email);
    }

    @Override
    public boolean rimuoviDisponibilita(String email) {
        return super.rimuoviDisponibilita(email);
    }

    @Override
    public boolean eliminaTourDefinitivamente(String email) {
        return super.eliminaTourDefinitivamente(email);
    }

    @Override
    public boolean aggiungiDisponibilita(String email) {
        super.aggiungiDisponibilita(email);
        return true;
    }

    @Override
    public boolean eliminaSosta(String email) {
        return super.eliminaSosta(email);
    }

    @Override
    public boolean modificaNomeDelTour (String email) {
        return super.modificaNomeDelTour(email);
    }

    @Override
    public boolean proponiTag() {
        return super.proponiTag();
    }

    @Override
    public boolean modificaData(String email) {
        return super.modificaData(email);
    }

    @Override
    public boolean modificaDescrizioneDelTour(String email) {
        return super.modificaDescrizioneDelTour(email);
    }

    @Override
    public boolean modificaPostiTour(String email) {
        return super.modificaPostiTour(email);
    }

    @Override
    public boolean modificaPrezzoTour(String email) {
        return super.modificaPrezzoTour(email);
    }

    @Override
    public void creaTour(String email) {
        super.creaTour(email);
    }

    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public String getPartitaIVA() {
        return partitaIVA;
    }

    private boolean controlloDati(String stringa) {
        if (!controlloStringa(stringa)) {
            return false;
        }
        return true;
    }

    private boolean controlloStringa(String stringa){
        return Pattern.matches("[A-Za-z0-9 àèéòùì!']*", stringa);
    }

    private boolean controlloStringaIVA(String IVA){
        while (IVA.length() != 6 ) {
            System.out.println ("Codice partita IVA non valido. Deve essere di 6 cifre");
            return false;
        }
        if(!Pattern.matches("[0-9]+",IVA)){
            System.out.println("La partita IVA deve contenere solo 6 numeri.");
            return false;
        }
        return Pattern.matches("[0-9]+",IVA);
    }

    private boolean controlloStringaEmail (String stringa) {
        return Pattern.matches(".+@.+\\.[a-zA-Z0-9]+",stringa);
    }

    private boolean controlloPassword(String password){
        boolean validita = false;
        if(password.length()<8) {
            validita = false;
            System.out.println("La password deve essere lunga almeno 8 caratteri.");
        }else {
            for (int i = 0; i < password.length(); i++) {
                char c = password.charAt(i);
                if (('a' <= c && c <= 'z') // Checks if it is a lower case letter
                        || ('A' <= c && c <= 'Z') //Checks if it is an upper case letter
                        || ('0' <= c && c <= '9') //Checks to see if it is a digit
                ) {
                    validita = true;
                } else {
                    System.out.println("Sono ammesse solo lettere e numeri.");
                    validita = false;
                    break;
                }
            }
        }
        return validita;
    }

}
