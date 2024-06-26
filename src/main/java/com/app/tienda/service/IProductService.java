package com.app.tienda.service;

import com.app.tienda.model.request.ProductRequest;
import com.app.tienda.model.response.IProductResponse;
import com.app.tienda.model.response.ProductResponse;
import com.app.tienda.model.response.ProviderResponse;

import java.util.List;

public interface IProductService {
  public List<ProductResponse> findAll();
  public ProductResponse save(ProductRequest productRequest);
  public ProductResponse getById(Long id);
  public List<IProductResponse> findAllBySupplier(Long supplierId);
  public List<ProductResponse> getByName(String name);
  public ProductResponse update(Long id, ProductRequest productRequest);
  public List<ProductResponse> getByCategory(String name);
  public void delete(Long id);
}

