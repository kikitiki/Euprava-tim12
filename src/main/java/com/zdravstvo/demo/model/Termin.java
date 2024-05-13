package com.zdravstvo.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Termin {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Student student;
    @ManyToOne
    private Doktor doktor;
    private LocalDateTime pocetakTermina;
    private LocalDateTime krajTermina;
    private StatusTermina statusTermina;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Doktor getDoktor() {
        return doktor;
    }

    public void setDoktor(Doktor doktor) {
        this.doktor = doktor;
    }

    public LocalDateTime getPocetakTermina() {
        return pocetakTermina;
    }

    public void setPocetakTermina(LocalDateTime pocetakTermina) {
        this.pocetakTermina = pocetakTermina;
    }

    public LocalDateTime getKrajTermina() {
        return krajTermina;
    }

    public void setKrajTermina(LocalDateTime krajTermina) {
        this.krajTermina = krajTermina;
    }

    public StatusTermina getStatusTermina() {
        return statusTermina;
    }

    public void setStatusTermina(StatusTermina statusTermina) {
        this.statusTermina = statusTermina;
    }
}
