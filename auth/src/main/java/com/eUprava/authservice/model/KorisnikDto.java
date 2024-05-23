package com.eUprava.authservice.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KorisnikDto {
    private Long id;

    @NotBlank
    private String username;
    @NotBlank
    private String password;


    public KorisnikDto(Korisnik createUser){
        this.username = createUser.getUsername();
        this.password = createUser.getLozinka();
    }
}
