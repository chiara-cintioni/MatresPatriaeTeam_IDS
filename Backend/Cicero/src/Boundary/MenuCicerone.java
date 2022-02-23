package Boundary;
import Controller.*;
import java.sql.SQLException;
import java.util.Scanner;
import Actors.Cicerone;
import DBManagers.DBManagerStampa;

public class MenuCicerone {
    private static MenuCicerone menuCicerone;
    private final Scanner scanner = new Scanner(System.in);

    public MenuCicerone() {
    }

   public static MenuCicerone getInstance() {
        if (menuCicerone == null) {
            menuCicerone = new MenuCicerone();
        }
        return menuCicerone;
    }

    public void menuCicerone(String username) {
        Cicerone cicerone = new Cicerone();

        String email = "";
        String risposta;
        System.out.println("Bentornato: " + username);
        System.out.println("Cosa vuoi fare?");
        System.out.println("0 - Esci.");
        System.out.println("1 - Crea un tour.");
        System.out.println("2 - Modifica un tour.");
        System.out.println("3 - Aggiungi disponibilita'.");
        System.out.println("4 - Rimuovi disponibilita'.");
        System.out.println("5 - Proponi tag.");
        System.out.println("6 - Elimina tour.");
        System.out.println("7 - Visualizza i tuoi tour.");
        System.out.println("8 - Visualizza il tuo calendario delle disponibilita'.");
        System.out.println("9 - Visualizza gli avvisi e aggiornamenti sui tuoi tour.");
        System.out.println("10 - Cambia la lingua del sistema.");
        System.out.println("11 - Log out.");
        risposta = scanner.nextLine();
        if(risposta.equals("")){
            risposta = scanner.nextLine();
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
                cicerone.creaTour(email);
                menuCicerone(username);
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
                cicerone.aggiungiDisponibilita(email);
                menuCicerone(username);
                break;
            case "4":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                cicerone.rimuoviDisponibilita(email);
                menuCicerone(username);
                break;
            case "5":
                cicerone.proponiTag();
                menuCicerone(username);
                break;
            case "6":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                cicerone.eliminaTourDefinitivamente(email);
                menuCicerone(username);
                break;
            case "7":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                TourController.getInstance().stampaElencoTourCiceroneConDettagli(email);
                menuCicerone(username);
                break;
            case "8":
                try {
                    email = DBManagerStampa.prendiEmail(username);
                } catch (SQLException | ClassNotFoundException e) {
                    System.out.println("Errore: non è possibile accedere al database.");
                }
                DisponibilitaController.getInstance().eseguiMostraDisponibilita(email);
                menuCicerone(username);
                break;
            case "9":

                break;
            case "10":
                System.out.println("Seleziona l'id della lingua che vuoi utilizzare:");
                System.out.println("1 - Italiano");
                System.out.println("2 - English (coming soon)");
                System.out.println("3 - 语 (即将推出)");
                String lingua = scanner.nextLine();
                while (!lingua.equalsIgnoreCase("1")) {
                    System.out.println("Lingua non disponibile. Seleziona un'altra lingua.");
                    lingua = scanner.nextLine();
                }
                System.out.println("Lingua cambiata con successo");
                menuCicerone(username);
                break;
            case "11":
                System.out.println("Log out effettuato con successo.");
                MenuUtenteGenerico.getInstance().menu();
                risposta = "0";
                break;
            default:
                System.out.println("ERRORE: inserisci un id presente nella lista.");
                menuCicerone(username);
        }
    }
}
