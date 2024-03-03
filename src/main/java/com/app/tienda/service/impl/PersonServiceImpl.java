package com.app.tienda.service.impl;

import com.app.tienda.entity.PersonEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.PersonRequest;
import com.app.tienda.model.response.PersonResponse;
import com.app.tienda.repository.PersonRepository;
import com.app.tienda.service.IPersonService;
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
public class PersonServiceImpl implements IPersonService {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private PersonRepository personRepository;
  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<PersonResponse> findAllPerson() {
    log.info("PersonServiceImpl - find all users");

    List<PersonEntity> persons = personRepository.findAll();

    //Modelmaper me sirve para mapear(mapeo es transformacion) un objeto a otro objeto.

    return persons.stream()
            .map(personEntity -> modelMapper.map(personEntity, PersonResponse.class))
            .collect(Collectors.toList());
  }

  @Override
  public PersonResponse save(PersonRequest personRequest) {
    try {
      //Mapeo de ub objeto personRequest a un objeto personEntity.
      PersonEntity personEntity = modelMapper.map(personRequest, PersonEntity.class);
      PersonEntity savedPerson = personRepository.save(personEntity);
      return modelMapper.map(savedPerson, PersonResponse.class);
    } catch (Exception e) {
      log.error("Hubo un error al crear la persona: {}", e.getMessage());
      throw new InternalServerException("Hubo un error al crear la persona");
    }
  }

  @Override
  public PersonResponse getById(Long id) {
    Optional<PersonEntity> personOptional = personRepository.findById(id);

    return personOptional
            .map(personEntity -> modelMapper.map(personEntity, PersonResponse.class))
            .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));
  }

  @Override
  public PersonResponse update(Long id, PersonRequest personRequest) {
    try {
      Optional<PersonEntity> personOptional = personRepository.findById(id);

      if (personOptional.isPresent()) {
        PersonEntity personEntity = personOptional.get();
        modelMapper.map(personRequest, personEntity);

        PersonEntity updatedPerson = personRepository.save(personEntity);
        return modelMapper.map(updatedPerson, PersonResponse.class);
      } else {
        throw new ResourceNotFoundException("Persona no encontrada con ID: " + id);
      }
    } catch (DataAccessException e) {
      log.error("Hubo un error al actualizar la persona: {}", e.getMessage());
      throw new InternalServerException("Error al actualizar la persona con ID: " + id, e);
    }
  }

  @Override
  public void delete(Long id) {
    try {
      Optional<PersonEntity> personOptional = personRepository.findById(id);

      if (personOptional.isPresent()) {
        personRepository.deleteById(id);
      } else {
        throw new ResourceNotFoundException("Persona no encontrada con ID: " + id);
      }
    } catch (DataAccessException e) {
      log.error("Hubo un error al eliminar la persona: {}", e.getMessage());
      throw new InternalServerException("Error al eliminar la persona con ID: " + id);
    }
  }
}
