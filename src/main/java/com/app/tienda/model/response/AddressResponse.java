package com.app.tienda.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AddressResponse {
  private Long id;

  private String city;

  private String municipality;

  private String colony;

  private String street;
}
