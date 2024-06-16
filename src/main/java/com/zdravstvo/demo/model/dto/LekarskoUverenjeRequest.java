package com.zdravstvo.demo.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@NoArgsConstructor
@AllArgsConstructor
@Data
public class LekarskoUverenjeRequest {
    @NotNull
    @NotBlank
    @Size(min = 13, max = 13)
    private String jmbg;

    @NotNull
    @NotBlank
    private String ime;

    @NotNull
    @NotBlank
    private String prezime;
}
