package it.unicam.cicero.model;
import java.lang.*;
import lombok.Data;



@Data

/**
 * Classe che crea un turista. il turista ha: un nome, un cognome, un email e un codice fiscale.
 */
public class Turista {

    private String nome;
    private String cognome;
    private String email;
    private final String codiceFiscale;


    public Turista(String nome, String cognome, String email, String codiceFiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.codiceFiscale = codiceFiscale;
    }


    /**
     * Metodo che permette al turista di eseguire una prenotazione
     *
     * @param tourDaPrenotare tour che il turista vuole prenotare
     */
    public void eseguiPrenotazione (TourCalendario tourDaPrenotare) {
        PrenotazioneTour prenotazione = new PrenotazioneTour (tourDaPrenotare, this);
        tourDaPrenotare.getElencoPrenotazioni().add(prenotazione);
    }

    /**
     * Metodo che permette al turista di annullare una prenotazione
     *
     * @param prenotazione che il turista vuole annullare
     */
    public void annullaPrenotazione (PrenotazioneTour prenotazione) {
        prenotazione.getTourCalendario().getElencoPrenotazioni().remove(prenotazione);
        int totaleNuoviPostiDisponibili = prenotazione.getNumeroPostiDaPrenotare() + prenotazione.getTourCalendario().getPostiDisponibili();
        prenotazione.getTourCalendario().setPostiDisponibili(totaleNuoviPostiDisponibili);
        int postiPrenotati = prenotazione.getTourCalendario().getTotalePostiPrenotati() - prenotazione.getNumeroPostiDaPrenotare();
        prenotazione.getTourCalendario().setTotalePostiPrenotati(postiPrenotati);
    }
}
