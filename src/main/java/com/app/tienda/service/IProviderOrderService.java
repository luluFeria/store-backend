package com.app.tienda.service;

import com.app.tienda.model.request.ProviderOrderRequest;
import com.app.tienda.model.request.ProviderRequest;
import com.app.tienda.model.response.ProviderOrderResponse;
import com.app.tienda.model.response.ProviderResponse;

import java.util.List;

public interface IProviderOrderService {
  public List<ProviderOrderResponse> findAll();
  public ProviderOrderResponse getById(Long id);
  public ProviderOrderResponse save(ProviderOrderRequest providerOrderRequest);
  public ProviderOrderResponse update(Long id, ProviderOrderRequest providerOrderRequest);
  public void delete(Long id);
}
