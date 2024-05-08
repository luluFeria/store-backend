package com.app.tienda.service;

import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.CustomerResponse;

import java.util.List;

public interface ICustomerService {
  public List<CustomerResponse> findAllCustomer();

  public CustomerResponse save(CustomerRequest customerRequest);
}
