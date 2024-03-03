package com.app.tienda.controller;

import com.app.tienda.model.request.Policia;
import com.app.tienda.service.IPoliciaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/policias")
public class PoliciaController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private IPoliciaService policiaService;

  // El autowired es el patron que se encarga de crear el objeto de la clase externa
  // y suminstrarlo a la clase que lo requiera.

  @GetMapping
  public ArrayList<Policia> getAll() {
    log.info("Entrando al policiaController - getAll");

    return policiaService.getAll();
  }

  @PostMapping
  private ResponseEntity<Policia> save(@RequestBody Policia policia) {
    log.info("PoliciaController - save");
    log.info("El parametro es: {}", policia);

    Policia policiaAgregado =  policiaService.save(policia);

    return new ResponseEntity<>(policiaAgregado, HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  private ResponseEntity<Policia> getById(@PathVariable Long id) {
    log.info("Entrando a mi funcion getById");
    log.info("parametro: {}", id);

    Policia policiaEncontrado = policiaService.getById(id);

    if (policiaEncontrado != null) {
      return new ResponseEntity<>(policiaEncontrado, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{id}")
  private ResponseEntity<String> update(@PathVariable Long id,
                                        @RequestBody Policia policia
  ) {
    log.info("Funcion update");
    log.info("Parametro path: {}", id);
    log.info("Parametro body: {}", policia);

    Boolean policyUpdated = policiaService.update(id, policia);

    if (policyUpdated) {
      return ResponseEntity.ok("Se ha modificado el policia con exito");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body(("No se encontro el id" + id));
    }
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<String> delete(@PathVariable Long id) {
    log.info("Metodo delete");
    log.info("id: {}", id);

    Boolean policiaEliminado = policiaService.delete(id);

    if (policiaEliminado) {
      log.info("Se elimino correctamente");
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontro ningun policia con ID: " + id);
    }
  }
}
