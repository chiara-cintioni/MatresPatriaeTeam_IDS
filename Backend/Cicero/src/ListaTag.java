import java.util.HashSet;

public class ListaTag {

    private static ListaTag listaTag;
    private static HashSet<Tag> lista;

    private ListaTag(){
        this.lista = new HashSet<Tag>();
    }

    public static ListaTag getInstance(){
        if(listaTag == null)
            listaTag = new ListaTag();
        return listaTag;
    }

    /**
     * Metodo che verifica se nella lista e' gia' presente il tag passato come argomento
     * @param tag di cui si vuole controllare la presenza
     * @return true se il tag esiste nella lista, false altrimenti
     */

    public boolean controlloEsistenzaTag(Tag tag){
        for (Tag t : lista ) {
            if (t.getNome().equalsIgnoreCase(tag.getNome())){
                return true;
            }
        }
        return false;

    }

      /**
     * Metodo per inserire un nuovo tag nella lista dei tag
     * @return true se il tag e' stato inserito, false altrimenti
     */

    public boolean aggiungiTagAllaLista(Tag tag){
        return lista.add(tag);
    }

    /**
     * Metodo che stampa la lista dei tag
     */

    public void stampaElencoTag() {
        System.out.println("ELENCO TAG:  ");
        for (Tag t : lista) {
            System.out.println("Tag: "+ t.getNome());
        }

    }

}
