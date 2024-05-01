package com.app.tienda.controller;

import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/customer")
public class CustomerController {

  @Autowired
  private ICustomerService customerService;

  @GetMapping
  public List<CustomerResponse> getAllCustomers() {
    log.info("Fetching all customers");
    return customerService.findAllCustomer();
  }




}
