package Boundary;
import Controller.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;
import Actors.UtenteGenerico;

import javax.swing.*;

public class MenuUtenteGenerico {

    private static MenuUtenteGenerico menuUtenteGenerico;
    private final Scanner scanner = new Scanner(System.in);
    private static boolean esci = false;

    private MenuUtenteGenerico() {
    }

    public static MenuUtenteGenerico getInstance() {
        if (menuUtenteGenerico == null) {
            menuUtenteGenerico = new MenuUtenteGenerico();
        }
        return menuUtenteGenerico;
    }

    public void menu(){
        String risposta;
        Scanner scanner = new Scanner (System.in);
        do {
            if (esci) {
                System.out.println("Uscita dal sistema");
                risposta = "0";
                scanner.close();
            } else {
                System.out.println("Cosa vuoi fare? Scrivi l'id dell'operazione che vuoi effettuare.");
                System.out.println("0 - Esci.");
                System.out.println("1 - Registrazione.");
                System.out.println("2 - Log in.");
                System.out.println("3 - Visualizza i tour guidati");
                //System.out.println("4 - Ricerca il tour."); //non implementato
                System.out.println("5 - Cambia la lingua del sistema.");
                risposta = scanner.nextLine();
                if (risposta.equals("")) {
                    risposta = scanner.nextLine();
                }
                switch (risposta) {
                    case "0":
                        setEsci();
                        scanner.close();
                        break;
                    case "1":
                        try {
                            UtenteController.getInstance().eseguiRegistrazione();
                        } catch (IOException e) {
                            System.out.println("ERRORE: non è possibile accedere al sistema.");
                        }
                        break;
                    case "2":
                        UtenteController.getInstance().eseguiLogin();
                        break;
                    case "3":
                        if (TourController.getInstance().stampaElencoTourConDettagli()) {
                            System.out.println("Per visualizzare le disponibilita di un tour inserire l'id del tour, altrimenti inserisci 0 per tornare al menu' principale: ");
                            int indiceTour = scanner.nextInt();
                            if (indiceTour == 0) {
                                break;
                            } else {
                                DisponibilitaController.getInstance().eseguiVisualizzaDisponibilitaDiUnTour(indiceTour);
                                System.out.println("Per prenotare un tour bisogna essere registrati come Turisti ed aver effettuato il login.");
                            }
                        } else {
                            System.out.println("Non ci sono tour da visualizzare.");
                        }
                        break;
                    case "4":
                        //da fare ricerca il tour
                        break;
                    case "5":
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
                        break;
                    default:
                        System.out.println("ERRORE: inserisci un id presente nella lista.");
                }
            }
        }while (!risposta.equalsIgnoreCase("0") && esci == false) ;
    }

    public static void setEsci(){
        esci = true;
    }
}
