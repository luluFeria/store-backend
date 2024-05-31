package com.app.tienda.controller;

import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
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

    if (bindingResult.hasErrors()) {
      log.info("Se ha producido un error: {}", bindingResult.hasErrors());

      List<String> errors = bindingResult.getFieldErrors().stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    CustomerResponse customerSaved = customerService.save(customerRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(customerSaved);
  }

  @GetMapping("/{id}")
  private ResponseEntity<CustomerResponse> findById(@PathVariable Long id) {
    log.info("Fetching customer by id: {}", id);

    return new ResponseEntity<>(customerService.getById(id), HttpStatus.OK);
  }

  @GetMapping("/city/{city}")
  private ResponseEntity<List<CustomerResponse>> findByCity(@PathVariable String city) {
    log.info("Fetching customer by city: {}", city);

    return new ResponseEntity<>(customerService.getByCity(city), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  private ResponseEntity<CustomerResponse> findByName(@PathVariable String name) {
    log.info("Fetching customer by name: {}", name);

    return new ResponseEntity<>(customerService.getByName(name), HttpStatus.OK);
  }

  @GetMapping("/email/{email}")
  private ResponseEntity<CustomerResponse> findByEmail(@PathVariable String email) {
    log.info("Fetching customer by email: {}", email);

    return new ResponseEntity<>(customerService.getByEmail(email), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  private ResponseEntity<?> update(
          @PathVariable Long id,
          @Valid @RequestBody CustomerRequest customerRequest,
          BindingResult bindingResult
  ) {
    log.info("Updating customer by id: {}", id);

    if (bindingResult.hasErrors()) {
      log.info("Se ha producido un error: {}", bindingResult.hasErrors());

      List<String> errors = bindingResult.getFieldErrors().stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    CustomerResponse customerUpdated = customerService.update(id, customerRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(customerUpdated);
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<?> delete(@PathVariable Long id) {
    log.info("Deleting customer with id: {}", id);

    customerService.delete(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
