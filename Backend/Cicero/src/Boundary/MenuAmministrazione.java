package Boundary;

import Controller.TagController;
import Controller.ToponimiController;
import Actors.Amministrazione;

import java.util.Scanner;

public class MenuAmministrazione {
    private static MenuAmministrazione menuAmministrazione;
    private final Scanner scannerMenuAmministrazione = new Scanner(System.in);

    public MenuAmministrazione() {
    }

    public static MenuAmministrazione getInstance() {
        if (menuAmministrazione== null) {
            menuAmministrazione = new MenuAmministrazione();
        }
        return menuAmministrazione;
    }

    public void menuAmministrazione(){
        Amministrazione amministrazione = Amministrazione.getInstance();
        String risposta;
        String email = "cicero@amministrazione.it";
        System.out.println("Bentornata Amministrazione.");
        do{
            System.out.println ("Cosa vuoi fare?");
            System.out.println("0 - Esci dal sistema.");
            System.out.println("1 - Crea tag.");
            System.out.println("2 - Controlla i tag proposti.");
            System.out.println("3 - Crea toponimo.");
            System.out.println("4 - Elimina tag.");
            System.out.println("5 - Elimina toponimo.");
            System.out.println("6 - Visualizza tag.");
            System.out.println("7 - Visualizza toponimi.");
            System.out.println("8 - Visualizza i tuoi avvisi.");
            System.out.println("9 - Log out.");
            risposta = scannerMenuAmministrazione.nextLine();
            if(risposta.equals("")){
                risposta = scannerMenuAmministrazione.nextLine();
            }

            switch (risposta) {
                case "0":
                    MenuUtenteGenerico.setEsci();
                    risposta = "0";
                    break;
                case "1":
                    amministrazione.creaTag();
                    break;
                case "2":
                    amministrazione.accettaITag();
                    break;
                case "3":
                    amministrazione.creaToponimo();
                    break;
                case "4":
                    amministrazione.eliminaTag();
                    break;
                case "5":
                    amministrazione.eliminaToponimo();
                    break;
                case "6":
                    TagController.getInstance().visualizzaTag();
                    break;
                case "7":
                    ToponimiController.getInstance().visualizzaTuttiIToponimi();
                    break;
                case"8":
                    amministrazione.visualizzaAvviso(email);
                    break;
                case "9":
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

