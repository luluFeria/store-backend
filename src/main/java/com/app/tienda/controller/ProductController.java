package com.app.tienda.controller;

import com.app.tienda.model.request.ProductRequest;
import com.app.tienda.model.request.ProviderRequest;
import com.app.tienda.model.response.ProductResponse;
import com.app.tienda.model.response.ProviderResponse;
import com.app.tienda.service.IProductService;
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
@RequestMapping("/api/v1/product")
public class ProductController {

  @Autowired
  private IProductService productService;

  @GetMapping
  public List<ProductResponse> getAll() {
    log.info("Fetching all products");

    return productService.findAll();
  }

  @PostMapping
  public ResponseEntity<Object> save(
          @Valid @RequestBody ProductRequest prroductRequest,
          BindingResult bindingResult
  ) {
    log.info("Creating product: {}", prroductRequest);

    if (bindingResult.hasErrors()) {

      List<String> errors = bindingResult.getFieldErrors().stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    ProductResponse productSaved = productService.save(prroductRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(productSaved);
  }

  @GetMapping("/{id}")
  private ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
    log.info("Fetching product by id: {}", id);

    return new ResponseEntity<>(productService.getById(id), HttpStatus.OK);
  }

  @GetMapping("/supplier/{supplierId}")
  private ResponseEntity<List<ProductResponse>> findProductBySupplier(@PathVariable Long supplierId) {
    log.info("Fetching product by supplierId: {}", supplierId);

    return new ResponseEntity<>(
      productService.findAllBySupplier(supplierId),
      HttpStatus.OK
    );
  }

  @PutMapping("/{id}")
  private ResponseEntity<?> update(
          @PathVariable Long id,
          @Valid @RequestBody ProductRequest productRequest,
          BindingResult bindingResult
  ) {
    log.info("Updating product by id: {}", id);

    if (bindingResult.hasErrors()) {

      List<String> errors = bindingResult.getFieldErrors().stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    ProductResponse productUpdated = productService.update(id, productRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(productUpdated);
  }
}
