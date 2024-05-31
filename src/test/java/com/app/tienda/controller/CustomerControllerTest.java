package com.app.tienda.controller;

import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

  @InjectMocks
  private CustomerController customerController;

  @Mock
  private ICustomerService customerService;
  @Test
  void getAll() {
    List<CustomerResponse> customers = new ArrayList<>();
    CustomerResponse customer = new CustomerResponse();
    customer.setId(1l);
    customer.setName("lulu");
    customer.setAddress(null);
    customer.setPhone("5544345");
    customer.setEmail("lulu@gmail.com");
    customer.setGender("F");
    customers.add(customer);

    // Configurar el comportamiento del metodo findAllCustomer mockeado
    when(customerService.findAllCustomer()).thenReturn(customers);

    List<CustomerResponse> result = customerController.getAll();

    assertNotNull(result);
    assertEquals(customers, result);
    assertEquals(1, result.size());
  }

  @Test
  void save() {
  }
}