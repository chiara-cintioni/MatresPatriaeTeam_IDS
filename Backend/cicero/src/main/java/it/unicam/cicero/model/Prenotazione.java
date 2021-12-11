package it.unicam.cicero.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;


@Getter
public class Prenotazione {

    private String id;
    /*Variabile boolean che indica se la prenotazione può essere pagata o meno (si è raggiunto il numero minimo di
    partecipanti).*/
    private boolean statoPrenotazione;
    private int numeroPostiDaPrenotare;
    private TourCalendario tourCalendario;
    private Turista turista;

    public Prenotazione(TourCalendario tourCalendario, Turista turista) {
        this.id = id;
        this.numeroPostiDaPrenotare = 1;
        this.tourCalendario = tourCalendario;
        this.turista = turista;
    }


    /**
     * @param statoPrenotazione
     */
    public void setStatoPrenotazione(boolean statoPrenotazione) {
        this.statoPrenotazione = statoPrenotazione;
    }


    /**
     *
     * @param numeroPostiDaPrenotare
     */
    public void setNumeroPostiDaPrenotare ( int numeroPostiDaPrenotare){
        this.numeroPostiDaPrenotare = numeroPostiDaPrenotare;
    }


        /**
         *
         * @param tourCalendario
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
        Prenotazione that = (Prenotazione) o;
        return statoPrenotazione == that.statoPrenotazione && numeroPostiDaPrenotare == that.numeroPostiDaPrenotare && Objects.equals(id, that.id) && Objects.equals(tourCalendario, that.tourCalendario);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}


