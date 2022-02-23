package Entity;

import Actors.Turista;
import java.util.Date;

public class PagamentoTour {

    private static int contatorePagamentoTour = 0;
    private final int id;
    private double totale;
    private Date data;
    private PrenotazioneTour prenotazione;
    private Turista turista;


    public PagamentoTour(double totale, Date data, PrenotazioneTour prenotazione, Turista turista) {
        this.prenotazione = prenotazione;
        this.turista = turista;
        this.totale = totale;
        this.data = data;
        contatorePagamentoTour++;
        this.id = contatorePagamentoTour;
    }


    /**
     * Metodo per chiamare il sistema esterno per il pagamento online
     * @return true se il sistema risponde, false altrimenti.
     */
    //TODO: 28/01/2022
    public boolean chiamaSistemaPerPagamanto(){
        return true;
    }


    /**
     * Metodo per richiedere l'autorizzazione del pagamento al sistema esterno di pagamento online
     * @return true se c'e' l'autorizzazione, false altrimenti.
     */
    //TODO:  28/01/2022
    public boolean richiestaAutorizzazionePagamento(){
        return true;
    }

    public double getTotale() {
        return totale;
    }

    public Date getData() {
        return data;
    }

    public PrenotazioneTour getPrenotazione() {
        return prenotazione;
    }

    public Turista getTurista() {
        return turista;
    }
}
