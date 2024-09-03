package com.ftn.authservice.authservice.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class KorisnikDTO {

    private Long id;

    @NotBlank
    private String ime;

    @NotBlank
    private String prezime;


    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Nullable
    @Pattern(regexp = "\\d{13}", message = "JMBG mora sadržati tačno 13 cifara.")
    private String jmbg;


    public KorisnikDTO(Korisnik createdUser) {
        this.username = createdUser.getUsername();
        this.password = createdUser.getLozinka();
    }
}
