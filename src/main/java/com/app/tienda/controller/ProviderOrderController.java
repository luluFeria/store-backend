package com.app.tienda.controller;

import com.app.tienda.enums.OrderStatus;
import com.app.tienda.model.request.ProviderOrderRequest;
import com.app.tienda.model.response.ProviderOrderWithDetailsResponse;
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
@RequestMapping("/api/v1/orders/providers")
public class ProviderOrderController {
  @Autowired
  private IProviderOrderService providerOrderService;

  //Probar este medtodo
  @GetMapping
  public List<ProviderOrderWithDetailsResponse> getAllWithDetails() {
    log.info("Fetching all orders to provider");

    return providerOrderService.findAllWithDetails();
  }

  @GetMapping("/{id}")
  private ResponseEntity<ProviderOrderWithDetailsResponse> findById(@PathVariable Long id) {
    log.info("Fetching provider order by id: {}", id);

    return new ResponseEntity<>(providerOrderService.findById(id), HttpStatus.OK);
  }

  @GetMapping("/provider/{providerId}")
  private ResponseEntity<List<ProviderOrderWithDetailsResponse>> findByProviderId(@PathVariable Long providerId) {
    log.info("Fetching provider orders by providerId: {}", providerId);

    return new ResponseEntity<>(providerOrderService.getByProviderId(providerId), HttpStatus.OK);
  }

  @GetMapping("/status/{status}")
  private ResponseEntity<List<ProviderOrderWithDetailsResponse>> findByStatus(@PathVariable String status) {
    log.info("Fetching provider orders by status: {}", status);

    return new ResponseEntity<>(providerOrderService.findByStatus(status), HttpStatus.OK);
  }

  @PutMapping("/updateStatus/{id}")
  public ResponseEntity<String> updateStatus(@PathVariable Long id, @RequestBody String status) {
    log.info("Updating order status by id: {}, {}", id, status);

    providerOrderService.updateStatus(id, status);

    return ResponseEntity.ok("Status was updated successfully");
  }

  //Probar este metodo
  @PostMapping
  private ResponseEntity<?> create(@Valid @RequestBody ProviderOrderRequest providerOrderRequest, BindingResult bindingResult) {
    log.info("Creating provider order: {}", providerOrderRequest);

    if (bindingResult.hasErrors()) {
      List<String> errors = bindingResult.getFieldErrors().stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    String orderSaved = providerOrderService.save(providerOrderRequest);

    log.info("Provider order successfully created: {}", orderSaved);

    return ResponseEntity.status(HttpStatus.CREATED).body(orderSaved);
  }

  @PutMapping("/{id}")
  private ResponseEntity<?> update(
          @PathVariable Long id,
          @Valid @RequestBody ProviderOrderRequest providerOrderRequest,
          BindingResult bindingResult
  ) {
    log.info("Updating provider order by id: {}", id);

    if (bindingResult.hasErrors()) {

      List<String> errors = bindingResult.getFieldErrors().stream()
             .map(error -> error.getField() + ": " + error.getDefaultMessage())
             .collect(Collectors.toList());

      log.info("Errors: {}", errors);

      return ResponseEntity.badRequest().body(errors);
    }

    ProviderOrderWithDetailsResponse orderUpdated = providerOrderService.update(id, providerOrderRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(orderUpdated);
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<?> delete(@PathVariable Long id) {
    log.info("Deleting provider order with id: {}", id);

    providerOrderService.delete(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}
