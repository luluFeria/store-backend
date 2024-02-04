package com.app.tienda.controller;

import com.app.tienda.model.request.Gato;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/gatos")
public class GatoController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private ArrayList<Gato> listaGatos = new ArrayList<>();

  @GetMapping
  public ArrayList<Gato> getAll() {
    log.info("Entrando a la funcion lista Gato");

    return listaGatos;
  }

  @PostMapping
  private ResponseEntity<String> save(@RequestBody Gato gato) {
    log.info("Entrando a la funcion save()");
    log.info("Mi parametro es: {}", gato);

    listaGatos.add(gato);

    return new ResponseEntity<>("Se ha agregado con exito el gato", HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  private ResponseEntity<Gato> getById(@PathVariable Long id) {
    log.info("Entrando a la funcion getById");
    log.info("Parametro recibido {}", id);

    Gato gatoEncontrado = listaGatos.stream()
          .filter(gato -> gato.getId() == id)
          .findFirst()
          .orElse(null);

    if (gatoEncontrado != null) {
      return new ResponseEntity<>(gatoEncontrado, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{id}")
  private ResponseEntity<String> update(@PathVariable Long id, @RequestBody Gato gato) {
    log.info("Entrando a la funcion update");
    log.info("Parametro recibido {}", id);
    log.info("Parametro recibido {}", gato);

    Optional<Gato> gatoOptional = listaGatos.stream()
      .filter(gatoo -> gato.getId() == id)
      .findFirst();

    if (gatoOptional.isPresent()) {
      Gato gatoEncontrado = gatoOptional.get();
      gatoEncontrado.setId(gato.getId());
      gatoEncontrado.setName(gato.getName());
      gatoEncontrado.setAge(gato.getAge());
      gatoEncontrado.setBreed(gato.getBreed());
      gatoEncontrado.setHobbies(gato.getHobbies());

      return ResponseEntity.ok(" Se ha actualizado con exito el gato");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body("No se encontró el gato con ID: " + id + ". No se realizó ninguna actualización.");
    }
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<String> delete(@PathVariable Long id) {
    log.info("Entrando a la funcion delete");
    log.info("Parametro recibido {}", id);

    boolean gatoEliminado = listaGatos.removeIf(gato -> gato.getId() == id);
    log.info("Variable boolean {}", gatoEliminado);

    if (gatoEliminado) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el gato con ID: " + id);
    }
  }
}
