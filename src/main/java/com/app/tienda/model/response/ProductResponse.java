package com.app.tienda.model.response;

import com.app.tienda.entity.ProviderEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponse {
  private Long id;

  private String name;

  private String description;

  private Integer price;

  private Integer quantityInInventory;

  private ProviderResponse provider;

  private String category;
}
