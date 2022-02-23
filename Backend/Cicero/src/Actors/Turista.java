package Actors;

import Controller.AvvisoController;
import Controller.PrenotazioniController;
import DBManagers.DBManagerConfronti;
import DBManagers.DBManagerInsert;
import Entity.*;
import java.lang.*;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Turista {

    private String nome;
    private String cognome;
    private String email;
    private String username;
    private String password;
    private Scanner scanner = new Scanner(System.in);
    private static final String stringaIdentificatore = "Turista";

    public Turista(String nome, String cognome, String username, String email) {
        String password;
        while (!controlloStringa(nome) || !controlloStringa(cognome) ) {
            if (!controlloStringa(nome)  &&  !controlloStringa(cognome)){
                System.out.println("Formato del nome e cognome non valido. Inserire un nuovo nome e cognome:");
                System.out.println("Inserisci nome");
                nome = scanner.nextLine();
                System.out.println("Inserisci cognome");
                cognome=scanner.nextLine();
            }
            else if (!controlloStringa(nome)) {
                System.out.println("Formato del nome non valido. Inserire un nuovo nome:");
                nome = (scanner.nextLine());
            }
            else  {
                System.out.println("Formato del cognome non valido. Inserire un nuovo cognome:");
                cognome = (scanner.nextLine());
            }
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
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        try{
            DBManagerInsert.InserisciDatiRegistrazione(email, username, password, stringaIdentificatore);
            DBManagerInsert.InserisciNellaTabellaTuristi(nome, cognome, email, stringaIdentificatore);
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Errore: non è possibile inserire i dati nel database.");
        }
    }

    public Turista(){}

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
                if (('a' <= c && c <= 'z')            // Checks if it is a lower case letter
                        || ('A' <= c && c <= 'Z')     //Checks if it is an upper case letter
                        || ('0' <= c && c <= '9')) {  //Checks to see if it is a digit
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
     * Metodo che permette al turista di eseguire una prenotazione.
     * @param email del turista che vuole effettuare una prenotazione
     * @return true se la prenotazione è avvenuta, false altrimenti.
     */
    public boolean eseguiPrenotazione (String email) {
        boolean esito = PrenotazioniController.getInstance().eseguiCreaPrenotazione(email);
        if(esito){
            System.out.println("Prenotazione effettuata con successo.");
            return true;
        }
        System.out.println("Operazione di prenotazione non effettuata.");
        return false;
    }

    /**
     * Metodo che permette al turista di annullare una prenotazione.
     * @param email del turista che vuole annullare una prenotazione
     * @return true se la prenotazione è stata annullata, false altrimenti.
     */
    public boolean annullaPrenotazione (String email) {
        boolean esito = PrenotazioniController.getInstance().eseguiAnnullaPrenotazione(email);
        if(esito){
            System.out.println("Prenotazione annullata con successo.");
            return true;
        }
        System.out.println("Operazione annulla prenotazione annullata.");
        return false;
    }


    /**
     * Non implementato
     */
    public void accettaInvito(Invito invito){ }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    /**
     * Metodo per il pagamento del tour. Non implementato.
     * @param prenotazione che si vuole pagare
     * @return true se il pagamento è avvenuto, false altrimenti.
     */
    public boolean effettuaPagamento(PrenotazioneTour prenotazione){
        return true;
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

    public static String getStringaIdentificatore() {
        return stringaIdentificatore;
    }

    /**
     * Metodo per inserire i dati della carta di credito che si vuole utilizzare per pagare.
     * Non implementato.
     * @return true se i dati inseriti sono tutti corretti, false altrimenti
     */
    public boolean inserisciCartaDiCredito(){
        return true;
    }

    /**
     * Metodo per annullare un pagamento
     * @param prenotazione di cui si vuole annullare il pagamento
     * @return true se l'annullamento è avvenuto, false altrimenti
     */
    public boolean annullaPagamento(PrenotazioneTour prenotazione){
        return true;
    }

    public void visualizzaAvvisi(String email) {
        AvvisoController.getInstance().eseguiVisualizzaITuoiAvvisi(email);
    }

    public void visualizzaPrenotazioni(String email) {
        if(!PrenotazioniController.getInstance().eseguiVisualizzaPrenotazioni(email)){
            System.out.println("Non esistono prenotazioni a tuo nome.");
        }
    }
}
