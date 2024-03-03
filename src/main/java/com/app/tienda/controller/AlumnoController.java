package com.app.tienda.controller;

import com.app.tienda.model.request.AlumnoRequest;
import com.app.tienda.model.response.AlumnoResponse;
import com.app.tienda.service.IAlumnoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/students")
public class AlumnoController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IAlumnoService alumnoService;

  @GetMapping
  public List<AlumnoResponse> getAllStudents() {
    log.info("Entrando a la funcion getAllStudents");

    return alumnoService.findAllAlumnos();
  }

  @PostMapping
  public ResponseEntity<?> createStudent(
          @Valid @RequestBody AlumnoRequest alumnoRequest,
          BindingResult bindingResult
  ) {
    log.info("Entrando a la funcion createStudent");
    log.info("Creating student: {}", alumnoRequest);

    if (bindingResult.hasErrors()) {
      log.info("Se ha producido un error: {}", bindingResult.hasErrors());

      List<String> errors = bindingResult.getFieldErrors().stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

     AlumnoResponse alumnoSaved = alumnoService.save(alumnoRequest);

     return ResponseEntity.status(HttpStatus.CREATED).body(alumnoSaved);
  }

  @GetMapping("/{id}")
  private ResponseEntity<AlumnoResponse> finById(@PathVariable Long id) {
    log.info("Entrando a la funcion findById");
    log.info("Parameter: {}", id);
    log.info("Fetching student by id: {}", id);

    return new ResponseEntity<>(alumnoService.getById(id), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  private ResponseEntity<?> updateStudent(
          @PathVariable Long id,
          @Valid @RequestBody AlumnoRequest alumnoRequest,
          BindingResult bindingResult
  ) {
    log.info("Entering the update student function");
    log.info("Parameter id {}", id);
    log.info("Updating student by id {}", id);

    if (bindingResult.hasErrors()) {
      log.info("An error has occurred: {}", bindingResult.hasErrors());

      List<String> errors = bindingResult.getFieldErrors().stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    AlumnoResponse alumnoUpdated = alumnoService.updateStudent(id, alumnoRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(alumnoUpdated);
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<?> delete(@PathVariable Long id) {
    log.info("Entering the delete function");
    log.info("Parameter {}", id);
    log.info("Deleting student with id: {}", id);

    alumnoService.delete(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

}

