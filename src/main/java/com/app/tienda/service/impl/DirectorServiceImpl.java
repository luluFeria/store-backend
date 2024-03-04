package com.app.tienda.service.impl;

import com.app.tienda.service.IDirectorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class DirectorServiceImpl implements IDirectorService {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  private ArrayList<Director> listaDirectores = new ArrayList<>();
  @Override
  public ArrayList<Director> getAll() {
    log.info("Entrando a  service - getAll");
    return listaDirectores;
  }

  @Override
  public Director save(Director director) {
    log.info("Entrando a la funcion Service-save");
    log.info("Parametro: {}", director);

    listaDirectores.add(director);
    return director;
  }

  @Override
  public Director getById(Long id) {
    log.info("Entrando a  service -getById");
    log.info("Parametro: {}", id);

    Director directorEncontrado = listaDirectores.stream()
            .filter(director -> director.getId() == id)
            .findFirst()
            .orElse(null);

    return directorEncontrado;
  }

  @Override
  public Boolean update(Long id, Director director) {
    log.info("Entrando a  service - update");
    log.info("Parametro id: {}", id);
    log.info("Parametro director: {}", director);

    Optional<Director> directorOptional = listaDirectores.stream()
            .filter(dire -> dire.getId() == id)
            .findFirst();

    if (directorOptional.isPresent()) {
      Director directorEncontrado = directorOptional.get();

      directorEncontrado.setId(director.getId());
      directorEncontrado.setName(director.getName());
      directorEncontrado.setLastName(director.getLastName());
      directorEncontrado.setSecondLastName(director.getSecondLastName());
      directorEncontrado.setAge(director.getAge());
      directorEncontrado.setEmail(director.getEmail());
      directorEncontrado.setPhone(director.getPhone());
      directorEncontrado.setAddress(director.getAddress());

      return true;
    }

    return false;
  }

  @Override
  public Boolean delete(Long id) {
    log.info("Entrando a  service - delete");
    log.info("Parametro id: {}", id);

    Boolean eliminarDirector = listaDirectores.removeIf(director -> director.getId() == id);
    log.info("Resultado: {}", eliminarDirector);

    return eliminarDirector;
  }
}
