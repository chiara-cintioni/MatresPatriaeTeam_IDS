package it.unicam.cicero.model;

import lombok.Getter;

import java.util.Objects;


@Getter
/**
 * Classe che permette di creare una prenotazione a un tour. Una prneotazione ha: un id, un astatoPrenotazione (Variabile boolean che indica se la prenotazione può essere pagata o meno (si è raggiunto il numero minimo di
 *     partecipanti)), numero dei posti che si vogliono prenotare con la prenotazione, il tour che si vuole prenotare e il turista che fa la prenotazione.
 */
public class PrenotazioneTour {

    private String id;
    private boolean statoPrenotazione;
    private int numeroPostiDaPrenotare;
    private TourCalendario tourCalendario;
    private Turista turista;

    public PrenotazioneTour(TourCalendario tourCalendario, Turista turista) {
        this.id = id;
        this.numeroPostiDaPrenotare = 1;
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
     * Metodo che modifca il numero di posti che si vuole prenotare
     *
     * @param numeroPostiDaPrenotare che il turista vuole prenotare
     */
    public void setNumeroPostiDaPrenotare ( int numeroPostiDaPrenotare){
        this.numeroPostiDaPrenotare = numeroPostiDaPrenotare;
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

    @Override
    public int hashCode() {

            return Objects.hash(id);
    }


}


