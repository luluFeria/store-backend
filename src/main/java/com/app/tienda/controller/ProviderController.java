package com.app.tienda.controller;

import com.app.tienda.model.request.ProviderRequest;
import com.app.tienda.model.response.ProviderResponse;
import com.app.tienda.service.IProviderService;
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
@RequestMapping("/api/v1/providers")
public class ProviderController {

  @Autowired
  private IProviderService providerService;

  @GetMapping
  public List<ProviderResponse> getAll() {
    log.info("Fetching all providers");

    return providerService.findAll();
  }

  @PostMapping
  public ResponseEntity<Object> save(
          @Valid @RequestBody ProviderRequest providerRequest,
          BindingResult bindingResult
  ) {
    log.info("Creating provider: {}", providerRequest);

    if (bindingResult.hasErrors()) {

      List<String> errors = bindingResult.getFieldErrors().stream()
             .map(error -> error.getField() + ": " + error.getDefaultMessage())
             .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    ProviderResponse providerSaved = providerService.save(providerRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(providerSaved);
  }

  @GetMapping("/{id}")
  private ResponseEntity<ProviderResponse> findById(@PathVariable Long id) {
    log.info("Fetching provider by id: {}", id);

    return new ResponseEntity<>(providerService.getById(id), HttpStatus.OK);
  }

  @GetMapping("/city/{city}")
  private ResponseEntity<List<ProviderResponse>> findByCity(@PathVariable String city) {
    log.info("Fetching provider by city: {}", city);

    return new ResponseEntity<>(providerService.getByCity(city), HttpStatus.OK);
  }

  @GetMapping("/name/{name}")
  private ResponseEntity<List<ProviderResponse>> findByName(@PathVariable String name) {
    log.info("Fetching provider by name: {}", name);

    return new ResponseEntity<>(providerService.getByName(name), HttpStatus.OK);
  }

  @GetMapping("/email/{email}")
  private ResponseEntity<ProviderResponse> findByEmail(@PathVariable String email) {
    log.info("Fetching provider by email: {}", email);

    return new ResponseEntity<>(providerService.getByEmail(email), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  private ResponseEntity<?> update(
          @PathVariable Long id,
          @Valid @RequestBody ProviderRequest providerRequest,
          BindingResult bindingResult
  ) {
    log.info("Updating provider by id: {}", id);

    if (bindingResult.hasErrors()) {

      List<String> errors = bindingResult.getFieldErrors().stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    ProviderResponse providerUpdated = providerService.update(id, providerRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(providerUpdated);
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<?> delete(@PathVariable Long id) {
    log. info("Deleting provider by id: {}", id);

    providerService.delete(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
