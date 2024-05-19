package com.app.tienda.model.response;

import com.app.tienda.entity.AddressEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProviderResponse {

  private Long id;

  private String name;

  private String firstName;

  private String secondName;

  private AddressEntity address;

  private String phone;

  private String email;

  private String gender;
}
