package com.app.tienda.service;

import com.app.tienda.model.request.ProviderRequest;
import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.model.response.ProviderResponse;

import java.util.List;

public interface IProviderService {
  public List<ProviderResponse> findAll();
  public ProviderResponse save(ProviderRequest providerRequest);
  public ProviderResponse getById(Long id);
  public List<ProviderResponse> getByCity(String city);
}

