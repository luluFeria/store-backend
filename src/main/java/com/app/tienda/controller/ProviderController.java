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
@RequestMapping("/api/v1/provider")
public class ProviderController {

  @Autowired
  private IProviderService providerService;

  @GetMapping
  public List<ProviderResponse> getAll() {
    log.info("Fetching all providers");

    return providerService.findAll();
  }

  @PostMapping
  public ResponseEntity<?> save(
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



}
