package Boundary;

import Actors.Associazione;
import Controller.DisponibilitaController;
import Controller.GuidaController;
import Controller.TourController;
import DBManagers.DBManagerStampa;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuAssociazione {
    private static MenuAssociazione menu;
    private final static Scanner scannerMenuAssociazione = new Scanner(System.in);

    private MenuAssociazione () {}

    public static MenuAssociazione getInstance( ) {
        if (menu == null) {
            menu = new MenuAssociazione();  }
        return menu;
    }

    public void menuAssociazione (String username) {
        Associazione associazione = new Associazione();
        String email = "";
        String risposta;
        System.out.println("Bentornato: " + username);
        System.out.println("Cosa vuoi fare?");
        System.out.println("0 - Esci.");
        System.out.println("1 - Crea un tour.");
        System.out.println("2 - Modifica un tour.");
        System.out.println("3 - Aggiungi disponibilita'.");
        System.out.println("4 - Rimuovi disponibilita'.");
        System.out.println("5 - Aggiungi guida alla tua lista di guide turistiche.");
        System.out.println("6 - Rimuovi guida dalla tua lista di guide turistiche.");
        System.out.println("7 - Proponi tag.");
        System.out.println("8 - Elimina tour.");
        System.out.println("9 - Visualizza i tuoi tour.");
        System.out.println("10 - Visualizza il tuo calendario delle disponibilita'.");
        System.out.println("11 - Visualizza la tua lista di guide turistiche.");
        System.out.println("12 - Visualizza gli avvisi e aggiornamenti sui tuoi tour.");
        System.out.println("13 - Cambia la lingua del sistema.");
        System.out.println("14 - Log out.");
        risposta = scannerMenuAssociazione.nextLine();
        if(risposta.equals("")){
            risposta = scannerMenuAssociazione.nextLine();
        }
        switch (risposta) {
            case "0":
                MenuUtenteGenerico.setEsci();
                risposta = "0";
                break;
            case "1":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                associazione.creaTour(email);
                menuAssociazione(username);
                break;
            case "2":
                MenuModificaTour.getInstance().menuModificaTour(username);
                break;
            case "3":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                associazione.aggiungiDisponibilita(email);
                menuAssociazione(username);
                break;
            case "4":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                associazione.rimuoviDisponibilita(email);
                menuAssociazione(username);
                break;
            case "5":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                associazione.aggiungiGuida(email, username);
                menuAssociazione(username);
                break;
            case "6":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                associazione.eliminaGuida(email);
                menuAssociazione(username);
                break;
            case "7":
                associazione.proponiTag();
                menuAssociazione(username);
                break;
            case "8":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                associazione.eliminaTourDefinitivamente(email);
                menuAssociazione(username);
                break;
            case "9":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                TourController.getInstance().stampaElencoTourCiceroneConDettagli(email);
                menuAssociazione(username);
                break;
            case "10":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                DisponibilitaController.getInstance().eseguiMostraDisponibilita(email);
                menuAssociazione(username);
                break;
            case "11":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                GuidaController.getInstance().eseguiVisualizzaGuideAssociazione(email);
                menuAssociazione(username);
                break;
            case "12":
                break;
            case "13":
                System.out.println("Seleziona l'id della lingua che vuoi utilizzare:");
                System.out.println("1 - Italiano");
                System.out.println("2 - English (coming soon)");
                System.out.println("3 - 语 (即将推出)");
                String lingua = scannerMenuAssociazione.nextLine();
                while (!lingua.equalsIgnoreCase("1")) {
                    System.out.println("Lingua non disponibile. Seleziona un'altra lingua.");
                    lingua = scannerMenuAssociazione.nextLine();
                }
                System.out.println("Lingua cambiata con successo");
                menuAssociazione(username);
                break;
            case "14":
                System.out.println("Log out effettuato con successo.");
                MenuUtenteGenerico.getInstance().menu();
                risposta = "0";
                break;
            default:
                System.out.println("ERRORE: inserisci un id presente nella lista.");
                menuAssociazione(username);
        }
    }
}
