package it.unicam.cicero.model;

import lombok.Getter;

import java.util.Objects;


@Getter
/**
 * Classe che permette di creare una prenotazione a un tour. Una prneotazione ha: un id, un astatoPrenotazione (Variabile boolean che indica se la prenotazione può essere pagata o meno (si è raggiunto il numero minimo di
 *     partecipanti)), numero dei posti che si vogliono prenotare con la prenotazione, il tour che si vuole prenotare e il turista che fa la prenotazione.
 */
public class PrenotazioneTour {

    private final int id;
    private static int contatoreTour = 0;
    private boolean statoPrenotazione;
    private int numeroPostiDaPrenotare;
    private TourCalendario tourCalendario;
    private Turista turista;

    public PrenotazioneTour(TourCalendario tourCalendario, Turista turista, int numeroPostiDaPrenotare) {
        contatoreTour++;
        this.id = contatoreTour;
        this.numeroPostiDaPrenotare = numeroPostiDaPrenotare;
        this.tourCalendario = tourCalendario;
        this.turista = turista;
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
     * Metodo che diminuisce il numero di posti disponibili in un tour dopo che avviene una prneotazione
     *
     * @param tourCalendario tour di cui si vogliono modificare i posti disponibili
     */
    public void decreaseAvailablePlaces (TourCalendario tourCalendario){
        int posti = tourCalendario.getPostiDisponibili() - numeroPostiDaPrenotare;
        tourCalendario.setPostiDisponibili(posti);
        int postiPrenotati = tourCalendario.getTotalePostiPrenotati() + numeroPostiDaPrenotare;
        tourCalendario.setTotalePostiPrenotati(postiPrenotati);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PrenotazioneTour that = (PrenotazioneTour) o;
        return statoPrenotazione == that.statoPrenotazione && numeroPostiDaPrenotare == that.numeroPostiDaPrenotare && Objects.equals(id, that.id) && Objects.equals(tourCalendario, that.tourCalendario);
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

    @Override
    public int hashCode() {
            return Objects.hash(id);
    }


}


