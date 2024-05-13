package com.zdravstvo.demo.model.dto;

import com.zdravstvo.demo.model.SpecijalnostDoktora;
import com.zdravstvo.demo.model.Termin;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TerminResponse {
    private Long id;
    private String pocetakTermina;
    private String krajTermina;
    private String imeDoktora;
    private String specijalnostDoktora;
    private String statusTermina;

    public TerminResponse(Termin termin){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        this.id = termin.getId();
        this.pocetakTermina = termin.getPocetakTermina().format(formatter);
        this.krajTermina = termin.getKrajTermina().format(formatter);
        this.imeDoktora = termin.getDoktor().getIme() + " " + termin.getDoktor().getPrezime();
        this.specijalnostDoktora = SpecijalnostDoktora.returnSpecijalnostString(termin.getDoktor().getSpecijalnost());
        this.statusTermina = termin.getStatusTermina().toString();
    }
}
