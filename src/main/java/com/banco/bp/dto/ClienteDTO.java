package com.banco.bp.dto;

import com.banco.bp.enums.Genero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nombre;
    private Genero genero;
    private String identificacion;
    private String direccion;
    private String telefono;
    private String password;
    private Boolean estado;
}
