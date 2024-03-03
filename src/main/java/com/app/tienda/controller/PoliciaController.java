package com.app.tienda.controller;

import com.app.tienda.model.request.Policia;
import com.app.tienda.model.request.Profesor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/policias")
public class PoliciaController {

  private final Logger log = LoggerFactory.getLogger(this.getClass());

  ArrayList<Policia> listaPolicias = new ArrayList<>();

  @GetMapping
  public ArrayList<Policia> getAll() {
    log.info("Entrando a la funcion lista Policia");

    return listaPolicias;
  }

  @PostMapping
  private ResponseEntity<String> save(@RequestBody Policia policia) {
    log.info("Entrando a la funcion save()");
    log.info("Mi parametro es: {}", policia);

    listaPolicias.add(policia);

    return new ResponseEntity<>("Se ha agregado con exito el policia", HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  private ResponseEntity<Policia> getById(@PathVariable Long id) {
    log.info("Entrando a la funcion getById");
    log.info("Parametro recibido {}", id);

    Policia policiaEncontrado = listaPolicias.stream()
     .filter(policia -> policia.getId() == id)
     .findFirst()
     .orElse(null);

    if (policiaEncontrado != null) {
      return new ResponseEntity<>(policiaEncontrado, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PutMapping("/{id}")
  private ResponseEntity<String> update(@PathVariable Long id, @RequestBody Policia policia) {
    log.info("Entrando a la funcion update");
    log.info("Parametro recibido {}", id);
    log.info("Parametro recibido {}", policia);

    Optional<Policia> policiaOptional = listaPolicias.stream()
       .filter(pol -> pol.getId() == id)
       .findFirst();

    if (policiaOptional.isPresent()) {
      Policia policiaEncontrado = policiaOptional.get();

      policiaEncontrado.setId(policia.getId());
      policiaEncontrado.setName(policia.getName());
      policiaEncontrado.setLastName(policia.getLastName());
      policiaEncontrado.setSecondLastName(policia.getSecondLastName());
      policiaEncontrado.setAge(policia.getAge());
      policiaEncontrado.setEmail(policia.getEmail());
      policiaEncontrado.setPhone(policia.getPhone());
      policiaEncontrado.setAddress(policia.getAddress());

      return ResponseEntity.ok(" Se ha actualizado con exito el policia");
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body("No se encontró el policia con ID: " + id + ". No se realizó ninguna actualización.");
    }
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<String> delete(@PathVariable Long id) {
    log.info("Entrando a la funcion delete");
    log.info("Parametro recibido {}", id);

    boolean policiaEliminado = listaPolicias.removeIf(pol -> pol.getId() == id);
    log.info("Variable bolean {}", policiaEliminado);

    if (policiaEliminado) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el policia con ID: " + id);
    }
  }


}
