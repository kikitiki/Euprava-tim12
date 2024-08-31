package com.ftn.euprava.dom.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Konkurs {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "grad")

    private String grad;

    @Column(name = "skolska_godina")
    private String skolskaGodina;

    @Column(name = "opis")
    private String opis;

    @Temporal(TemporalType.DATE)
    @Column(name = "datum_pocetka")
    private Date datumPocetka;

    @Temporal(TemporalType.DATE)
    @Column(name = "datum_zavrsetka")
    private Date datumZavrsetka;
}



