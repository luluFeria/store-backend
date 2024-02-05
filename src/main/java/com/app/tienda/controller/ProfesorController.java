package com.app.tienda.controller;


import com.app.tienda.model.request.Profesor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/profesores")
public class ProfesorController {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private ArrayList<Profesor> listaProfesores = new ArrayList<>();

  @GetMapping
  public ArrayList<Profesor> getAll() {
    log.info("Entrando a la funcion lista Profesor");

    return listaProfesores;
  }

  @PostMapping
  private ResponseEntity<String> save(@RequestBody Profesor profesor) {
    log.info("Entrando a la funcion save()");
    log.info("Mi parametro es: {}", profesor);

    listaProfesores.add(profesor);

    return new ResponseEntity<>("Se ha agregado con exito el profesor", HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  private ResponseEntity<Profesor> getById(@PathVariable Long id) {
    log.info("Entrando a la funcion getById");
    log.info("Parametro recibido {}", id);

    Profesor profesorEncontrado = listaProfesores.stream()
        .filter(profesor -> profesor.getId() == id)
        .findFirst()
        .orElse(null);

    if (profesorEncontrado != null) {
      return new ResponseEntity<>(profesorEncontrado, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{id}")
  private ResponseEntity<String> update(@PathVariable Long id, @RequestBody Profesor profesor) {
    log.info("Entrando a la funcion update");
    log.info("Parametro recibido {}", id);
    log.info("Parametro recibido {}", profesor);

    Optional<Profesor> profesorOptional = listaProfesores.stream()
      .filter(profe -> profe.getId() == id)
      .findFirst();

    if (profesorOptional.isPresent()) {
      Profesor profesorEncontrado = profesorOptional.get();

      profesorEncontrado.setId(profesor.getId());
      profesorEncontrado.setName(profesor.getName());
      profesorEncontrado.setLastName(profesor.getLastName());
      profesorEncontrado.setSecondLastName(profesor.getSecondLastName());
      profesorEncontrado.setAge(profesor.getAge());
      profesorEncontrado.setEmail(profesor.getEmail());
      profesorEncontrado.setPhone(profesor.getPhone());
      profesorEncontrado.setAddress(profesor.getAddress());

      return ResponseEntity.ok(" Se ha actualizado con exito el profesor");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body("No se encontró el profesor con ID: " + id + ". No se realizó ninguna actualización.");
    }
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<String> delete(@PathVariable Long id) {
    log.info("Entrando a la funcion delete");
    log.info("Parametro recibido {}", id);

    boolean profesorEliminado = listaProfesores.removeIf(profesor -> profesor.getId() == id);
    log.info("Variable bolean {}", profesorEliminado);

    if (profesorEliminado) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el profesor con ID: " + id);
    }
  }





}
