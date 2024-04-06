package com.app.tienda.controller;

import com.app.tienda.model.request.PerroRequest;
import com.app.tienda.model.response.PerroResponse;
import com.app.tienda.service.IPerroService;
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
@RequestMapping("/api/v1/perros")
public class PerroController {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private IPerroService perroService;

  @GetMapping
  public List<PerroResponse> getAllPerros() {
    log.info("Fetching all the dogs");

    return perroService.findAllPerros();
  }

  @PostMapping
  public ResponseEntity<?> create(
          @Valid @RequestBody PerroRequest perroRequest,
          BindingResult bindingResult
  ) {
    log.info("Creating dog: {}", perroRequest);
    
   if (bindingResult.hasErrors()) {

     List<String> errors = bindingResult.getFieldErrors().stream()
              .map(error -> error.getField() + ": " + error.getDefaultMessage())
              .collect(Collectors.toList());

     return ResponseEntity.badRequest().body(errors);
   }

   PerroResponse perroSaved = perroService.save(perroRequest);

   return ResponseEntity.status(HttpStatus.CREATED).body(perroSaved);
  }

  @GetMapping("/{id}")
  private ResponseEntity<PerroResponse> findById(@PathVariable Long id) {
    log.info("Fetching dog by id: {}", id);

    return new ResponseEntity<>(perroService.getById(id), HttpStatus.OK);
  }

  @PutMapping("/{id}")
  private ResponseEntity<?> update(
          @PathVariable Long id,
          @Valid @RequestBody PerroRequest perroRequest,
          BindingResult bindingResult
  ) {
    log.info("Updating dog by id {}", id);

    if (bindingResult.hasErrors()) {

      List<String> errors = bindingResult.getFieldErrors().stream()
             .map(error -> error.getField() + ": " + error.getDefaultMessage())
             .collect(Collectors.toList());

      return ResponseEntity.badRequest().body(errors);
    }

    PerroResponse perroUpdated = perroService.update(id, perroRequest);

    return ResponseEntity.status(HttpStatus.CREATED).body(perroUpdated);
  }

  @DeleteMapping("/{id}")
  private ResponseEntity<?> delete(@PathVariable Long id) {
    log.info("Deleting dog with id: {}", id);

    perroService.delete(id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
