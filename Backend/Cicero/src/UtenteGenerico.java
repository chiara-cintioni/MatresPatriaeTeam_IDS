public interface UtenteGenerico {

    /**
     * Metodo per visualizzare tutti i tour presenti nel sistema
     */
    //TODO: 28/01/2022
    public void visualizzaTuttiTour();

    /**
     * Metodo utilizzato per visualizzare un invito e decidere se accettare o no. Per accettare/rifiutare l'utente
     * deve essere registrato come Turista
     * @param invito che si vuole visualizzare
     * @return invito accettato o invito rifiutato
     */
    //TODO: 28/01/2022
    public String rispondiAllInvito(Invito invito);

    /**
     * Metodo per permettere al turista di scegliereun singolo tour dall'elenco tour
     * @return tour scelto
     */
    public Tour scegliTour ();

}
