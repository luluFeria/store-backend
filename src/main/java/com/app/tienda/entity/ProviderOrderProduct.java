package com.app.tienda.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Embeddable
public class ProviderOrderProduct {
  @Column(name = "product_id")
  private Long productId;

  @Column(name = "quantity")
  private Integer quantity;
}
