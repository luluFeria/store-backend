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
public class ProviderRequest {

  @NotBlank(message = "Obligatorio")
  @Size(min = 3, max = 20, message = "Nombre: de 3 a 20 caracteres")
  private String name;

  private AddressRequest address;

  @Size(max = 10, message = "Teléfono: máximo 10 dígitos")
  private String phone;

  @Email(message = "Correo inválido")
  private String email;
}
