package it.unicam.cicero.model;
import lombok.Data;

import java.util.Objects;


@Data
/**
 * Classe che crea un oggetto di tipo Tour. Oggetto di tipo Tour viene preso da TourCalendario che lo inserisce nel calendario del Cicerone.
 * Oggetto di tipo tour ha: un contatoreTour che mi indica il nuemro di tour che vengono creati, un nome, un numeroMinimo di persone che possono prenotare il tour,
 * il numeroMassimo di persone che possono prneotare il tour, il prezzo del tour, una descrizione, e un id.
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
