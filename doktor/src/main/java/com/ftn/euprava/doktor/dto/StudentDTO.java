package com.ftn.euprava.doktor.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private Long id;
    private String ime;
    private String prezime;
    private String jmbg;
    private boolean lekarski;

}