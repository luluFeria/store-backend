package com.app.tienda.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Entity
@Table(name="provider_orders")
public class ProviderOrder {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  //private List<> productsOrderedWithTheirQuantities;

  private LocalDateTime date;

  private String status;

  private Double total;

  /*@OneToMany
  @JoinColumn(name = "provider_id", referencedColumnName = "id")
  private ProviderEntity provider;*/
}
