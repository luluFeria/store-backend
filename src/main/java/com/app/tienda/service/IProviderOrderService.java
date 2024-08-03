package com.app.tienda.service;

import com.app.tienda.model.request.ProviderOrderRequest;
import com.app.tienda.model.response.ProviderOrderWithDetailsResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProviderOrderService {
  public List<ProviderOrderWithDetailsResponse> findAllWithDetails();
  public List<ProviderOrderWithDetailsResponse> findAll();
  public ProviderOrderWithDetailsResponse findById(Long id);
  public List<ProviderOrderWithDetailsResponse> getByProviderId(Long providerId);
  public List<ProviderOrderWithDetailsResponse> findByStatus(String status);
  public String updateStatus(Long id, String status);
  public String save(ProviderOrderRequest providerOrderRequest);
  public ProviderOrderWithDetailsResponse update(Long id, ProviderOrderRequest providerOrderRequest);
  public void delete(Long id);
}


