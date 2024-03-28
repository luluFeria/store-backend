package com.app.tienda.service;

import com.app.tienda.model.request.PerroRequest;
import com.app.tienda.model.response.PerroResponse;

import java.util.List;

public interface IPerroService {
  public List<PerroResponse> findAllPerros();
  public PerroResponse save(PerroRequest perroRequest);
  public PerroResponse getById(Long id);
}
