package com.zdravstvo.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LekarskiIzvestajRequest {
    @NotNull
    @NotEmpty
    private String opis;

    @NotNull
    private String terminId;
}
