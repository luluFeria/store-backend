package com.app.tienda.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.security.Provider;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name="provider_orders")
public class ProviderOrderEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ElementCollection
  @CollectionTable(name = "provider_order_product", joinColumns = @JoinColumn(name = "provider_order_id"))
  private List<ProviderOrderProduct> products;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDateTime date;

  private String status;

  private BigDecimal totalAmount;

  @ManyToOne
  @JoinColumn(name = "provider_id", nullable = false)
  private ProviderEntity provider;
}
