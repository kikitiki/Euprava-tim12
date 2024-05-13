package com.zdravstvo.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.web.WebProperties;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String jmbg;

    @Column(unique = true)
    private String username;
    private String lozinka;
    private String ime;
    private String prezime;
    private Pol pol;
    private Boolean stomatolog;
    private Boolean ginekolog;

    private Boolean opstaPraksa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
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

    public Pol getPol() {
        return pol;
    }

    public void setPol(Pol pol) {
        this.pol = pol;
    }

    public Boolean getStomatolog() {
        return stomatolog;
    }

    public void setStomatolog(Boolean stomatolog) {
        this.stomatolog = stomatolog;
    }

    public Boolean getGinekolog() {
        return ginekolog;
    }

    public void setGinekolog(Boolean ginekolog) {
        this.ginekolog = ginekolog;
    }

    public Boolean getOpstaPraksa() {
        return opstaPraksa;
    }

    public void setOpstaPraksa(Boolean opstaPraksa) {
        this.opstaPraksa = opstaPraksa;
    }
}
