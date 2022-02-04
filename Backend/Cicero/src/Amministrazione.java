import java.util.Scanner;

/**
 * Classe singleton per la creazione dell'amministrazione
 */
public class  Amministrazione {
    private static Amministrazione amministrazione ; //riferimento all'istanza
    private static String userId;
    private Scanner scanner = new Scanner (System.in);

    private Amministrazione (){ //costruttore
    this.userId = "Amministrazione";
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

        System.out.println("Nome del Tag: ");
        String nomeTag = scanner.nextLine();

        Tag tag = new Tag(nomeTag);
      if(!ListaTag.getInstance().controlloEsistenzaTag(tag)) {
          ListaTag.getInstance().aggiungiTagAllaLista(tag);
          return true;
      }
             System.out.println ("Esiste gia' un tag con il nome: "+nomeTag);
      return false;
    }

    /**
     * Metodo per creare un toponimo

     * @return true se il toponimo e' stato creato, false altriemnti
     */

    public boolean creaToponimo(){
        System.out.println ("Seleziona id del tipo di toponimo che si vuole creare: ");
        GestoreToponimi.getInstance().stampaElencoTipoToponimo();
        int id = scanner.nextInt();
        while (id < 0 || id > 6) {
            System.out.println ("Non esiste id selezionato. Scegliere un id tra 0 e 6 ");
            GestoreToponimi.getInstance().stampaElencoTipoToponimo();
            System.out.println ("Seleziona id del tipo di toponimo che si vuole creare: ");
             id = scanner.nextInt();
        }
         Scanner sc = new Scanner (System.in);
        System.out.println("Nome del Toponimo: ");
        String nomeToponimo = sc.nextLine();
        TipoToponimo t = TipoToponimo.ORONIMO;
        Toponimo toponimo = new Toponimo( t.getTipo(id), nomeToponimo);
        GestoreToponimi.getInstance().aggiungiToponimo(toponimo);
        return true;
    }

    /**
     * Metodo per rimborsare i soldi al turista
     * @param prenotazione da rimborsare
     */
    //TODO: 28/01/2022
    public void rimborsoSoldi(PrenotazioneTour prenotazione){
        System.out.println ("Il rimborso soldi avverra' tra i 5 e 7 giorni lavorativi");
    }

    public void stampa() {
    System.out.println(userId);
    }
}
