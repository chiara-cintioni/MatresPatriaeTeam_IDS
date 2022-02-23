package Entity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

public class Tour {

    private static int contatoreTour = 0;
    private String nome;
    private int numeroMinimo;
    private int numeroMassimo;
    private double prezzo;
    private String descrizione;
    private final int id;
    private int giorniScadenzaInvito;


    public Tour(String nome, int numeroMinimo, int numeroMassimo, double prezzo, String descrizione) {
        this.nome = nome;
        this.numeroMinimo = numeroMinimo;
        this.numeroMassimo = numeroMassimo;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        contatoreTour++;
        this.id = contatoreTour;
        this.giorniScadenzaInvito = 2;
    }

    public static int getContatoreTour() {
        return contatoreTour;
    }

    public String getNome() {
        return nome;
    }

    public int getNumeroMinimo() {
        return numeroMinimo;
    }

    public int getNumeroMassimo() {
        return numeroMassimo;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public int getId() {
        return id;
    }

    public int getGiorniScadenzaInvito() {
        return giorniScadenzaInvito;
    }

    public void setGiorniScadenzaInvito(int giorniScadenzaInvito) {
        if(giorniScadenzaInvito >= 2){
            this.giorniScadenzaInvito = giorniScadenzaInvito;
        }
    }




}