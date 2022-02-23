package Controller;

import DBManagers.DBManagerConfronti;
import DBManagers.DBManagerDelete;
import DBManagers.DBManagerInsert;
import DBManagers.DBManagerStampa;
import Entity.TipoToponimo;
import Entity.Toponimo;

import java.sql.SQLException;
import java.util.Scanner;

public class ToponimiController {
    private static ToponimiController toponimiController;
    private static Scanner scannerToponimiController = new Scanner(System.in);

    private ToponimiController(){
    }

    public static ToponimiController getInstance(){
        if(toponimiController == null){
            toponimiController = new ToponimiController();
        }
        return toponimiController;
    }

    private boolean esistenzaToponimi(String tipo){
        try{
            boolean risposta = DBManagerConfronti.esistenzaToponimiNellaTabella(tipo);
            return risposta;
        }catch(SQLException | ClassNotFoundException e){
            return false;
        }
    }

    public boolean eseguiCreaToponimi(){
        System.out.println("Inserisci il nome del toponimo che vuoi creare: ");
        String nomeToponimo = scannerToponimiController.nextLine();
        if(nomeToponimo.equalsIgnoreCase("")){
            nomeToponimo = scannerToponimiController.nextLine();
        }
        System.out.println("Seleziona il tipo di toponimo: ");
        visualizzaTipiToponimi();
        int risposta = scannerToponimiController.nextInt();
        TipoToponimo tipoToponimo = TipoToponimo.ORONIMO;
        String tipo = tipoToponimo.getTipo(risposta).toString();
        try {
            if(DBManagerConfronti.confrontoEsistenzaToponimi(nomeToponimo,tipo)){
                System.out.println("Esiste già un toponimo con questo nome.");
                return false;
            }
            Toponimo toponimo = new Toponimo(tipoToponimo, nomeToponimo);
            DBManagerInsert.InserisciNellaTabellaToponimi(nomeToponimo, tipo);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile interagire con il database.");
            return false;
        }
    }

    public boolean eseguiEliminaToponimo(){
        String tipo = visualizzaIdToponimiPerTipo();
        if(tipo == null){
            return false;
        }
        if(!esistenzaToponimi(tipo)){
            System.out.println("Non ci sono toponimi da eliminare per questo tipo di toponimi.");
            return false;
        }
        System.out.println("Inserisci l'id del tag che vuoi eliminare: ");
        int id = scannerToponimiController.nextInt();
        try {
            if(id < 0 || id > 6){
                System.out.println("Valore non valido.");
                return false;
            }
            DBManagerDelete.RimuoviToponimo(id);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            return false;
        }
    }

    private void visualizzaTipiToponimi(){
        System.out.println("I tipi di toponimi sono:");
        System.out.println("0 - Oronimo (Rilievi montuosi).");
        System.out.println("1 - Poleonimo (Centri abitati).");
        System.out.println("2 - Coronimo (Regioni).");
        System.out.println("3 - Nesonimo (Isole).");
        System.out.println("4 - Limnonimo (Laghi).");
        System.out.println("5 - Idronimo (Corsi d'acqua).");
        System.out.println("6 - Odonimo (Vie, Piazze, Strade).");
    }

    public void visualizzaTuttiIToponimi(){
        try {
            DBManagerStampa.visualizzaTuttiIToponimi();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è stato possibile accedere al database.");
        }
    }

    private String visualizzaIdToponimiPerTipo(){
        System.out.println("Inserisci il numero corrispondente alla lista dei toponimi che vuoi visualizzare");
        visualizzaTipiToponimi();
        int id = scannerToponimiController.nextInt();
        if(id < 0 || id > 6){
            System.out.println("Valore non valido.");
            return null;
        }
        TipoToponimo tipoToponimo = TipoToponimo.ORONIMO;
        String tipo = tipoToponimo.getTipo(id).toString();
        try {
            DBManagerStampa.visualizzaIdToponimoPerTipo(tipo);
            return tipo;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è stato possibile accedere al database.");
            return null;
        }
    }

}
