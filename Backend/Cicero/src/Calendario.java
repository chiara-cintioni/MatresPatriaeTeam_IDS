import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calendario {
    private TourCalendario[][] calendario;

    public Calendario() {
        this.calendario = new TourCalendario[32][13];
    }

    public TourCalendario restituisciTour (int giorno, int mese) {
          return this.calendario[giorno][mese];
    }

    // Stampa il calendario.
    public void stampaGriglia() {
        System.out.println("\n");
        int riga, colonna;
        System.out.print("  ");
        for (colonna = 1; colonna < 13; colonna++) {
            System.out.print("      " + colonna + "  ");
        }
        System.out.println("\n  +---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+ ");
        for (riga = 1; riga < 32; riga++) {
            System.out.print(riga + " | ");
            for (colonna = 1; colonna < 13; colonna++) {
                if (calendario[riga][colonna] == null) {
                    System.out.print(" null " + " | ");
                } else {
                    System.out.print(calendario[riga][colonna].getTour().getNome() + " | ");
                }
            }
            System.out.println("\n  +---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+---------+ ");
        }
    }
    

    public void stampaCalendarioDellaGuida () {
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 13; j++) {
                if (restituisciTour(i, j) != null) {
                    System.out.println("Il tour nel giorno: " + i + " e mese: " + j + " e': " + restituisciTour(i, j).getTour().getNome());
                }
            }
        }
    }

    /**
     * Metodo che inserisce un tourCalendario nel calendario, se e solo se il giorno e il mese esistono.
     * @param giorno del tour da inserire
     * @param mese del tour da inserire
     * @param tour da inserire
     */
    public void inserisciTour (int giorno, int mese, TourCalendario tour) {
        if (giorno > 31 || giorno < 0) throw new IllegalArgumentException("Il giorno non esiste");
        if (mese > 12 || mese < 0) throw new IllegalArgumentException("Il mese non esiste");
        /*
         * Controllo per il mese di Febbraio riguardo l'anno bisestile */
        Calendar date = new GregorianCalendar();
        int year = date.get(Calendar.YEAR);
        if (mese == 2) {
            if(!annoBisestile(year)){
                if(giorno>27){
                    throw new IllegalArgumentException("Non esiste questo giorno");
                }
            } else{
                if (giorno > 28) {
                    throw new IllegalArgumentException("Non esiste questo giorno");
                }
            }
        }
        int [] arrayMesi = {4, 6, 9, 11}; //mesi che hanno 30 giorni
        for (int i = 0; i < arrayMesi.length; i++) {
            if (mese == arrayMesi[i]) {
                if (giorno > 30) {
                    throw new IllegalArgumentException("Non esiste questo giorno");
                }

            }
        }
        this.calendario[giorno][mese] = tour;
        tour.getTour().getElencoDisponibilitaDelTour().add(tour);
    }

    /**
     * Metodo che elimina un tourCalendario dal calendario e elimina il tourCalendario dall'elenco del tour.
     * @param giorno del tour da eliminare
     * @param mese del tour da eliminare
     */
    public void eliminaTourCalendario (int giorno, int mese ) {
        calendario[giorno][mese].getTour().getElencoDisponibilitaDelTour().remove(calendario[giorno][mese]);
        calendario[giorno][mese] = null;
    }

    /**
     *Metodo che controlla se l'anno e' bisestile oppure no
     * @param anno che si vuole controllare
     * @return true se l'anno e' bisestile, false atrimenti
     */
    private boolean annoBisestile(int anno){
        if(anno > 1584 && (anno%400==0 || (anno%4==0 && anno%100!=0))){
            return true;
        }
        return false;
    }

    public TourCalendario[][] getCalendario() {
        return calendario;
    }

    public void setCalendario(TourCalendario[][] calendario) {
        this.calendario = calendario;
    }
}
