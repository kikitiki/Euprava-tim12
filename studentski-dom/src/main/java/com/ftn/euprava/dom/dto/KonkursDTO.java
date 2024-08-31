package com.ftn.euprava.dom.dto;

import lombok.Data;

import java.util.Date;

@Data
public class KonkursDTO {
    private Long id;
    private String grad;
    private String skolskaGodina;
    private String opis;
    private Date datumPocetka;
    private Date datumZavrsetka;
}

