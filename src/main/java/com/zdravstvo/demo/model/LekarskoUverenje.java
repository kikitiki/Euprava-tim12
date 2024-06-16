package com.zdravstvo.demo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "uverenja")
public class LekarskoUverenje {
    @Id@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String studentJmbg;

    private String poruka;

    public LekarskoUverenje(String studentJmbg,String poruka){
        this.studentJmbg = studentJmbg;
        this.poruka = poruka;
    }
}
