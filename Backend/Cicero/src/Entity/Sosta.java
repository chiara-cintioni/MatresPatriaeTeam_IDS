package Entity;

public class Sosta {
    private String nomeSosta;
    private String descrizione;
    private String nomeTour;
    private String emailCicerone;

    public Sosta(String nomeSosta, String descrizione, String nomeTour, String emailCicerone) {
        this.nomeSosta = nomeSosta;
        this.descrizione = descrizione;
        this.nomeTour = nomeTour;
        this.emailCicerone = emailCicerone;
    }

    public String getNomeSosta() {
        return nomeSosta;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getNomeTour() {
        return nomeTour;
    }

    public String getEmailCicerone() {
        return emailCicerone;
    }
}
