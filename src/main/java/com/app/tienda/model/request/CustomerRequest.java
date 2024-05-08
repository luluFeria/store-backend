package com.app.tienda.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class CustomerRequest {

  @NotBlank(message = "Nombre obligatorio")
  @Size(min = 3, max = 20, message = "El nombre debe de contener de 3 a 20 caracteres")
  private String name;

  private AddressRequest address;

  @Size(max = 10, message = " El número de teléfono debe tener como máximo 10 dígitos")
  private String phone;

  @Email(message = "El correo debe ser válido")
  private String email;

  private String gender;
}
