package Controller;

import DBManagers.DBManagerConfronti;
import DBManagers.DBManagerDelete;
import DBManagers.DBManagerInsert;
import DBManagers.DBManagerStampa;
import Entity.Avviso;
import Entity.Tag;
import Entity.TagProposti;

import java.sql.SQLException;
import java.util.Scanner;

public class TagController {
    private static TagController tagController;
    private Scanner scannerTagController = new Scanner(System.in);

    private TagController(){}

    public static TagController getInstance() {
        if (tagController == null) {
            tagController = new TagController();
        }
        return tagController;
    }

    public boolean eseguiCreaTag(){
        System.out.println("Inserisci il nome del tag che vuoi creare: ");
        String nomeTag = scannerTagController.nextLine();
        if(nomeTag.equalsIgnoreCase("")){
            nomeTag = scannerTagController.nextLine();
        }
        try {
            if(DBManagerConfronti.confrontoEsistenzaTag(nomeTag)){
                System.out.println("Esiste già un tag con questo nome.");
                return false;
            }
            Tag tag = new Tag(nomeTag);
            DBManagerInsert.InserisciNellaTabellaTag(nomeTag);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile interagire con il database.");
            return false;
        }
    }

    public boolean eseguiProponiTag(){
        System.out.println("Inserisci il nome del tag che vuoi proporre: ");
        String nomeTag = scannerTagController.nextLine();
        if(nomeTag.equalsIgnoreCase("")){
            nomeTag = scannerTagController.nextLine();
        }
        try {
            TagProposti tag = new TagProposti(nomeTag);
            DBManagerInsert.InserisciNellaTabellaTagProposti(nomeTag);
            String avviso = "Hai almeno un tag da controllare!";
            Avviso avviso1 = new Avviso(avviso);
            String emailAmministrazione = "cicero@amministrazione.it";
            if(DBManagerConfronti.confrontoEsistenzaAvviso(avviso,emailAmministrazione)){
                return true;
            }else{
                AvvisoController.getInstance().eseguiInviaAvviso(emailAmministrazione,avviso);
                return true;
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile interagire con il database.");
            return false;
        }
    }

    public boolean eseguiEliminaTag(){
        if(!esistenzaTag()){
            System.out.println("Non ci sono tag da eliminare.");
            return false;
        }
        visualizzaTagEId();
        System.out.println("Inserisci l'id del tag che vuoi eliminare: ");
        int id = scannerTagController.nextInt();
        try {
            DBManagerDelete.RimuoviTag(id);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile interagire con il database.");
            return false;
        }
    }

    private boolean esistenzaTag(){
        try{
            boolean risposta = DBManagerConfronti.esistenzaTagNellaTabella();
            return risposta;
        }catch(SQLException | ClassNotFoundException e){
            return false;
        }
    }

    public void visualizzaTag(){
        try {
            DBManagerStampa.visualizzaTag();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
    }

    private void visualizzaTagEId(){
        try {
            DBManagerStampa.visualizzaTagConId();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }
    }

    public void eseguiAccettaITag(){
        System.out.println("Per ogni tag proposto inserisci 'si' se vuoi accettare il tag, 'no' altrimenti. " +
                "(Qualsiasi altro valore non comporta l'accettazione o rifiuto del tag.");
        int idTagProposto = 0;
        try{
            while(DBManagerConfronti.esistenzaTagProposti()){
                idTagProposto = DBManagerStampa.IdTagPropostoMinimo();
                String nomeTag = DBManagerStampa.prendiNomeTagProposto(idTagProposto);
                System.out.println("Vuoi accettare il tag: "+nomeTag);
                String risposta = scannerTagController.nextLine();
                if(risposta.equalsIgnoreCase("")){
                    risposta = scannerTagController.nextLine();
                }
                if(risposta.equalsIgnoreCase("si")){
                    DBManagerInsert.InserisciNellaTabellaTag(nomeTag);
                    DBManagerDelete.RimuoviTagProposti(idTagProposto);
                }else if (risposta.equalsIgnoreCase("no")){
                    DBManagerDelete.RimuoviTagProposti(idTagProposto);
                }
            }
        }catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non è possibile accedere al database.");
        }

    }
}
