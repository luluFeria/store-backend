package com.app.tienda.model.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AddressRequest {
  @NotBlank(message = "Ciudad obligatoria")
  @Size(min = 3, max = 50, message = "La ciudad debe tener entre 3 y 50 caracteres")
  private String city;

  @NotBlank(message = "Municipio obligatorio")
  @Size(min = 3, max = 50, message = "El municipio debe tener entre 3 y 50 caracteres")
  private String municipality;

  @NotBlank(message = "Colonia obligatoria")
  @Size(min = 3, max = 50, message = "La colonia debe tener entre 3 y 50 caracteres")
  private String colony;

  @NotBlank(message = "Calle obligatoria")
  @Size(min = 3, max = 50, message = "La calle debe tener entre 3 y 50 caracteres")
  private String street;
}
