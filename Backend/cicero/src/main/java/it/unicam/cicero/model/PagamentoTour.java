package it.unicam.cicero.model;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor

public class PagamentoTour {
    private String numeroIdentificiativo;
    private double totale;
    private Date data;
    private Date ora;
    private PrenotazioneTour prenotazione;

    public PagamentoTour(PrenotazioneTour prenotazione) {
        this.prenotazione = prenotazione;
    }

    public String getNumeroIdentificiativo() {
        return numeroIdentificiativo;
    }

    public double getTotale() {
        return totale;
    }

    public Date getData() {
        return data;
    }

    public Date getOra() {
        return ora;
    }

    public PrenotazioneTour getPrenotazione() {
        return prenotazione;
    }


}
