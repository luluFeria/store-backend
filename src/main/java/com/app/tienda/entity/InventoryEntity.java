package com.app.tienda.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Entity
@Table(name = "inventory")
public class InventoryEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "product_id")
  private ProductEntity product;

  private Integer stock;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd")
  private LocalDate date;
}
