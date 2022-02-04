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

    public void setTipo(TipoToponimo tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
