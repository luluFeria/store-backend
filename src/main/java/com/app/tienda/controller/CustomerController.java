package com.app.tienda.controller;

import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.model.response.PersonResponse;
import com.app.tienda.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/api/v1/customer")
public class CustomerController {

  @Autowired
  private ICustomerService customerService;

  @GetMapping
  public List<CustomerResponse> getAll() {
    log.info("Fetching all customers");
    return customerService.findAllCustomer();
  }

  @PostMapping
  public ResponseEntity<?> save(
          @Valid @RequestBody CustomerRequest customerRequest,
          BindingResult bindingResult
  ) {
    log.info("Creating customer: {}", customerRequest);
    log.info("Name: {}", customerRequest.getName());

    if (bindingResult.hasErrors()) {
      log.info("Se ha producido un error: {}", bindingResult.hasErrors());

      List<String> errors = bindingResult.getFieldErrors().stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.toList());

      log.info("errors: {}", errors);

      return ResponseEntity.badRequest().body(errors);
    }

    CustomerResponse customerSaved = customerService.save(customerRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(customerSaved);
  }

  @GetMapping("/{id}")
  private ResponseEntity<CustomerResponse> finById(@PathVariable Long id) {
    log.info("Fetching customer by id: {}", id);
    
    return null;
  }



}
