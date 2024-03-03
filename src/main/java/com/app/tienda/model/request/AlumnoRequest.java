package com.app.tienda.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class AlumnoRequest {

  @NotBlank(message = "Nombre obligatorio")
  @Size(min = 3, max = 20, message = "El nombre debe de contener de 3 a 20 caracteres")
  private String name;

  @NotBlank(message = "Apellido obligatorio")
  private String firstName;

  private String secondName;

  @NotNull(message = "Edad obligatorio")
  @Min(value=2, message = "La edad debe contener minimo 2 digitos")
  private Integer age;

  private String gender;
}
