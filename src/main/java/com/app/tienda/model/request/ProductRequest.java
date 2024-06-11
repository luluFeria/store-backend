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
public class ProductRequest {
  @NotBlank(message = "Obligatorio")
  @Size(min = 3, max = 20, message = "Nombre: de 3 a 20 caracteres")
  private String name;

  @NotBlank(message = "Obligatoria")
  @Size(max = 50, message = "Máximo 50 caracteres")
  private String description;

  @NotNull(message = "Obligatorio")
  @Min(value = 1, message = "El precio debe ser mayor que 0")
  private Integer price;

  @NotNull(message = "Obligatorio")
  @Min(value = 0, message = "La cantidad en inventario debe ser mayor o igual a 0")
  private Integer quantityInInventory;

  @NotNull(message = "Obligatorio")
  private Long idProvider;

  @NotBlank(message = "Obligatorio")
  private String category;
}
