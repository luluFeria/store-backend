package com.app.tienda.service;

import com.app.tienda.model.response.ProviderResponse;

import java.util.List;

public interface IProviderService {
  public List<ProviderResponse> findAll();
}
