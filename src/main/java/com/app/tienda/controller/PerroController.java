package com.app.tienda.controller;

import com.app.tienda.model.response.PerroResponse;
import com.app.tienda.service.IPerroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/perros")
public class PerroController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IPerroService perroService;

  @GetMapping
  public List<PerroResponse> getAllPerros() {

    return perroService.findAllPerros();
  }

}
