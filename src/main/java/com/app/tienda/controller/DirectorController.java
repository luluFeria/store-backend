package com.app.tienda.controller;

import com.app.tienda.model.request.DirectorRequest;
import com.app.tienda.model.response.DirectorResponse;
import com.app.tienda.service.IDirectorService;
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
@RequestMapping("/api/v1/directores")
public class DirectorController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IDirectorService directorService;

  @GetMapping
  public List<DirectorResponse> getAllDirectors() {
  log.info("Entrando a la funcion getAllDirectors");

  return directorService.findAllDirectors();
  }

  @PostMapping
  public ResponseEntity<?> create(
          @Valid @RequestBody DirectorRequest directorRequest,
          BindingResult bindingResult
  ) {
    log.info("Entrando a la funcion create");
    log.info("Creating director: {}", directorRequest);

    if (bindingResult.hasErrors()) {
      log.info("Se ha producido un error: {}", bindingResult.hasErrors());

      List<String> errors = bindingResult.getFieldErrors().stream()
            .map(error -> error.getField() + ": " + error.getDefaultMessage())
            .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    DirectorResponse directorSaved = directorService.save(directorRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(directorSaved);
  }




}
