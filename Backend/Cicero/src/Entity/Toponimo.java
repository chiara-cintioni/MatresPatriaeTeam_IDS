package Entity;

public class Toponimo {
    private TipoToponimo tipo;
    private String nome;

    public Toponimo(TipoToponimo tipo, String nome) {
        this.tipo = tipo;
        this.nome = nome;
    }

    public TipoToponimo getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }
}
