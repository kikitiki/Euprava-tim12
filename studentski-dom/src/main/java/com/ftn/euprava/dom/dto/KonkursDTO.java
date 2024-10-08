package com.ftn.euprava.dom.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class KonkursDTO {
    private Long id;
    private String grad;
    private String skolskaGodina;
    private String opis;
    private LocalDate datumPocetka;
    private LocalDate datumZavrsetka;
}

