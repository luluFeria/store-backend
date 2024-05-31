package com.app.tienda.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Entity
@Table(name="products")
public class ProductEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String description;

  private Integer price;

  private Integer quantityInInventory;

  @ManyToOne
  @JoinColumn(name = "provider_id")
  private ProviderEntity providers;

  private String category;
}
