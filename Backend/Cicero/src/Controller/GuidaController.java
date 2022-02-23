package Controller;

import DBManagers.*;
import Entity.GuidaAssociazione;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GuidaController {
    private static GuidaController guidaController;
    private Scanner scannerGuidaController = new Scanner(System.in);

    private GuidaController(){}

    public static GuidaController getInstance() {
        if (guidaController == null) {
            guidaController = new GuidaController();
        }
        return guidaController;
    }

    public boolean eseguiCreaGuida (String email, String nomeAssociazione) {
        String nome;
        String cognome;
        String descrizione;
        String username;
        System.out.println ("Inserire nome della guida: ");

        nome = scannerGuidaController.nextLine();
        if(nome.equalsIgnoreCase("")){
            nome = scannerGuidaController.nextLine();
        }
        while(!controlloDati(nome)) {
            System.out.println ("Nome della guida non valido, inserire nome valido: ");
            nome = scannerGuidaController.nextLine();
        }
        System.out.println ("Inserire cognome della guida: ");
        cognome = scannerGuidaController.nextLine();
        if(cognome.equalsIgnoreCase("")){
            cognome = scannerGuidaController.nextLine();
        }
        while (!controlloDati(cognome)) {
            System.out.println ("Cognome della guida non valido, inserire cognome valido: ");
            cognome = scannerGuidaController.nextLine();
        }
        try {
            username = generaUsername(nomeAssociazione, nome, cognome);
            System.out.println ("Inserire informazioni e contatti della guida: ");
            descrizione = scannerGuidaController.nextLine();
            GuidaAssociazione guida = new GuidaAssociazione(nome, cognome, username, descrizione, email);
            DBManagerInsert.InserisciNellaTabellaGuide(nome, cognome, username, descrizione, email);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Il database non risponde. Riprova più tardi.");
            return false;
        }
        return true;
    }

    private String generaUsername(String nomeAssociazione, String nome, String cognome){
        String username;
        return username = nomeAssociazione+" (Guida: "+nome+"."+cognome+")";
    }

    public String prendiNomeGuidaDaId(String emailAssociazione, int indice){
        try{
            String nomeGuida = DBManagerStampa.selezionaGuidaDaIndice(indice);
            return nomeGuida;
        }catch(SQLException | ClassNotFoundException e){
            System.out.println("Non e' possibile contattare il database.");
            return null; }
    }

    public int selezionaGuida(String emailAssociazione){
        int indiceGuida;
        System.out.println("Seleziona una guida dal tuo elenco: ");
        try {
            DBManagerStampa.visualizzaIdGuidaDaElenco(emailAssociazione);
            System.out.println("Seleziona l'id della guida che ti interessa: ");
            indiceGuida = scannerGuidaController.nextInt();
            while(!DBManagerConfronti.confrontaIdGuida(indiceGuida)){
                System.out.println("Non esiste una guida con questo id. Inserisci un nuovo id.");
                indiceGuida = scannerGuidaController.nextInt();
            }
            return indiceGuida;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Il database non risponde. Ritenta tra 5 minuti.");
            return -1;
        }
    }

    public int selezionaGuida2(String emailAssociazione, String data){
        int indiceGuida;
        System.out.println("Seleziona una guida dal tuo elenco: ");
        try {
            DBManagerStampa.visualizzaGuidaDaElencoSenzaVecchiaGuida(emailAssociazione, data);
            System.out.println("Seleziona l'id della guida che ti interessa: ");
            indiceGuida = scannerGuidaController.nextInt();
            while(!DBManagerConfronti.confrontaIdGuida(indiceGuida)){
                System.out.println("Non esiste una guida con questo id. Inserisci un nuovo id.");
                indiceGuida = scannerGuidaController.nextInt();
            }
            return indiceGuida;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Il database non risponde. Ritenta tra 5 minuti.");
            return -1;
        }
    }

    /**
     * Metodo che ritorna la guida che l'associazione ha scelto di inserire in una determinata data. Questo metodo viene richiamato in:
     * rimuovi guida dalla lista e aggiungi disponibilita'.
     * @param email dell'Associazione
     * @param data della disponibilita'
     * @return il nome di una guida disponibile in quella data, scelta dall'Associazione.
     */
    public String eseguiAssegnaGuida(String email, String data){
        int indiceGuida = GuidaController.getInstance().selezionaGuida(email);
        String nomeGuida = GuidaController.getInstance().prendiNomeGuidaDaId(email,indiceGuida);
        if(nomeGuida == null){
            System.out.println("La guida non esiste. Scegliere un'altra guida.");
            indiceGuida = GuidaController.getInstance().selezionaGuida(email);
            nomeGuida = GuidaController.getInstance().prendiNomeGuidaDaId(email,indiceGuida);
        }
        try {
            while(DBManagerConfronti.esistenzaDiUnTourInQuellaDataPerGuidaAssociazione(nomeGuida,data,email)){
                System.out.println("La guida ha gia' un tour in questa data. Per inserire un'altra guida inserisci 1, altrimenti verra' annullata l'operazione.");
                String ris = scannerGuidaController.nextLine();
                if(ris.equalsIgnoreCase("")){
                    ris = scannerGuidaController.nextLine();
                }
                if(!ris.equalsIgnoreCase("1")){
                    return null;
                }
                indiceGuida = GuidaController.getInstance().selezionaGuida(email);
                nomeGuida = GuidaController.getInstance().prendiNomeGuidaDaId(email,indiceGuida);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non e' stato possibile accedere al database. Ci scusiamo per il disagio.");
        }
        return nomeGuida;
    }

    /**
     * Metodo di assegna guida che non stampa in selezione la vecchia guida
     * @param email
     * @param data
     * @param vecchioNome
     * @return
     */
    private String eseguiAssegnaGuida(String email, String data, String vecchioNome){
        int indiceGuida = GuidaController.getInstance().selezionaGuida2(email, data);
        String nomeGuida = GuidaController.getInstance().prendiNomeGuidaDaId(email,indiceGuida);
        if(nomeGuida == null){
            System.out.println("La guida non esiste. Scegliere un'altra guida.");
            indiceGuida = GuidaController.getInstance().selezionaGuida2(email, data);
            nomeGuida = GuidaController.getInstance().prendiNomeGuidaDaId(email,indiceGuida);
        }
        try {
            while(DBManagerConfronti.esistenzaDiUnTourInQuellaDataPerGuidaAssociazione(nomeGuida,data,email)){
                System.out.println("La guida ha gia' un tour in questa data. Per inserire un'altra guida inserisci 1, altrimenti verra' annullata l'operazione.");
                String ris = scannerGuidaController.nextLine();
                if(ris.equalsIgnoreCase("")){
                    ris = scannerGuidaController.nextLine();
                }
                if(!ris.equalsIgnoreCase("1")){
                    return null;
                }
                indiceGuida = GuidaController.getInstance().selezionaGuida(email);
                nomeGuida = GuidaController.getInstance().prendiNomeGuidaDaId(email,indiceGuida);
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Non e' stato possibile accedere al database. Ci scusiamo per il disagio.");
        }
        return nomeGuida;
    }

    public boolean eseguiRimuoviGuidaDallaLista(String email){
        int indiceGuida = selezionaGuida(email);
        String nomeGuida;
        int [] elencoDisponibilita;
        try {
            nomeGuida = DBManagerStampa.selezionaGuidaDaIndice(indiceGuida);
            //Controlla se la guida da rimuovere ha delle disponibilita'
            if(DBManagerConfronti.confrontaDisponibilitaGuida(nomeGuida)){
                elencoDisponibilita = DBManagerStampa.prendiIndiceDisponibilitaPerGuidaAssociazione(email, nomeGuida);
                if(!DBManagerConfronti.esistenzaDelleGuide(email,nomeGuida)){
                    System.out.println("Vuoi eliminare l'operazione di elimina guida o vuoi eliminare tutte le disponibilita' della guida? Inserisci 1 per annullare l'operazione, 2 per eliminare le disponibilita'.");
                    String ris = scannerGuidaController.nextLine();
                    if(ris.equals("")){
                        ris = scannerGuidaController.nextLine();
                    }
                    while(!ris.equals("1") && !ris.equals("2")){
                        System.out.println("Valore non valido. Inserisci 1 (annulla operazione di elimina guida) o 2 (elimina tutte le disponibilita della guida).");
                        ris = scannerGuidaController.nextLine();
                    }
                    if(ris.equals("1")){
                        return false;
                    }else{
                        for(int i = 0; i < elencoDisponibilita.length; i++){
                            DisponibilitaController.getInstance().eseguiRimuoviDisponibilita2(email, elencoDisponibilita[i]);
                        }
                    }
                }
                else {
                    System.out.println("Ogni disponibilita della guida va assegnata ad una guida diversa: ");
                    for(int i=0; i<elencoDisponibilita.length; i++){
                        String data = DBManagerStampa.prendiDataTourDaTabellaDisponibilita(email,elencoDisponibilita[i]);
                        boolean stampa = DBManagerStampa.visualizzaGuidaDaElencoSenzaVecchiaGuida(email, data);
                        if(!stampa){
                            System.out.println("Non essendo presenti guide disponibili per la sostituzione della guida nella data: "+data+", procederemo alla rimozione della disponibilità.");
                                DisponibilitaController.getInstance().eseguiRimuoviDisponibilita2(email, elencoDisponibilita[i]);
                        }else{
                            System.out.println("Disponibilita con data: "+data);
                            String nuovoNome = eseguiAssegnaGuida(email, data, nomeGuida);
                            if(nuovoNome == null){
                                System.out.println("Non è stato possibile eliminare la guida.");
                                return false;
                            }
                            DBManagerUpdate.cambiaNomeGuidaAllaDisponibilita(data, email, nomeGuida, nuovoNome);
                        }

                    }
                }
            }
            DBManagerDelete.RimuoviGuidaDallaTabella (indiceGuida);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Il database non e' raggiungibile.");
            return false;
        }
        return true;
    }

    public void eseguiVisualizzaGuideAssociazione(String emailAssociazione){
        try {
            DBManagerStampa.visualizzaInformazioniGuidaAssociazione(emailAssociazione);

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Il database non risponde. Ritenta tra 5 minuti.");
        }
    }

    private boolean controlloDati(String stringa) {
        if (!controlloStringa(stringa)) {
            return false;
        }
        return true;
    }

    private boolean controlloStringa(String stringa){
        return Pattern.matches("[A-Za-z0-9 àèéòùì']*", stringa);
    }
}
