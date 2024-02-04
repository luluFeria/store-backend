package com.app.tienda.controller;

import com.app.tienda.model.request.Alumno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {
  private ArrayList<Alumno> listaAlumnos = new ArrayList<>();
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  //CONSULTAR TODOS LOS REGISTROS
  @GetMapping
  public ArrayList<Alumno> getAll() {
    log.info("Entrando a la funcion nombresEstudiantes");

    return listaAlumnos;
  }

  //CONSULTAR TODOS LOS REGISTROS
  @GetMapping("/{id}") //consultar un solo registro por su id
  private ResponseEntity<Alumno> getById(@PathVariable Long id) {
    log.info("Entrando a la función getById");
    log.info("Parametro recibido {}", id);

    Alumno alumnoEncontrado = listaAlumnos.stream()
            .filter(alumno -> alumno.getId() == id)
            .findFirst()
            .orElse(null);

    if (alumnoEncontrado != null) { // Se ha encontrado un alumno
      return new ResponseEntity<>(alumnoEncontrado, HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  //AGREGAR UN NUEVO REGISTRO
  @PostMapping //agregrar un nuevo registro
  private  ResponseEntity<String> save(@RequestBody Alumno alumno) { // @RequestBody representa el valor del cuerpo de la solicitud
    log.info("Method save() ");
    log.info("Mi parametro es: {}", alumno);

    listaAlumnos.add(alumno);

    return new ResponseEntity<>("Se ha agregado con exito el alumno", HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  private ResponseEntity<String> update(@PathVariable Long id, @RequestBody Alumno alumno) {
    log.info("Entrando a la funcion update");
    log.info("Parametro recibido {}", id);
    log.info("Parametro recibido {}", alumno);

    Optional<Alumno> alumnoOptional = listaAlumnos.stream()
            .filter(alumn -> alumn.getId() == id)
            .findFirst();

    if (alumnoOptional.isPresent()) {
      Alumno alumnoEncontrado = alumnoOptional.get();
      alumnoEncontrado.setId(alumno.getId());
      alumnoEncontrado.setName(alumno.getName());
      alumnoEncontrado.setFirstName(alumno.getFirstName());
      alumnoEncontrado.setSecondName(alumno.getSecondName());
      alumnoEncontrado.setAge(alumno.getAge());
      alumnoEncontrado.setGender(alumno.getGender());
      // Actualizar otras propiedades según sea necesario
      return ResponseEntity.ok(" Se ha actualizado con exito el alumno");
    } else {
      // Si no se encuentra el alumno, devolver un mensaje indicando que no se pudo actualizar
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
              .body("No se encontró el alumno con ID: " + id + ". No se realizó ninguna actualización.");
    }
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<String> delete(@PathVariable Long id) {
    log.info("Entrando a la funcion delete");
    log.info("Parametro {}",id);

    boolean alumnoEliminado = listaAlumnos.removeIf(alumno -> alumno.getId() == id);
    log.info("Variable boolean {}", alumnoEliminado);

    if (alumnoEliminado) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el alumno con ID: " + id);
    }

  }

  }

