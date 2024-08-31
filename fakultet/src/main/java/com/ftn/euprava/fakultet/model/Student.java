package com.ftn.euprava.fakultet.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String ime;
    private String prezime;
    private String jmbg;
    private int godinaStudiranja;
    private int osvojeniBodovi;
    private double prosek;

    @Enumerated(EnumType.STRING)
    private Kartica kartica;
}