package com.zdravstvo.demo.model.dto;

import com.zdravstvo.demo.model.LekarskiIzvestaj;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LekarskiIzvestajResponse {

    private Long id;
    private String opis;

    private String studentImePrezime;
    private String studentJmbg;

    public LekarskiIzvestajResponse(LekarskiIzvestaj izvestaj){
        this.id = izvestaj.getId();
        this.opis = izvestaj.getOpis();
        this.studentImePrezime = izvestaj.getTermin().getStudent().getPrezime() + " " + izvestaj.getTermin().getStudent().getPrezime();
        this.studentJmbg = izvestaj.getTermin().getStudent().getJmbg();
    }
}
