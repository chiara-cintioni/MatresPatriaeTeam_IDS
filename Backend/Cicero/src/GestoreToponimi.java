import java.util.HashSet;

/**
 * Classe singleton per la creazione delle liste dei toponimi e per la loro gestione
 */

public class GestoreToponimi {
    private static GestoreToponimi gestoreToponimi;
    private static HashSet<Toponimo> oronimo;
    private static HashSet<Toponimo> poleonimo;
    private static HashSet<Toponimo> coronimo;
    private static HashSet<Toponimo> nesonimo;
    private static HashSet<Toponimo> limnonimo;
    private static HashSet<Toponimo> idronimo;
    private static HashSet<Toponimo> odonimo;

    private GestoreToponimi(){
        this.oronimo = new HashSet<Toponimo>();
        this.poleonimo = new HashSet<Toponimo>();
        this.coronimo = new HashSet<Toponimo>();
        this.nesonimo = new HashSet<Toponimo>();
        this.limnonimo = new HashSet<Toponimo>();
        this.idronimo = new HashSet<Toponimo>();
        this.odonimo = new HashSet<Toponimo>();
    }

    public static GestoreToponimi getInstance(){
        if(gestoreToponimi == null){
            gestoreToponimi = new GestoreToponimi();
        }
        return gestoreToponimi;
    }

    /**
     *Metodo per aggiungere un toponimo alla sua lista corrispondente
     * @param toponimo che si vuole aggiungere alla lista corrispondente
     * @return true se il tponimo e' stato aggiunto, false altrimenti
     */
    public boolean aggiungiToponimo(Toponimo toponimo){
        if(toponimo == null){
            throw new NullPointerException("toponimo è nullo");
        }

        if(controlloEsistenzaToponimo(toponimo.getNome(), toponimo.getTipo())) return false;

        if(toponimo.getTipo() == TipoToponimo.ORONIMO){
            oronimo.add(toponimo);
            return true;
        }
        else if(toponimo.getTipo() == TipoToponimo.CORONIMO){
            coronimo.add(toponimo);
            return true;
        }
        else if(toponimo.getTipo() == TipoToponimo.IDRONIMO){
            idronimo.add(toponimo);
            return true;
        }
        else if(toponimo.getTipo() == TipoToponimo.LIMNONIMO){
            limnonimo.add(toponimo);
            return true;
        }
        else if(toponimo.getTipo() == TipoToponimo.NESONIMO){
            nesonimo.add(toponimo);
            return true;
        }
        else if(toponimo.getTipo() == TipoToponimo.POLEONIMO){
            poleonimo.add(toponimo);
            return true;
        }
        else if (toponimo.getTipo() == TipoToponimo.ODONIMO){
            odonimo.add(toponimo);
            return true;
        }
    return false;
    }

    /**
     * Metodo che controlla se un toponimo e' gia' presente nella lista
     * @param nome dle toponimo
     * @param tipo del toponimo
     * @return true se il toponimo esiste, false altrimenti
     */

    public boolean controlloEsistenzaToponimo(String nome, TipoToponimo tipo){
        if (tipo == TipoToponimo.ORONIMO) {
            for (Toponimo t : oronimo) {
                if (t.getNome().equals(nome)) {
                    System.out.println("E' gia' presente un toponimo del tipo ORONIMO con il nome: "+nome);
                    return true;
                }
            }
        }
        if (tipo == TipoToponimo.CORONIMO) {
            for (Toponimo t : coronimo) {
                if (t.getNome().equals(nome)) {
                    System.out.println("E' gia' presente un toponimo del tipo CORONIMO con il nome: "+nome);
                    return true;
                }
            }
        }
        if (tipo == TipoToponimo.IDRONIMO) {
            for (Toponimo t : idronimo) {
                if (t.getNome().equals(nome)) {
                    System.out.println("E' gia' presente un toponimo del tipo IDRONIMO con il nome: "+nome);
                    return true;
                }
            }
        }
        if (tipo == TipoToponimo.LIMNONIMO) {
            for (Toponimo t : limnonimo) {
                if (t.getNome().equals(nome)) {
                    System.out.println("E' gia' presente un toponimo del tipo LIMNONIMO con il nome: "+nome);
                    return true;
                }
            }
        }
        if (tipo == TipoToponimo.NESONIMO) {
            for (Toponimo t : nesonimo) {
                if (t.getNome().equals(nome)) {
                    System.out.println("E' gia' presente un toponimo del tipo NESONIMO con il nome: "+nome);
                    return true;
                }
            }
        }
        if (tipo == TipoToponimo.POLEONIMO) {
            for (Toponimo t : poleonimo) {
                if (t.getNome().equals(nome)) {
                    System.out.println("E' gia' presente un toponimo del tipo POLEONIMO con il nome: "+nome);
                    return true;
                }
            }
        }
        if (tipo == TipoToponimo.ODONIMO) {
            for (Toponimo t : odonimo) {
                if (t.getNome().equals(nome)) {
                    System.out.println("E' gia' presente un toponimo del tipo ODONIMO con il nome: "+nome);
                    return true;
                }
            }
        }
        return false;
    }




    private void stampaOrononimo() {
        System.out.println("ELENCO ORONIMI:  ");
        for (Toponimo t : oronimo) {
            System.out.println("Toponimo: "+ t.getNome());
        }

    }
    private void stampaCorononimo() {
        System.out.println("ELENCO CORONIMI:  ");
        for (Toponimo t : coronimo) {
            System.out.println("Toponimo: "+ t.getNome());
        }

    } private void stampaIdrononimo() {
        System.out.println("ELENCO IDRONIMI:  ");
        for (Toponimo t : idronimo) {
            System.out.println("Toponimo: "+ t.getNome());
        }

    } private void stampaLimnonimo() {
        System.out.println("ELENCO LIMNONIMI:  ");
        for (Toponimo t : limnonimo) {
            System.out.println("Toponimo: "+ t.getNome());
        }

    } private void stampaNesonimo() {
        System.out.println("ELENCO NESONIMI:  ");
        for (Toponimo t : nesonimo) {
            System.out.println("Toponimo: "+ t.getNome());
        }

    } private void stampaPoleonimo() {
        System.out.println("ELENCO POLEONIMI:  ");
        for (Toponimo t : poleonimo) {
            System.out.println("Toponimo: "+ t.getNome());
        }

    } private void stampaOdononimo() {
        System.out.println("ELENCO ODONIMI:  ");
        for (Toponimo t : odonimo) {
            System.out.println("Toponimo: "+ t.getNome());
        }

    }

    public void stampaElenchiToponimi() {
        stampaNesonimo();
        stampaOdononimo();
        stampaOrononimo();
        stampaPoleonimo();
        stampaCorononimo();
        stampaIdrononimo();
        stampaLimnonimo();
    }

    public void stampaElencoTipoToponimo () {
        TipoToponimo t = TipoToponimo.ORONIMO;
        t.stampaId();
    }
}
