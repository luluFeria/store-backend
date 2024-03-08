package com.app.tienda.service;

import com.app.tienda.model.request.DirectorRequest;
import com.app.tienda.model.response.DirectorResponse;

import java.util.List;

public interface IDirectorService {
  public List<DirectorResponse> findAllDirectors();
  public DirectorResponse save(DirectorRequest directorRequest);
  public DirectorResponse getById(Long id);
}
