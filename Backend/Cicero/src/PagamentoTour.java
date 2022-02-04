
import java.util.Date;


public class PagamentoTour {
    private String id;
    private double totale;
    private Date data;
    private Date ora;
    private PrenotazioneTour prenotazione;
    private Turista turista;


    public PagamentoTour(double totale, Date data, Date ora, PrenotazioneTour prenotazione, Turista turista) {
        this.prenotazione = prenotazione;
        this.turista = turista;
        this.totale = totale;
        this.data = data;
        this.ora = ora;
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
}
