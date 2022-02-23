package Controller;

import DBManagers.DBManagerInsert;
import DBManagers.DBManagerStampa;
import Entity.Avviso;

import java.sql.SQLException;
import java.util.Scanner;

public class AvvisoController {

    private static AvvisoController avvisoController;
    private Scanner scannerAvvisoController = new Scanner(System.in);

    private AvvisoController(){}

    public static AvvisoController getInstance() {
        if (avvisoController == null) {
            avvisoController = new AvvisoController();
        }
        return avvisoController;
    }

    public boolean eseguiInviaAvviso(String email, String stringa){
        try {
            Avviso avviso = new Avviso(stringa);
            DBManagerInsert.InserisciNellaTabellaAvvisi(stringa, email);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Non e' possibile accedere al database.");
            return false;
        }
        return true;
    }

    public void eseguiVisualizzaITuoiAvvisi(String email){
        try{
            DBManagerStampa.visualizzaITuoiAvvisi(email);
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
    }
}
