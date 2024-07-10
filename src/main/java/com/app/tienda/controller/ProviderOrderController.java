package com.app.tienda.controller;

import com.app.tienda.model.request.ProviderOrderRequest;
import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.model.response.ProviderOrderResponse;
import com.app.tienda.service.IProviderOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders/provider")
public class ProviderOrderController {

  @Autowired
  private IProviderOrderService providerOrderService;

  @GetMapping
  public List<ProviderOrderResponse> getAll() {
    log.info("Fetching all orders to provider");

    return providerOrderService.findAll();
  }

  @GetMapping("/{id}")
  private ResponseEntity<ProviderOrderResponse> findById(@PathVariable Long id) {
    log.info("Fetching provider order by id: {}", id);

    //return new ResponseEntity<>(providerOrderService.findById(id), HttpStatus.OK);
    return null;
  }

  @PostMapping
  private ResponseEntity<?> create(@Valid @RequestBody ProviderOrderRequest providerOrderRequest, BindingResult bindingResult) {
    log.info("Creating provider order: {}", providerOrderRequest);

    if (bindingResult.hasErrors()) {
      List<String> errors = bindingResult.getFieldErrors().stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }
    ProviderOrderResponse orderSaved = providerOrderService.save(providerOrderRequest);
    log.info("Provider order successfully created: {}", orderSaved);

    return ResponseEntity.status(HttpStatus.CREATED).body(orderSaved);
  }

}
