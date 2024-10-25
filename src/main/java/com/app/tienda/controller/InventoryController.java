package com.app.tienda.controller;

import com.app.tienda.model.response.InventoryResponse;
import com.app.tienda.service.IInventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {

  @Autowired
  private IInventoryService inventoryService;

  @GetMapping("/product/{id}")
  private ResponseEntity<InventoryResponse> findProductById(@PathVariable Long id) {
    log.info("findProducts {}", id);

    return null;
  }

  @GetMapping
  public List<InventoryResponse> getAll() {
    log.info("get all inventory");

    this.inventoryService.findAll();

    return null;
  }

  @GetMapping("/category/{category}")
  private ResponseEntity<List<InventoryResponse>> findByCategory(@PathVariable String category) {
    log.info("Fetching product by category: {}", category);

    return null;
  }
}
