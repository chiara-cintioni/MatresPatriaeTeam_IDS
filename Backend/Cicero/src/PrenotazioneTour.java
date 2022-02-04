import java.util.*;
import java.lang.Object;
import java.util.regex.Pattern;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.Timer;
import java.time.*;


//@Getter
/**
 * Classe che permette di creare una prenotazione a un tour. Una prenotazione ha: un id, uno statoPrenotazione (Variabile boolean che indica se la prenotazione può essere pagata o meno (si è raggiunto il numero minimo di
 * partecipanti)), numero dei posti che si vogliono prenotare con la prenotazione, il tour che si vuole prenotare e il turista che fa la prenotazione.
 */
public class PrenotazioneTour {


    private final int id;
    private static int contatoreTour = 0;
    private boolean statoPrenotazione; //booleano per verificare se un turista ha gia' pagato la prneotazione
    private boolean annullaGratis; //booleano per verificare se il turista puo' annullare una prenotazione senza pagare la mora
    private int numeroPostiDaPrenotare;
    private TourCalendario tourCalendario;
    private Timer scadenza = new Timer();
    private Turista turista;
    private String [] elencoDettagliPrenotati;
    private Scanner scanner = new Scanner(System.in);



    public PrenotazioneTour(TourCalendario tourCalendario, Turista turista, int numeroPostiDaPrenotare) {
        contatoreTour++;
        this.id = contatoreTour;
        this.annullaGratis = true;
        this.numeroPostiDaPrenotare = numeroPostiDaPrenotare;
        this.tourCalendario = tourCalendario;
        this.turista = turista;
        this.statoPrenotazione = false; //serve per sapere se il turista ha pagato la prenotazione
        this.elencoDettagliPrenotati = new String[numeroPostiDaPrenotare];
        for (int i = 0; i<elencoDettagliPrenotati.length; i++) {
            elencoDettagliPrenotati[i] = aggiungiDettagliPersona();
        }
        avvioTimerScadenza();
    }

    /**
     * Metodo per avviare il timer per la scadenza della conferma a un invito
     */
    public void avvioTimerScadenza () {
        TimerTask cambioStato = new modicaAnnullaGratis();
        Date data = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(data);
        c.add(Calendar.DATE, 10);
        data = c.getTime();
        this.scadenza.schedule(cambioStato, data);
    }

    /**
     * Metodo per inserire i dettagli delle persone che inserisco nella prenotazione
     */
    public String aggiungiDettagliPersona () {
        System.out.println("Inserire i dati per ogni persona presente nel numero di prenotazione.");
        System.out.println("Inserire nome della persona da inserire nella prenotazione: ");
        String nome;
        nome = scanner.nextLine();
        System.out.println("Inserire cognome della persona da inserire nella prenotazione: ");
        String cognome;
        cognome = scanner.nextLine();
        while (!controlloStringa(nome) || !controlloStringa(cognome)) {
            if (!controlloStringa(nome)  &&  !controlloStringa(cognome)){
                System.out.println("Formato del nome e cognome non valido. Inserire un nuovo nome e cognome:");
                System.out.println("Inserisci nome");
                nome = scanner.nextLine();
                System.out.println("Inserisci cognome");
                cognome=scanner.nextLine();
            }  else if (!controlloStringa(nome)) {
                System.out.println("Formato del nome non valido. Inserire un nuovo nome:");
                nome = (scanner.nextLine());
            }
            else  {
                System.out.println("Formato del cognome non valido. Inserire un nuovo cognome:");
                cognome = (scanner.nextLine());}

              }
String s =  "[nome: "+nome+", cognome: "+cognome+"]";
        return s;
        }

    private boolean controlloStringa(String stringa){

        return Pattern.matches("[A-Za-z àèéòùì']*", stringa);
    }



    /**
     * Metodo che invoca l'amministrazione che si occupa di provvedere al rimborso per il turista
     *
     */
       public void richiestaRimborso(){
        Amministrazione.getInstance().rimborsoSoldi(this);
       }


    /**
     * Metodo che modifica lo sttao di una prenotazione.
     *
     * @param statoPrenotazione che si vuole mettere come valore nuovo.
     */
    public void setStatoPrenotazione(boolean statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }


    /**
     * Metodo che elimina una prenotazione effettuata.
     * @return true se la prenotazione viene eliminata correttamente.
     */
    public boolean eliminaPrenotazione(){
        tourCalendario.getElencoPrenotazioni().remove(this);
        this.tourCalendario = null;
        this.turista = null;
        return true;
    }

    /**
     * Metodo per aggiungere una prenotazione all'elenco delle prenotazioni di un tour
     * @return true se la prneotazione e' stata aggiunta all'elenco delle prenotazione, false altriemnti;
     */
    public boolean aggiungiPrenotazione () {
        tourCalendario.getElencoPrenotazioni().add(this);
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrenotazioneTour that = (PrenotazioneTour) o;
        return statoPrenotazione == that.statoPrenotazione && numeroPostiDaPrenotare == that.numeroPostiDaPrenotare && Objects.equals(id, that.id) && Objects.equals(tourCalendario, that.tourCalendario);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Timer getScadenza() {
        return scadenza;
    }

    public void setScadenza(Timer scadenza) {
        this.scadenza = scadenza;
    }

    public String[] getElencoDettagliPrenotati() {
        return elencoDettagliPrenotati;
    }

    public void setElencoDettagliPrenotati(String[] elencoDettagliPrenotati) {
        this.elencoDettagliPrenotati = elencoDettagliPrenotati;
    }

    public static int getContatoreTour() {
        return contatoreTour;
    }
    public static void setContatoreTour(int contatoreTour) {
        PrenotazioneTour.contatoreTour = contatoreTour;
    }


    public int getNumeroPostiDaPrenotare() {
        return numeroPostiDaPrenotare;
    }


    public void setNumeroPostiDaPrenotare(int numeroPostiDaPrenotare) {
        this.numeroPostiDaPrenotare = numeroPostiDaPrenotare;
    }


    public TourCalendario getTourCalendario() {
        return tourCalendario;
    }


    public void setTourCalendario(TourCalendario tourCalendario) {
        this.tourCalendario = tourCalendario;
    }


    public Turista getTurista() {
        return turista;
    }


    public void setTurista(Turista turista) {
        this.turista = turista;
    }


    public int getId() {
        return id;
    }


    public boolean isStatoPrenotazione() {
        return statoPrenotazione;
    }



    public boolean isAnnullaGratis() {
        return annullaGratis;
    }

    public void setAnnullaGratis(boolean annullaGratis) {
        this.annullaGratis = annullaGratis;
    }
    private class modicaAnnullaGratis extends TimerTask {
        public void run(){
            annullaGratis = false;
        }
    }

    @Override
    public String toString() {
        return "PrenotazioneTour{" +
                "id=" + id +
                ", statoPrenotazione=" + statoPrenotazione +
                ", numeroPostiDaPrenotare=" + numeroPostiDaPrenotare +
                ", tourCalendario=" + tourCalendario +
                ", scadenza=" + scadenza +
                ", turista=" + turista +
                ", elencoDettagliPrenotati=" + Arrays.toString(elencoDettagliPrenotati) +
                ", scanner=" + scanner +
                '}';
    }
}


