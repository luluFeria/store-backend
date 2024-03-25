package com.app.tienda.service;

import com.app.tienda.model.response.PerroResponse;

import java.util.List;

public interface IPerroService {
  public List<PerroResponse> findAllPerros();
}
