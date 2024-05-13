package com.zdravstvo.demo.model.dto;

import com.zdravstvo.demo.model.Termin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TerminDoktorResponse {

    private Long id;
    private String imeStudenta;
    private String jmbgStudenta;

    private String pocetakTermina;
    private String krajTermina;
    private String statusTermina;

    private TerminDoktorResponse(Termin termin){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.id = termin.getId();
        if (termin.getStudent() != null){
            this.imeStudenta = termin.getStudent().getIme() + " " + termin.getStudent().getPrezime();
            this.jmbgStudenta = termin.getStudent().getJmbg();
        }else {
            this.imeStudenta = "-";
            this.jmbgStudenta = "-";
        }

        this.pocetakTermina = termin.getPocetakTermina().format(formatter);
        this.krajTermina = termin.getKrajTermina().format(formatter);
        this.statusTermina = termin.getStatusTermina().toString();
    }
}
