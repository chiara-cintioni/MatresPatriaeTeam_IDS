package Boundary;

import Controller.TourController;
import DBManagers.DBManagerStampa;
import Actors.Turista;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuTurista {
   private static MenuTurista menu;
   private static Scanner scanner = new Scanner (System.in);

   private MenuTurista () {}

   public static MenuTurista getInstance( ) {
       if (menu == null) {
           menu = new MenuTurista();
       }
       return menu;
   }

   public void menuTurista (String username) {
       String risposta;
       String email = "";
       Turista turista = new Turista();
       System.out.println ("Bentornato: " + username);
       do{
           System.out.println ("Cosa vuoi fare?");
           System.out.println("0 - Esci.");
           System.out.println("1 - Prenota un tour.");
           System.out.println("2 - Visualizza i tour guidati");
           //System.out.println("3 - Ricerca il tour.");
           System.out.println("4 - Annulla una prenotazione.");
           System.out.println("5 - Visualizza tour prenotati.");
           System.out.println("6 - Paga un tour.");
           System.out.println("7 - Accetta invito a un tour.");
           System.out.println("8 - Visiona avvisi e aggiornamenti sulle tue prenotazioni.");
           System.out.println("9 - Cambia la lingua del sistema.");
           System.out.println("10 - Log out.");
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
                   turista.eseguiPrenotazione(email);
                   break;
               case "2":
                   TourController.getInstance().eseguiVisualizzaTour();
                   break;
               case "3":
                   //da fare ricerca tour
                   break;
               case "4":
                   try {
                       email = DBManagerStampa.prendiEmail(username);
                   } catch (SQLException | ClassNotFoundException e) {
                       System.out.println("Errore: non è possibile accedere al database.");
                   }
                   turista.annullaPrenotazione(email);
                   break;
               case "5":
                   try {
                       email = DBManagerStampa.prendiEmail(username);
                   } catch (SQLException | ClassNotFoundException e) {
                       System.out.println("Errore: non è possibile accedere al database.");
                   }
                  turista.visualizzaPrenotazioni(email);
               case "6":
                    //non implementato
                   break;
               case "7":
                   break;
               case "8":
                   try {
                       email = DBManagerStampa.prendiEmail(username);
                   } catch (SQLException | ClassNotFoundException e) {
                       System.out.println("Errore: non è possibile accedere al database.");
                   }
                   turista.visualizzaAvvisi(email);
                   break;
               case "9":
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
               case "10":
                   System.out.println("Log out effettuato con successo.");
                   MenuUtenteGenerico.getInstance().menu();
                   risposta = "0";
                   break;
               default:
                   System.out.println("ERRORE: inserisci un id presente nella lista.");
           }
       }while(!risposta.equalsIgnoreCase("0"));
   }
}
