package it.unicam.cicero.model;
import java.lang.*;
import lombok.Data;



@Data

public class Turista {

    private String nome;
    private String cognome;
    private String email;
    private final String codiceFiscale;

    /**
     *
     * @param nome
     * @param cognome
     * @param email
     * @param codiceFiscale
     */
    public Turista(String nome, String cognome, String email, String codiceFiscale) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.codiceFiscale = codiceFiscale;
    }


    public void eseguiPrenotazione (TourCalendario tourDaPrenotare) {
        Prenotazione prenotazione = new Prenotazione(tourDaPrenotare, this);
        tourDaPrenotare.getElencoPrenotazioni().add(prenotazione);
    }

    public void annullaPrenotazione (Prenotazione prenotazione) {
        prenotazione.getTourCalendario().getElencoPrenotazioni().remove(prenotazione);
        int totaleNuoviPostiDisponibili = prenotazione.getNumeroPostiDaPrenotare() + prenotazione.getTourCalendario().getPostiDisponibili();
        prenotazione.getTourCalendario().setPostiDisponibili(totaleNuoviPostiDisponibili);
        int postiPrenotati = prenotazione.getTourCalendario().getTotalePostiPrenotati() - prenotazione.getNumeroPostiDaPrenotare();
        prenotazione.getTourCalendario().setTotalePostiPrenotati(postiPrenotati);
    }
}
