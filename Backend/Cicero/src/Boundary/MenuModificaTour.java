package Boundary;

import Actors.Associazione;
import DBManagers.DBManagerStampa;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuModificaTour {
    private static MenuModificaTour menuModificaTour;
    private final Scanner scannerModificaTour = new Scanner(System.in);

    public MenuModificaTour() {
    }

    public static MenuModificaTour getInstance() {
        if (menuModificaTour == null) {
            menuModificaTour = new MenuModificaTour();
        }
        return menuModificaTour;
    }

    public void menuModificaTour(String username){
        Associazione associazione = new Associazione();
        String risposta;
        String email = "";
        String ruolo;
        do{
            System.out.println ("Quale modifica vuoi effettuare?");
            System.out.println("0 - Torna alla home.");
            System.out.println("1 - Modifica nome di un tour.");
            System.out.println("2 - Modifica prezzo di un tour.");
            System.out.println("3 - Modifica il numero di posti di un tour.");
            System.out.println("4 - Modifica data disponibilita del tour.");
            System.out.println("5 - Modifica descrizione di un tour.");
            System.out.println("6 - Aggiungi una sosta ad un tour.");
            System.out.println("7 - Elimina una sosta da un tour.");
            risposta = scannerModificaTour.nextLine();
            // TODO: 14/02/2022
            switch (risposta) {
                case "0":
                    try {
                        email = DBManagerStampa.prendiEmail(username);
                        ruolo = DBManagerStampa.prendiRuolo(email);
                        if(ruolo.equalsIgnoreCase("Cicerone")){
                            MenuCicerone.getInstance().menuCicerone(username);
                        }else if(ruolo.equalsIgnoreCase("Associazione")){
                            MenuAssociazione.getInstance().menuAssociazione(username);
                        }
                    } catch (SQLException | ClassNotFoundException e) {
                        System.out.println("Errore: non è possibile accedere al database.");
                    }
                    break;
                case "1":
                    try {
                        email = DBManagerStampa.prendiEmail(username);
                    } catch (SQLException | ClassNotFoundException e) {
                        System.out.println("Errore: non è possibile accedere al database.");
                    }
                    associazione.modificaNomeDelTour(email);
                    break;
                case "2":
                    try {
                        email = DBManagerStampa.prendiEmail(username);
                    } catch (SQLException | ClassNotFoundException e) {
                        System.out.println("Errore: non è possibile accedere al database.");
                    }
                    associazione.modificaPrezzoTour(email);
                    break;
                case "3":
                    try {
                        email = DBManagerStampa.prendiEmail(username);
                    } catch (SQLException | ClassNotFoundException e) {
                        System.out.println("Errore: non è possibile accedere al database.");
                    }
                    associazione.modificaPostiTour(email);
                    break;
                case "4":
                    try {
                        email = DBManagerStampa.prendiEmail(username);
                    } catch (SQLException | ClassNotFoundException e) {
                        System.out.println("Errore: non è possibile accedere al database.");
                    }
                    associazione.modificaData(email);
                    break;
                case "5":
                    try {
                        email = DBManagerStampa.prendiEmail(username);
                    } catch (SQLException | ClassNotFoundException e) {
                        System.out.println("Errore: non è possibile accedere al database.");
                    }
                    associazione.modificaDescrizioneDelTour(email);
                    break;
                case "6":
                try {
                        email = DBManagerStampa.prendiEmail(username);
                    } catch (SQLException | ClassNotFoundException e) {
                        System.out.println("Errore: non è possibile accedere al database.");
                    }
                    associazione.aggiungiSosta(email);
                    break;
                case "7":
                    try {
                        email = DBManagerStampa.prendiEmail(username);
                    } catch (SQLException | ClassNotFoundException e) {
                        System.out.println("Errore: non è possibile accedere al database.");
                    }
                    associazione.eliminaSosta(email);
                    break;

                default:
                    System.out.println("ERRORE: inserisci un id presente nella lista.");
            }
        }while(!risposta.equalsIgnoreCase("0"));
    }
}
