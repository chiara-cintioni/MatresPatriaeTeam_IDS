package Entity;

public class Disponibilita {
    private String nomeTour;
    private String nomeGuida;
    private String nomeCicerone;
    private String emailCicerone;
    private String dataTour;
    private boolean possibilitaDiPagare = false;
    private int postiDisponibili;
    private int totalePostiPrenotati = 0;
    private final int idDisponibilita;
    private static int contatore = 0;

    public Disponibilita(String nomeTour, String nomeGuida, String nomeCicerone, String emailCicerone, String dataTour, int postiDisponibili) {
        this.nomeTour = nomeTour;
        this.nomeGuida = nomeGuida;
        this.nomeCicerone = nomeCicerone;
        this.emailCicerone = emailCicerone;
        this.dataTour = dataTour;
        contatore++;
        this.postiDisponibili = postiDisponibili;
        this.idDisponibilita = contatore;
    }

    public String getNomeTour() {
        return nomeTour;
    }

    public String getNomeGuida() {
        return nomeGuida;
    }

    public String getNomeCicerone() {
        return nomeCicerone;
    }

    public String getEmailCicerone() {
        return emailCicerone;
    }

    public String getDataTour() {
        return dataTour;
    }

    public boolean isPossibilitaDiPagare() {
        return possibilitaDiPagare;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public int getTotalePostiPrenotati() {
        return totalePostiPrenotati;
    }
}
