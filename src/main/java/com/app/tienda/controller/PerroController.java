package com.app.tienda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perros")
public class PerroController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @GetMapping
  public String saludo() {
    return "Buenos dias";
  };
  @GetMapping("/adios")
  public String despedida() {
    return "Adios";
  }

  @GetMapping("/{name}") //pathparam - recibiendo un parametro "name" por ruta.
  //Ejemplo:  http://localhost:8080/api/perros/tommy
  public String saludoPerro(@PathVariable String name) {
    log.info("Entrando a la funcion saludoPerro: {} ", name);
    return "Hola, " + name;
  }

  @GetMapping("/suma") //query param - recibiendo parametros por query param.
  //Ejemplo:  http://localhost:8080/api/perros/suma?num1=6&num2=4
  public Double suma(@RequestParam("num1") Double num1, @RequestParam("num2") Double num2) {
    log.info("Entrando a la funcion suma");
    log.info("param num1 {}", num1);
    log.info("param num2 {}", num2);
    return num1 + num2;
  };
}
