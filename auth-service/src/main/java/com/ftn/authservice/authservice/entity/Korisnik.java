package com.ftn.authservice.authservice.entity;


import lombok.Data;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;


@Entity
@Data
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ime;
    private String prezime;
    private String username;


    @Nullable
    @Pattern(regexp = "\\d{13}", message = "JMBG mora sadržati tačno 13 cifara.")
    @Column(nullable = true, unique = true)
    private String jmbg;

    @Column(length = 60)
    private String lozinka;

    private String uloga;
    private boolean enabled = false;
}
