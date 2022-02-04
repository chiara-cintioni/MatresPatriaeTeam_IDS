import java.util.HashSet;

public class ListaTour {

    private static ListaTour listaTour;
    private static HashSet<Tour> lista;

    private ListaTour(){
        this.lista = new HashSet<Tour>();
    }

    public static ListaTour getInstance(){
        if(listaTour == null)
            listaTour = new ListaTour();
        return listaTour;
    }

    /**
     * Metodo che aggiunge un tour alla lista dei tour di un Cicerone
     * @param tour che si vuole aggiungere alla lista
     */
   public void aggiungiTour (Tour tour) {
        lista.add(tour);
   }

    /**
     * Metodo che restituisce l'elenco dei tour della lista di un cicerone
     */
    public void visualizzaTourDellaLista () {
        for (Tour t : lista) {
            System.out.println(t.toString());
        }
    }

    /**
     * Metodo che restituisce un tour dato l'indice di quel tour
     * @param id del tour
     * @return tour con quello specifico indice
     */
    public Tour getTour (int id) {
        for (Tour t : lista) {
            if (t.getId() == id) {
                return t;
            }
        }
        return null;
    }

}
