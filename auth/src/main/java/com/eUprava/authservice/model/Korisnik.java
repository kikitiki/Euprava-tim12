package com.eUprava.authservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Korisnik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String ime;
    private String prezime;
    private String username;

    @Column(length = 60)
    private String lozinka;
    private String uloga;
    private boolean enabled = false;

    public Korisnik(Long id, String ime, String prezime, String username, String lozinka, String uloga, boolean enabled) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.username = username;
        this.lozinka = lozinka;
        this.uloga = uloga;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getUloga() {
        return uloga;
    }

    public void setUloga(String uloga) {
        this.uloga = uloga;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
