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

    public KorisnikDto(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
