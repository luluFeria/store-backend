package com.app.tienda.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name="customers")
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToOne
  @JoinColumn(name = "address_id", referencedColumnName = "id")
  private AddressEntity address;

  //@Column(name = "phone") // se utiliza para cambiar el nombre de un atributo, y es opcional.
  private String phone;
  private String email;
  private String gender;
}
