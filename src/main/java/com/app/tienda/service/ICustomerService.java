package com.app.tienda.service;

import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.CustomerResponse;

import java.util.List;

public interface ICustomerService {
  public List<CustomerResponse> findAllCustomer();
  public CustomerResponse save(CustomerRequest customerRequest);
  public CustomerResponse getById(Long id);
  public List<CustomerResponse> getByCity(String city);
  public CustomerResponse getByName(String name);
  public CustomerResponse getByEmail(String email);
  public CustomerResponse update(Long id, CustomerRequest customerRequest);
  public void delete(Long id);
}
