package Entity;

public class GuidaAssociazione {
    private String nome;
    private String cognome;
    private String username;
    private String descrizione;
    private String emailAssociaizone;



    public GuidaAssociazione(String nome, String cognome, String username, String descrizione, String emailAssociazione) {
        this.nome = nome;
        this.cognome = cognome;
        this.username = username;
        this.descrizione = descrizione;
        this.emailAssociaizone = emailAssociazione;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAssociaizone() {
        return emailAssociaizone;
    }
}