package com.zdravstvo.demo.model;

//import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Doktor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique=true)
    private String jmbg;
    private String ime;
    private String prezime;
    @Column(unique=true)
    private String username;

    private String lozinka;
    private SpecijalnostDoktora specijalnost;
}
