package com.app.tienda.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductOrder {
  private Long productId;

  private Integer quantity;
}
