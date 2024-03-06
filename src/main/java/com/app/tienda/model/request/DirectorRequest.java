package com.app.tienda.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.*;

@Getter
@Setter
@ToString
public class DirectorRequest {

  @NotBlank(message = "Nombre obligatorio")
  @Size(min = 3, max = 20, message = "El nombre debe de contener de 3 a 20 caracteres")
  private String name;

  @NotBlank(message = "Apellido obligatorio")
  private String firstName;

  private String secondName;

  @NotNull(message = "Edad obligatorio")
  @Min(value = 18, message = "El valor debe ser igual o mayor que 18")
  @Max(value = 120, message = "El valor debe ser igual o menor que 120")
  private Integer age;

  private String gender;
}
