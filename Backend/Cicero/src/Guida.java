public interface Guida {
    public boolean aggiungiDisponibilita(Tour tour, int giorno, int mese);
    public boolean rimuoviDisponibilita(int giorno, int mese);
    public boolean creaTour(String nome, int numeroMinimo, int numeroMassimo, double prezzo, String descrizione);
    public boolean eliminaTourDefinitivamente(Tour tour);
    public boolean modificaTour(Tour tour);
    public boolean modificaData(TourCalendario tour, int nuovoGiorno, int nuovoMese);
    public Tour visualizzaTour(String nome);
}
