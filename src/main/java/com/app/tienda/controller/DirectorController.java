package com.app.tienda.controller;

import com.app.tienda.model.request.Director;
import com.app.tienda.model.request.Policia;
import com.app.tienda.service.IDirectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/directores")
public class DirectorController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private IDirectorService directorService;

  @GetMapping
  public ArrayList<Director> getAll(){
    log.info("Entrando al directorController - getAll");

    //ArrayList<Director> listDirectors = directorService.getAll();
    //    return listDirectors;

    return directorService.getAll();
  }

  @PostMapping
  private ResponseEntity<String> save(@RequestBody Director director) {
    log.info("DirectorController - save");
    log.info("El parametro es: {}", director);

    Director directorAgregado = directorService.save(director);

    return new ResponseEntity<>("Se agregado con exito el director", HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  private ResponseEntity<Director> getById(@PathVariable Long id) {
    log.info("Entrando en mi funcion getById");
    log.info("El parametro es: {}", id);

    Director directorEncontrado = directorService.getById(id);

    if (directorEncontrado != null) {
      return new ResponseEntity<>(directorEncontrado, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{id}")
  private ResponseEntity<String> update(@PathVariable Long id, @RequestBody Director director) {
    log.info("Entrando a la funcion update");
    log.info("Parametro recibido Path {}", id);
    log.info("Parametro recibido Body {}", director);

    Boolean directorUpdate = directorService.update(id, director);

    if (directorUpdate) {
      return ResponseEntity.ok("Se ha modificado el director con exito");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
           .body(("No se encontro el id" + id));
    }
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<String> delete(@PathVariable Long id) {
    log.info("Entrando a la funcion delete");
    log.info("Parametro: {}" , id);

    Boolean directorEliminado = directorService.delete(id);

    if (directorEliminado) {
      log.info("Se elimino correctamente");
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el director con ID: " + id);
    }
  }
}
