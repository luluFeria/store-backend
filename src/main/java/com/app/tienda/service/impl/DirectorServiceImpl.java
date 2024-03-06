package com.app.tienda.service.impl;

import com.app.tienda.entity.DirectorEntity;
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
}
