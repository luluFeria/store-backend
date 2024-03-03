package com.app.tienda.service.impl;

import com.app.tienda.entity.AlumnoEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.AlumnoRequest;
import com.app.tienda.model.response.AlumnoResponse;
import com.app.tienda.repository.AlumnoRepository;
import com.app.tienda.service.IAlumnoService;
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
public class AlumnoServiceImpl implements IAlumnoService {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  @Autowired
  private AlumnoRepository alumnoRepository;
  @Autowired
  private ModelMapper modelMapper;
  @Override
  public List<AlumnoResponse> findAllAlumnos() {
    log.info("AlumnoServiceImpl - find all students");

    List<AlumnoEntity> students = alumnoRepository.findAll();

    return students.stream()
            .map(alumnoEntity -> modelMapper.map(alumnoEntity, AlumnoResponse.class))
            .collect(Collectors.toList());
  }

  @Override
  public AlumnoResponse save(AlumnoRequest alumnoRequest) {
    log.info("AlumnoServiceImpl - Accessing the save function");

    try {
      log.info("Estoy entrando al try");
      AlumnoEntity alumnoEntity = modelMapper.map(alumnoRequest, AlumnoEntity.class);
      AlumnoEntity savedAlumno = alumnoRepository.save(alumnoEntity);
      return modelMapper.map(savedAlumno, AlumnoResponse.class);
    } catch (Exception e) {
      log.info("Entrando al catch");
      log.error("Hubo un error al crear el alumno: {}", e.getMessage());
      throw new InternalServerException("Hubo un error al crear el alumno");
    }
  }

  @Override
  public AlumnoResponse getById(Long id) {
    log.info("AlumnoServiceImpl - Accessing the getById function ");

    Optional<AlumnoEntity> alumnoOptional = alumnoRepository.findById(id);

    return alumnoOptional
            .map(alumnoEntity -> modelMapper.map(alumnoEntity, AlumnoResponse.class))
            .orElseThrow(() -> new ResourceNotFoundException("Alumno no encontrado con ID: " + id));
  }

  @Override
  public AlumnoResponse updateStudent(Long id, AlumnoRequest alumnoRequest) {
    log.info("AlumnoServiceImpl - Accessing the updateStudent function");

    try {
      log.info("Entrando en el try");

      Optional<AlumnoEntity> alumnoOptional = alumnoRepository.findById(id);

      if (alumnoOptional.isPresent()) {
        AlumnoEntity alumnoEntity = alumnoOptional.get();
        modelMapper.map(alumnoRequest, alumnoEntity);

        AlumnoEntity alumnoUpdated = alumnoRepository.save(alumnoEntity);
        return modelMapper.map(alumnoUpdated, AlumnoResponse.class);
      } else {
        throw new ResourceNotFoundException("Alumno no encontrado con ID: " + id);
      }
    } catch (DataAccessException e) {
      log.info("Entrando en el catch");
      log.error("Hubo un error al actualizar el alumno: {}", e.getMessage());
      throw new InternalServerException("Error al actualizar el alumno con ID: " + id, e);
    }
  }

  @Override
  public void delete(Long id) {
    log.info("AlumnoServiceImpl - Accessing the delete function");

    try {
      log.info("Entrando al try");
      Optional<AlumnoEntity> alumnoOptional = alumnoRepository.findById(id);

      if (alumnoOptional.isPresent()) {
        alumnoRepository.deleteById(id);
      } else {
        throw new ResourceNotFoundException("Alumno no encontrado con ID: " + id);
      }
    } catch (DataAccessException e) {
      log.info("Entrando al catch");
      log.error("Hubo un error al eliminar el alumno: {}", e.getMessage());
      throw new InternalServerException("Error al eliminar el alumno con ID: " + id);
    }
  }

}
