import java.lang.*;


import java.util.*;

/**
 * Classe singleton per la creazione di un GestioneCalendario per la gestione dei calendari dei Ciceroni/Associazioni.
 */
public class GestioneCalendario {


    private static GestioneCalendario gestioneCalendario;

    private GestioneCalendario() {
    };


    public static GestioneCalendario getInstance() {
        if (gestioneCalendario == null)
            gestioneCalendario = new GestioneCalendario();
        return gestioneCalendario;
    }

    /**
     * Metodo che modifica il calendario del cicerone inserendo un tour in una data del calendario
     * Utilizzato nel metodo aggiungiDisponibilita del cicerone
     *
     * @param giorno del tour che si vuole modificare
     * @param mese   del tour che si vuole modificare
     * @param tour   che si vuole inserire in quel giorno e quel mese
     * @param calendario del cicerone
     */
    public boolean modificaCalendario(int giorno, int mese, Tour tour, Calendario calendario) {
        if (controlloDisponibilitaData(giorno, mese, calendario)) {
            TourCalendario tourDaInserire = new TourCalendario(tour, giorno, mese);
            calendario.inserisciTour(giorno, mese, tourDaInserire);
        } else {
            System.out.println("In questa data: " + giorno + "/" + mese + ", e' gia' presente un tour");
            return false;

        }
        return true;
    }

    /**
     * Metodo che elimina un tour dal calendario del cicerone
     *
     * @param giorno del tour che si vuole eliminare
     * @param mese   del tour che si vuoel eliminare
     * @param calendario del cicerone
     *
     * @return true se il tour e' stato eliminato in una specifica data, false altrimenti
     */
    public boolean eliminaTourDalCalendario(int giorno, int mese, Calendario calendario) {
        calendario.eliminaTourCalendario(giorno, mese);
        return true;
    }

    /**
     * Metodo che controlla se in una specifica data del calendario e' gia' presente un tour del cicerone
     * @param giorno del calendario
     * @param mese del calendario
     * @param calendario del cicerone
     * @return true se la data selezionata e' disponibile, false altrimenti
     */
    public boolean controlloDisponibilitaData(int giorno, int mese, Calendario calendario) {
        if (calendario.restituisciTour(giorno, mese) != null) {
            return false;
        }
        return true;
    }

    /**
     * Metodo per controllare la disponibilita' dei posti in un tour
     * @param tourCalendario di cui si vogliono controllare i posti
     * @param posti che il turista vuole prenotare e di cui bisogna controllare la disponibilita'
     * @return true se i posti sono disponibili per la prenotazione, false altrimenti.
     */
    public boolean controlloDisponibilitaPosti(TourCalendario tourCalendario, int posti) {
        if (tourCalendario.getPostiDisponibili() >= posti) {
            return true;
        }
       return false;
    }

}

