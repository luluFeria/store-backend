package com.app.tienda.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class ProviderOrderRequest {
  private List<ProductOrder> products;

  @NotNull(message = "Requerido")
  private Long providerId;
}



