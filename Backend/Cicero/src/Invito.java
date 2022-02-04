import java.util.*;
import java.lang.Object;
import java.util.regex.Pattern;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Timer;
import java.time.*;


public class Invito {
    private final int id;
    private String nomeInvitato;
    private String cognomeInvitato;
    private String emailInvitato;
    private TourCalendario tour;
    private int postiRiservati;
    private Timer timer;
    private static int contatore = 0;
    private Scanner scanner = new Scanner(System.in);
    private Date dataDiCreazione;

    //sistemare id (anche di prenotazione)
    public Invito(TourCalendario tour, int postiRiservati) {
         System.out.println("Inserire il nome dell'invitato: ");
         this.nomeInvitato = scanner.nextLine();
        System.out.println("Inserire il cognome dell'invitato: ");
        this.cognomeInvitato = scanner.nextLine();
        while (!controlloStringa(nomeInvitato) || !controlloStringa(cognomeInvitato)) {
            if (!controlloStringa(nomeInvitato)  &&  !controlloStringa(cognomeInvitato)){
                System.out.println("Formato del nome e cognome non valido. Inserire un nuovo nome e cognome:");
                System.out.println("Inserisci nome");
                nomeInvitato = scanner.nextLine();
                System.out.println("Inserisci cognome");
                cognomeInvitato=scanner.nextLine();
            }  else if (!controlloStringa(nomeInvitato)) {
                System.out.println("Formato del nome non valido. Inserire un nuovo nome:");
                nomeInvitato = (scanner.nextLine());
            }
            else  {
                System.out.println("Formato del cognome non valido. Inserire un nuovo cognome:");
                cognomeInvitato = (scanner.nextLine());}
        }
        System.out.println("Inserire email dell'invitato: ");
        this.emailInvitato = scanner.nextLine();
        while (!controlloStringaEmail(emailInvitato)) {
            System.out.println("Email non valida:");
            System.out.println("Inserisci email");
            emailInvitato = scanner.nextLine();
        }
        this.tour = tour;
        this.postiRiservati = postiRiservati;
        contatore++;
        this.id = contatore;
        this.timer = new Timer(); //il timer viene creato senza partire, si avvierà al momento dell'invio dell'invito
        this.dataDiCreazione =new Date();  //Giorno corrente


    }

    private boolean controlloStringaEmail (String stringa) {
        return Pattern.matches(".+@.+\\.[a-zA-Z0-9]+",stringa);
    }

    private boolean controlloStringa(String stringa){

        return Pattern.matches("[A-Za-z àèéòùì']*", stringa);
    }
    /**
     * Metodo che controlla se è avvenuta la scadenza per accettare l'invito
     * @return true se l'invito è scaduto, false altrimenti
     */
    // TODO: 27/01/2022
    public boolean controlloScadenzaAccettazione(){

        return true;
    }

    /**
     * Metodo che serve per avviare il timer di scadenza per la conferma di un invito e nel momento in
     * cui scade, l'invito viene eliminato automaticamente dall'elenco degli inviti di un tour.
     *
     */

    public void avvioTimer(){
        TimerTask oggettoElimina = new eliminaInvito();
        Date dataScadenza = new Date();
        Calendar c = Calendar.getInstance();
        System.out.println("Data creazione: "+ dataDiCreazione);
        c.setTime(dataScadenza);
        c.add(Calendar.DATE, tour.getTour().getGiorniScadenzaInvito());
        dataScadenza = c.getTime();
        System.out.println("Data scadenza: "+ dataScadenza);
        this.timer.schedule(oggettoElimina, dataScadenza);
    }




    /**
     * Metodo per aggiungere un invito all'elenco delle prenotazioni
     * @return true se l'invito e' stato aggiunto, false altrimenti
     */
    public boolean aggiungiInvito () {
        tour.getElencoInviti().add(this);
        return true;
    }

    /**
     * Metodo per modificare i posti riservati di un invito
     * @param posti che si vogliono aggiungere a quelli gia' riservati con l'invito
     * @return true se i posti sono stati aggiunti, false altrimenti
     */
    // TODO: 27/01/2022
    public boolean modificaPosti(int posti){
        return true;
    }

    public int getId() {
        return id;
    }

    public String getNomeInvitato() {
        return nomeInvitato;
    }

    public void setNomeInvitato(String nomeInvitato) {
        this.nomeInvitato = nomeInvitato;
    }

    public String getCognomeInvitato() {
        return cognomeInvitato;
    }

    public void setCognomeInvitato(String cognomeInvitato) {
        this.cognomeInvitato = cognomeInvitato;
    }

    public String getEmailInvitato() {
        return emailInvitato;
    }

    public void setEmailInvitato(String emailInvitato) {
        this.emailInvitato = emailInvitato;
    }

    public TourCalendario getTour() {
        return tour;
    }

    public void setTour(TourCalendario tour) {
        this.tour = tour;
    }

    public int getPostiRiservati() {
        return postiRiservati;
    }

    public void setPostiRiservati(int postiRiservati) {
        this.postiRiservati = postiRiservati;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }

    public static int getContatore() {
        return contatore;
    }

    public static void setContatore(int contatore) {
        Invito.contatore = contatore;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public Date getDataDiCreazione() {
        return dataDiCreazione;
    }

    public void setDataDiCreazione(Date dataDiCreazione) {
        this.dataDiCreazione = dataDiCreazione;
    }

    private void mettiANull () {
        this.nomeInvitato = null;
        this.cognomeInvitato = null;
        this.emailInvitato = null;
        this.tour = null;
        this.postiRiservati = 0;
    }
    /**
     * Classe per creare una TimerTask che serve per eliminare un invito dall'elenco degli inviti di un tour
     * quando il timer scade.
     */
    private class eliminaInvito extends TimerTask{
        public void run(){
            tour.getElencoInviti().remove(this);
            tour.incrementaPostiDisponibili(postiRiservati);
            mettiANull();
            System.out.println("Invito Cancellato");
        }
    }
}
