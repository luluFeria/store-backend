package com.app.tienda.service.impl;

import com.app.tienda.entity.DirectorEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.model.request.DirectorRequest;
import com.app.tienda.model.response.DirectorResponse;
import com.app.tienda.repository.DirectorRepository;
import com.app.tienda.service.IDirectorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DirectorServiceImpl implements IDirectorService {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private DirectorRepository directorRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<DirectorResponse> findAllDirectors() {
    log.info("DirectorServiceImpl - entrando a funcion findAllDirectors");

    List<DirectorEntity> directors = directorRepository.findAll();

    return directors.stream()
            .map(directorEntity -> modelMapper.map(directorEntity, DirectorResponse.class))
            .collect(Collectors.toList());
  }

  @Override
  public DirectorResponse save(DirectorRequest directorRequest) {
    log.info("DirectorServiceImpl - entrando a funcion save");

    try {
      log.info("Estoy entrando al try");
      DirectorEntity directorEntity = modelMapper.map(directorRequest, DirectorEntity.class);
      DirectorEntity savedDirector = directorRepository.save(directorEntity);
      return modelMapper.map(savedDirector, DirectorResponse.class);
    } catch (Exception e) {
      log.info("Entrando al catch");
      log.error("Hubo un error al crear el director: {}", e.getMessage());
      throw new InternalServerException("Hubo un error al crear el director");
    }
  }





}
