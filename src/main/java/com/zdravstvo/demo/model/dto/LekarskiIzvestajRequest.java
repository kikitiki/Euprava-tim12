package com.zdravstvo.demo.model.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


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
