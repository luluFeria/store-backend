package com.app.tienda.service;

import com.app.tienda.model.request.AlumnoRequest;
import com.app.tienda.model.response.AlumnoResponse;

import java.util.List;

public interface IAlumnoService {
  public List<AlumnoResponse> findAllAlumnos();
  public AlumnoResponse save(AlumnoRequest alumnoRequest);
  public AlumnoResponse getById(Long id);
  public AlumnoResponse updateStudent(Long id, AlumnoRequest alumnoRequest);
  public void delete(Long id);
}
