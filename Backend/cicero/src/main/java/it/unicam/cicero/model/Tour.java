package it.unicam.cicero.model;
import lombok.Data;

import java.util.Objects;


@Data
/**
 *
 */
public class Tour {

    private static int contatoreTour = 0;
    private String nome;
    private int numeroMinimo;
    private int numeroMassimo;
    private double prezzo;
    private String descrizione;
    private final int id;


    public Tour(String nome, int numeroMinimo, int numeroMassimo, double prezzo, String descrizione) {
        this.nome = nome;
        this.numeroMinimo = numeroMinimo;
        this.numeroMassimo = numeroMassimo;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        contatoreTour++;
        this.id = contatoreTour;
    }


}
