package com.app.tienda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/operaciones")
public class OperacionController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @GetMapping("/suma")
  public Integer suma(@RequestParam Integer num1, @RequestParam Integer num2) {
    log.info("Entrando a la funcion suma");
    log.info("param num1 {}", num1);
    log.info("param num2 {}", num2);
    return num1 + num2;
  };

  @GetMapping("/resta")
  public Double resta(@RequestParam("num1") Double num1, @RequestParam("num2") Double num2, @RequestParam("num3") Double num3) {
    log.info("Entrando a la función resta");
    log.info("param num1 {}", num1);
    log.info("param num2 {}", num2);
    log.info("param num3 {}", num3);
    return num1 - num2 - num3;
  };

  @GetMapping("/multiplicacion")
  public Integer multiplicacion(@RequestParam("num1") Integer num1, @RequestParam("num2") Integer num2){
    log.info("Entrando a la función multiplicacion");
    log.info("param num1 {}", num1);
    log.info("param num2 {}", num2);
    return num1 * num2;
  };

  @GetMapping("/division")
  public Integer division(@RequestParam("num1") Integer num1, @RequestParam("num2") Integer num2) {
    log.info("Entrando a la funcion division");
    log.info("param num1 {}", num1);
    log.info("param num2 {}", num2);
    return num1 / num2;
  };

  @GetMapping("/potencia")
  public Double potencia(@RequestParam("base") Integer base, @RequestParam("exponente") Integer exponente) {
    log.info("Entrando a la funcion potencia");
    log.info("param base {}", base);
    log.info("param exponente {}", exponente);
    return Math.pow(base, exponente);
  };

  @GetMapping("/raiz")
  public Double raizCuadrada(@RequestParam("num") Double num) {
    log.info("Entrando a la función raizCuadrada");
    log.info("param num {}", num);
    Double resultado = Math.sqrt(num);
    return resultado;
  };

}
