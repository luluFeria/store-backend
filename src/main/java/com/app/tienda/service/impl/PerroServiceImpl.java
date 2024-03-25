package com.app.tienda.service.impl;

import com.app.tienda.entity.PerroEntity;
import com.app.tienda.model.response.PerroResponse;
import com.app.tienda.repository.PerroRepository;
import com.app.tienda.service.IPerroService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerroServiceImpl implements IPerroService {
  private final Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private PerroRepository perroRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<PerroResponse> findAllPerros() {

    List<PerroEntity> perros = perroRepository.findAll();

    return perros.stream()
            .map(perroEntity -> modelMapper.map(perroEntity, PerroResponse.class))
            .collect(Collectors.toList());
  }

}
