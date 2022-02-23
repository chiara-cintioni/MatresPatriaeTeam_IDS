package Actors;

import Controller.DisponibilitaController;
import Controller.TagController;
import Controller.TourController;
import DBManagers.DBManagerConfronti;
import DBManagers.DBManagerInsert;
import Entity.Tour;
import java.lang.*;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.*;

public class Cicerone {

    private String nome;
    private String cognome;
    private String username;
    private String email;
    private String password;
    private Scanner scanner = new Scanner(System.in);
    private static final String stringaIdentificatore = "Cicerone";

    public Cicerone(String nome, String cognome, String username, String email) {
        String password;
        while (!controlloStringa(nome) || !controlloStringa(cognome) ) {
            if (!controlloStringa(nome)  &&  !controlloStringa(cognome)){
                System.out.println("Formato del nome e cognome non valido. Inserire un nuovo nome e cognome:");
                System.out.println("Inserisci nome");
                nome = scanner.nextLine();
                System.out.println("Inserisci cognome");
                cognome=scanner.nextLine();
            } else if (!controlloStringa(nome)) {
                System.out.println("Formato del nome non valido. Inserire un nuovo nome:");
                nome = (scanner.nextLine());
            }
            else  {
                System.out.println("Formato del cognome non valido. Inserire un nuovo cognome:");
                cognome = (scanner.nextLine());}

            if (!controlloStringa(nome) ||  !controlloStringa(cognome));
        }
        //controllo username
        try{
            while(!DBManagerConfronti.confrontaUsername(username)){
                System.out.println("Esiste gia' un account con questo username. Inserisci un nuovo username.");
                username = scanner.nextLine();
            }
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Esiste gia' un account con questo username.");
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
            password = scanner.nextLine();
        } while (!controlloPassword(password));
        this.password = password;
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.email = email;
        try{
            DBManagerInsert.InserisciDatiRegistrazione(email, username, password, stringaIdentificatore);
            DBManagerInsert.InserisciNellaTabellaCicerone(email,nome,cognome,stringaIdentificatore);
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Errore: non è possibile inserire i dati nel database.");
        }
    }

    public Cicerone () {}

    /**
     *Metodo che crea un tour
     * @param email del cicerone o associazione
     * @return true se il tour e' stato creato false altrimenti.
     * */
    public void creaTour(String email){
        System.out.println(TourController.getInstance().eseguiCreaTour(email));
    }

    public boolean aggiungiSosta(String email){
        return TourController.getInstance().eseguiAggiungiSosta(email);
    }

    /**
     *Metodo che aggiunge un tour del cicerone in una specifica data
     * @param email del cicerone
     * @return true se la disponibilita'
     */
    public boolean aggiungiDisponibilita (String email) {
        boolean risultato = DisponibilitaController.getInstance().eseguiAggiungiDisponibilita(email);
        if(!risultato){
            System.out.println("Non è stato possibile aggiungere questa disponibilità.");
        }else {
            System.out.println("Disponibilita aggiunta correttamente.");
        }
        return true;
    }

    public boolean eliminaSosta(String email){
        boolean risultato = TourController.getInstance().eseguiEliminaSosta(email);
        if(!risultato){
            System.out.println("Non è stato possibile eliminare la sosta.");
        }else {
            System.out.println("Eliminazione avvenuta con successo");
        }
        return true;
    }


    /**
     *Metodo che rimuove una disponibilità di un tour.
     *Viene eliminato solo il singolo tour in una specifica data, e non il tour dalla lista dei tour del cicerone
     */
    public boolean rimuoviDisponibilita (String email) {
        boolean esito = DisponibilitaController.getInstance().eseguiRimuoviDisponibilita(email);
        if(esito == false){
            System.out.println("Non e' stato possibile eliminare la disponibilita'. Ritenta.");
            return false;
        }
        System.out.println("La disponibilita' e' stata rimossa con successo.");
        return true;
    }


    /**
     * Metodo che controlla il contenuto di una stringa (utilizzato per il controllo dei nomi e dei cognomi)
     * @param stringa che deve essere controllata
     * @return true se la stringa contiene uno dei caratteri speciali, false altrimenti.
     */
    private boolean controlloStringa(String stringa){
        return Pattern.matches("[A-Za-z àèéòùì']*", stringa);
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
    /**
     *Metodo che elimina un tour definitivamente dalla lista dei tour fatti dal singolo cicerone.
     *Vengono eliminate anche le disponibilita' a lui collegate.
     * @param email del cicerone che vuole rimuovere il tour
     */
    public boolean eliminaTourDefinitivamente(String email) {
       boolean esito = TourController.getInstance().eseguiEliminaDefinitivamenteTour(email);
       if(esito == true) {
           System.out.println ("Il tour è stato rimosso con successo.");
           return true;
       }
       else {

           System.out.println ("Non e' stato possibile eliminare il tour.");
           return false;
       }
    }

    public boolean modificaNomeDelTour(String email) {
        boolean esito = TourController.getInstance().eseguiModificaNomeTour(email);
        if (esito == true) {
            System.out.println ("Il nome del tour e' stato modificato.");
            return true;
        } else {
            System.out.println ("Non e' stato possibile modificare il nome del tour.");
            return false;
        }
    }

    public boolean modificaDescrizioneDelTour(String email) {
        boolean esito = TourController.getInstance().eseguiModificaDescrizioneTour(email);
        if (esito == true) {
            System.out.println ("La descrizione del tour e' stata modificata.");
            return true;
        } else {
            System.out.println ("Non e' stato possibile modificare la descrizione del tour.");
            return false;
        }
    }

    public boolean modificaPrezzoTour(String email) {
        boolean esito = TourController.getInstance().eseguiModificaPrezzoTour(email);
        if (esito == true) {
            System.out.println ("Il prezzo del tour e' stato modificato.");
            return true;
        } else {
            System.out.println ("Non e' stato possibile modificare il prezzo del tour.");
            return false;
        }
    }

    /**
     * Metodo per modificare il numero massimo di posti prenotabili a un tour
     * @param email  del cicerone che ha il tour che bisogna modificare
     * @return true se la modifica e' avvenuta, false altrimenti
     */
    public boolean modificaPostiTour(String email) {
        boolean esito = TourController.getInstance().eseguiModificaPostiTour(email);
        if (esito == true) {
            System.out.println ("Il numero massimo di posti del tour e' stato modificato.");
            return true;
        } else {
            System.out.println ("Non e' stato possibile modificare il numero massimo di posti del tour.");
            return false;
        }
    }

    /**
     * Metodo utilizzato dal Cicerone per modificare la data di un tour, andando a
     * modificare la disponibilita' del tour
     * @param email del cicerone
     * @return true se la modifica e' avvenuta, false altrimenti
     */
    public boolean modificaData(String email){
        boolean esito = DisponibilitaController.getInstance().eseguiModificaData(email);
        if (esito == true) {
            System.out.println ("La data è stata modificata con successo.");
            return true;
        } else {
            System.out.println ("Non e' stato possibile modificare la data.");
            return false;
        }
    }

    public boolean proponiTag(){
        boolean esito = TagController.getInstance().eseguiProponiTag();
        if (esito == true) {
            System.out.println ("Il tag è stato inviato all'amministrazione.");
            return true;
        } else {
            System.out.println ("Operazione annullata.");
            return false;
        }
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

}

