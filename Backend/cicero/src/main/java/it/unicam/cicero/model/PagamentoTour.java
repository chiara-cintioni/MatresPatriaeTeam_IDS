package it.unicam.cicero.model;


import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor

public class PagamentoTour {
    private String numeroIdentificiativo;
    private double totale;
    private Date data;
    private Date ora;



}
