package com.app.tienda.model.request;

import com.app.tienda.entity.AddressEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class CustomerRequest {
  @NotBlank(message = "El nombre no puede estar en blanco")
  @Size(min = 3, max = 20, message = "El nombre debe de contener de 3 a 20 caracteres")
  private String name;

  @NotBlank(message = "La dirección no puede estar en blanco")
  private AddressEntity address;

  @Pattern(regexp = "\\d{10}", message = "El número de teléfono debe tener 10 dígitos")
  private String phone;

  @Email(message = "El correo debe ser válido")
  private String email;

  private String gender;
}
