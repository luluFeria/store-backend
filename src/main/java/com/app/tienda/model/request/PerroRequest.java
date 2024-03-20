package com.app.tienda.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
public class PerroRequest {

  @NotBlank(message = "Nombre obligatorio")
  @Size(min = 3, max = 20, message = "El nombre debe de contener de 3 a 20 caracteres")
  private String name;

  @NotBlank(message = "Raza obligatoria")
  private String breed;

  @NotNull(message = "Edad obligatoria")
  @Min(value = 1, message = "El valor debe ser igual o mayor que 1")
  @Max(value = 20, message = "El valor debe ser igual o menor que 20")
  private Integer age;

  private String gender;
}
