package Actors;

import Controller.TagController;
import Controller.ToponimiController;
import DBManagers.DBManagerInsert;
import DBManagers.DBManagerStampa;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Classe singleton per la creazione dell'amministrazione
 */
public class  Amministrazione {
    private static Amministrazione amministrazione ; //riferimento all'istanza
    private static final String ruolo = "Amministrazione";
    private Scanner scanner = new Scanner (System.in);

    private Amministrazione (){ //costruttore
        try{
            DBManagerInsert.InserisciDatiRegistrazione("cicero@amministrazione.it", "Amministrazione", "Amm1n1STr4z10n3", ruolo);
        }catch(SQLException | ClassNotFoundException e){
        }
    }

    public static Amministrazione getInstance(){
        if(amministrazione == null)
            amministrazione = new Amministrazione();

        return amministrazione;
    }

    /**
     * Metodo per creare un tag
     * @return true se il tag e' stato creato, false altrimenti
     */
    public boolean creaTag(){
        boolean esito = TagController.getInstance().eseguiCreaTag();
        if (esito == true) {
            System.out.println ("Il tag e' stato creato.");
            return true;
        } else {
            System.out.println ("Operazione annullata.");
            return false;
        }
    }

    public boolean eliminaTag(){
        boolean esito = TagController.getInstance().eseguiEliminaTag();
        if (esito == true) {
            System.out.println ("Il tag e' stato eliminato.");
            return true;
        } else {
            System.out.println ("Operazione annullata.");
            return false;
        }
    }

    public void accettaITag(){
        TagController.getInstance().eseguiAccettaITag();
    }

    /**
     * Metodo per creare un toponimo
     * @return true se il toponimo e' stato creato, false altriemnti
     */
    public boolean creaToponimo(){
        boolean esito = ToponimiController.getInstance().eseguiCreaToponimi();
        if (esito == true) {
            System.out.println ("Il toponimo e' stato creato.");
            return true;
        } else {
            System.out.println ("Operazione annullata.");
            return false;
        }
    }

    public boolean eliminaToponimo(){
        boolean esito = ToponimiController.getInstance().eseguiEliminaToponimo();
        if (esito == true) {
            System.out.println ("Il toponimo e' stato eliminato.");
            return true;
        } else {
            System.out.println ("Operazione annullata.");
            return false;
        }
    }

    /**
     * Metodo per rimborsare i soldi al turista
     * @param idPrenotazione da rimborsare
     */
    public void rimborsoSoldi(int idPrenotazione){
        System.out.println ("Il rimborso soldi avverra' tra i 5 e 7 giorni lavorativi");
    }

    public void visualizzaAvviso(String email) {
        try {
            DBManagerStampa.visualizzaITuoiAvvisi(email);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è stato possibile accedere al database");
        }
    }
}
