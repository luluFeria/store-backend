package com.app.tienda.controller;

import com.app.tienda.model.response.ProviderResponse;
import com.app.tienda.service.IProviderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
