package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.PerroEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.PerroRequest;
import com.app.tienda.model.response.PerroResponse;
import com.app.tienda.repository.PerroRepository;
import com.app.tienda.service.IPerroService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

  @Override
  public PerroResponse save(PerroRequest perroRequest) {

    try {
      PerroEntity perroEntity = modelMapper.map(perroRequest, PerroEntity.class);
      PerroEntity savedPerro = perroRepository.save(perroEntity);
      return modelMapper.map(savedPerro, PerroResponse.class);
    } catch (Exception e) {
      log.error("Hubo un error al crear el perro: {}", e.getMessage());
      throw new InternalServerException(Message.SAVE_ERROR + "el perro");
    }
  }

  @Override
  public PerroResponse getById(Long id) {

    Optional<PerroEntity> perroOptional = perroRepository.findById(id);

    return perroOptional
            .map(perroEntity -> modelMapper.map(perroEntity, PerroResponse.class))
            .orElseThrow(() -> new ResourceNotFoundException("Perro no encontrado con ID: " + id));
  }

  @Override
  public PerroResponse update(Long id, PerroRequest perroRequest) {

    try {
      Optional<PerroEntity> perroOptional = perroRepository.findById(id);

      if (perroOptional.isPresent()) {
        PerroEntity perroEntity = perroOptional.get();
        modelMapper.map(perroRequest, perroEntity);

        PerroEntity perroUpdated = perroRepository.save(perroEntity);
        return modelMapper.map(perroUpdated, PerroResponse.class);
      } else {
        throw new ResourceNotFoundException("Perro no encontrado con ID: " + id);
      }
    } catch (DataAccessException e) {
      log.error("Hubo un error al actualizar el perro: {}", e.getMessage());
      throw new InternalServerException( Message.UPDATE_ERROR + "el perro con ID: " + id, e);
    }
  }

  @Override
  public void delete(Long id) {

    try {
      Optional<PerroEntity> perroOptional = perroRepository.findById(id);

      if (perroOptional.isPresent()) {
        perroRepository.deleteById(id);
      } else {
        throw new ResourceNotFoundException("Perro no encontrado con ID: " + id);
      }
    } catch (DataAccessException e) {
      log.error("Hubo un error al eliminar el perro: {}", e.getMessage());
      throw new InternalServerException(Message.DELETE_ERROR + "el perro con ID: " + id);
    }
  }
}
