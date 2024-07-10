package com.app.tienda.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class ProviderOrderRequest {
  private Long id;

  private LocalDateTime date;

  @NotBlank(message = "Obligatoria")
  @Size(max = 50, message = "Máximo 50 caracteres")
  private String status;

  private Double total;

  private Long providerId;

}



