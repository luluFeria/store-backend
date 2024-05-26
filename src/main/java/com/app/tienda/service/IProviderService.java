package com.app.tienda.service;

import com.app.tienda.model.request.ProviderRequest;
import com.app.tienda.model.response.ProviderResponse;

import java.util.List;

public interface IProviderService {
  public List<ProviderResponse> findAll();
  public ProviderResponse save(ProviderRequest providerRequest);
  public ProviderResponse getById(Long id);
  public List<ProviderResponse> getByCity(String city);
  public List<ProviderResponse> getByName(String name);
  public ProviderResponse getByEmail(String email);
  public ProviderResponse update(Long id, ProviderRequest providerRequest);
  public void delete(Long id);
}

