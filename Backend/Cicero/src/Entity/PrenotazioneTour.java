package Entity;

import Actors.Amministrazione;
import Actors.Turista;
import java.util.*;
import java.lang.Object;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.Timer;


public class PrenotazioneTour {

    private boolean statoPrenotazione; //booleano per verificare se un turista ha gia' pagato la prneotazione
    private boolean annullaGratis; //booleano per verificare se il turista puo' annullare una prenotazione senza pagare la mora
    private int numeroPostiPrenotati;
    private Timer scadenza = new Timer();
    private String emailTurista;
    private int idDisponibilita;
    private Scanner scanner = new Scanner(System.in);



    public PrenotazioneTour(int idDisponibilita, String emailTurista, int numeroPostiPrenotati) {
        this.annullaGratis = true;
        this.numeroPostiPrenotati = numeroPostiPrenotati;
        this.emailTurista = emailTurista;
        this.idDisponibilita = idDisponibilita;
        //serve per sapere se il turista ha pagato la prenotazione
        this.statoPrenotazione = false;
        avvioTimerScadenza();
    }

    /**
     * Metodo per avviare il timer per la scadenza dell'annullamento della prenotazione gratuita.
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
     * Metodo che modifica lo stato di una prenotazione.
     * @param statoPrenotazione che si vuole mettere come valore nuovo.
     */
    public void setStatoPrenotazione(boolean statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }

    public Timer getScadenza() {
        return scadenza;
    }

    public void setScadenza(Timer scadenza) {
        this.scadenza = scadenza;
    }

    public int getNumeroPostiPrenotati() {
        return numeroPostiPrenotati;
    }

    public String getEmailTurista() {
        return emailTurista;
    }

    public int getIdDisponibilita() {
        return idDisponibilita;
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


}


