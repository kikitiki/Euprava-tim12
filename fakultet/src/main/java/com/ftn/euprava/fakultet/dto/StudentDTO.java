package com.ftn.euprava.fakultet.dto;

import com.ftn.euprava.fakultet.model.Kartica;
import lombok.Data;

@Data
public class StudentDTO {
    private Long id;
    private String ime;
    private String prezime;
    private String jmbg;
    private int godinaStudiranja;
    private int osvojeniBodovi;
    private double prosek;
    private Kartica kartica;


}