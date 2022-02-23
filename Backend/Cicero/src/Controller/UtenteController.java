package Controller;

import Actors.Associazione;
import Actors.Cicerone;
import Actors.Turista;
import Boundary.MenuAmministrazione;
import Boundary.MenuAssociazione;
import Boundary.MenuCicerone;
import Boundary.MenuTurista;
import DBManagers.DBManagerConfronti;
import DBManagers.DBManagerStampa;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class UtenteController {
    private static UtenteController utenteController;
    private Scanner scanner = new Scanner (System.in);

    private UtenteController() {}

    public static UtenteController getInstance() {
        if (utenteController == null) {
            utenteController = new UtenteController();
        }
        return utenteController;
    }

    public boolean eseguiLogin (){
        int tentativi = 0;
        int tentativiPassword = 0;
        String email;
        String password;
        String username = "ginetto00";
        System.out.println("Per effettuare il login, inserisci le tue credenziali.");
        System.out.println("Inserisci l'email.");
        email = scanner.nextLine();
        //confronto email per esistenza account
        try {
            while (!controlloStringaEmail(email) && tentativi < 5) {
                tentativi++;
                System.out.println("Email non valida:");
                System.out.println("Inserisci email: ");
                email = scanner.nextLine();
            }
            while (DBManagerConfronti.confrontaEmail(email) && tentativi <5) {
                tentativi++;
                System.out.println("Non esiste un account con questa mail.");
                System.out.println("Inserire un'altra mail o annullare l'operazione di login scrivendo 'annulla'.");
                email = scanner.nextLine();
                if (email.equalsIgnoreCase("annulla")) {
                    System.out.println("Operazione di login annullata");
                    return false;
                }
            }
            if (tentativi >= 5) {
                System.out.println("Raggiunto il numero massimo di tentativi. Operazione annullata.");
                return false;
            }
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Non esiste un account con questa email.");
        }
        //controllo correttezza password
        System.out.println("Inserisci la password");
        password = scanner.nextLine();
        try {
            while (!password.equals(DBManagerStampa.prendiPassword(email)) && tentativiPassword < 3) {
                tentativiPassword++;
                System.out.println("Password errata. Inserisci una nuova password o 'annulla' per annullare l'operazione.");
                password= scanner.nextLine();
                if (password.equalsIgnoreCase("annulla")) {
                    System.out.println("Operazione di login annullata");
                    return false;
                }
            }
            if (tentativiPassword >= 3) {
                System.out.println("Raggiunto il numero massimo di tentativi. Operazione annullata.");
                return false;
            }
        }catch (SQLException | ClassNotFoundException e){
            System.out.println("Non esiste un account con questa password.");
        }
        String ruolo;
        String username_utente;
        try{
            ruolo = DBManagerStampa.prendiRuolo(email);
            username_utente = DBManagerStampa.prendiUsername(email);
            System.out.println("Login effettuato come "+ruolo);
            if(ruolo.equalsIgnoreCase("Cicerone")) {
                MenuCicerone.getInstance().menuCicerone(username_utente);
            }else if(ruolo.equalsIgnoreCase("Turista")){
                MenuTurista.getInstance().menuTurista(username_utente);
            }else if(ruolo.equalsIgnoreCase("Associazione")){
                MenuAssociazione.getInstance().menuAssociazione(username_utente);
            }else if(ruolo.equalsIgnoreCase("Amministrazione")){
                MenuAmministrazione.getInstance().menuAmministrazione();
            }
        }catch(SQLException | ClassNotFoundException e){
            System.out.println("Non esiste questo account.");
        }
        return true;
    }

    public boolean eseguiRegistrazione() throws IOException {
        boolean registrato = false;
        String id_risposta;
        String nome;
        String cognome;
        String username;
        String email;
        String partitaIVA;
        String password;
        System.out.println("Seleziona l'id della modalita' di registrazione che vuoi effettuare.");
        System.out.println("1 - Cicerone.");
        System.out.println("2 - Associazione.");
        System.out.println("3 - Turista.");
        id_risposta = scanner.nextLine();
        while(!id_risposta.equalsIgnoreCase("1") && !id_risposta.equalsIgnoreCase("2") && !id_risposta.equalsIgnoreCase("3")){
            System.out.println("Valore non riconosciuto. Reinserire id della modalità di registrazione che vuoi effettuare.");
            id_risposta = scanner.nextLine();
        }
        FileReader f = new FileReader ("C:\\Users\\Denise\\Desktop\\repos\\Cicero2\\src\\TerminiDiServizio");
        BufferedReader b = new BufferedReader(f);
        System.out.println ("TERMINI DI SERVIZIO");
        String line = b.readLine();
        while(line != null ) {
            System.out.println (line);
            line = b.readLine();
        }
        f.close();
        System.out.println("Accetti il documento per il trattamento dei dati? Inserire Si o No.");
        String risp = scanner.nextLine();
        if(risp.equalsIgnoreCase("no")){
            System.out.println ("Operazione di registrazione annullata.");
            return false;
        }else {
            while (!risp.equalsIgnoreCase("si") && !risp.equalsIgnoreCase("no")) {
                System.out.println("Risposta non valida. Reinserire la risposta.");
                risp = scanner.nextLine();
            }
        }
        if(id_risposta.equalsIgnoreCase("1")){
            System.out.println("Inserisci i dati per la registrazione:");
            System.out.println("Inserisci il tuo nome.");
            nome = scanner.nextLine();
            System.out.println("Inserisci il tuo cognome.");
            cognome = scanner.nextLine();
            System.out.println("Scegli un username (ricorda che l'username non puo' essere modificato una volta registrato. Tutti possono vederlo.)");
            username = scanner.nextLine();
            System.out.println("Inserisci l'email con la quale registrarti.");
            email = scanner.nextLine();
            Cicerone c = new Cicerone(nome, cognome, username, email);
            if(c.getNome() != null){
                registrato = true;
            }
        }else if(id_risposta.equalsIgnoreCase("2")){
            System.out.println("Inserisci i dati per la registrazione:");
            System.out.println("Inserisci nome dell'Associazione.");
            nome = scanner.nextLine();
            System.out.println("Inserisci l'email con la quale registrarti.");
            email = scanner.nextLine();
            System.out.println("Inserisci la tua partita IVA.");
            partitaIVA = scanner.nextLine();
            Associazione a = new Associazione(nome, email, partitaIVA);
            if(a.getNome() != null){
                registrato = true;
            }
        }else if(id_risposta.equalsIgnoreCase("3")){
            System.out.println("Inserisci i dati per la registrazione:");
            System.out.println("Inserisci il tuo nome.");
            nome = scanner.nextLine();
            System.out.println("Inserisci il tuo cognome.");
            cognome = scanner.nextLine();
            System.out.println("Scegli un username (ricorda che l'username non puo' essere modificato una volta registrato. Tutti possono vederlo.)");
            username = scanner.nextLine();
            System.out.println("Inserisci l'email con la quale registrarti.");
            email = scanner.nextLine();
            Turista t = new Turista(nome, cognome, username, email);
            if(t.getNome() != null){
                registrato = true;
            }
        }
        if(registrato == true){
            selezionaLingua();
            System.out.println("Operazione terminata con successo.");
            return true;
        }else{
            System.out.println("La registrazione non è andata a buon fine.");
            return false;
        }
    }

    public void selezionaLingua(){
        System.out.println("Inserisci l'id della lingua che vuoi utilizzare:");
        System.out.println("1 - Italiano");
        System.out.println("2 - English (coming soon)");
        System.out.println("3 - 语 (即将推出)");
        String lingua = scanner.nextLine();
        while(!lingua.equalsIgnoreCase("1")){
            System.out.println("Lingua non disponibile. Seleziona un'altra lingua.");
            lingua = scanner.nextLine();
        }
    }

    private boolean controlloStringa(String stringa){
        return Pattern.matches("[A-Za-z àèéòùì']*", stringa);
    }

    private boolean controlloStringaEmail (String stringa) {
        return Pattern.matches(".+@.+\\.[a-zA-Z0-9]+",stringa);
    }
}
