package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.AddressEntity;
import com.app.tienda.entity.ProductEntity;
import com.app.tienda.entity.ProviderEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.ProductRequest;
import com.app.tienda.model.response.ProductResponse;
import com.app.tienda.model.response.ProviderResponse;
import com.app.tienda.repository.ProductRepository;
import com.app.tienda.repository.ProviderRepository;
import com.app.tienda.service.IProductService;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private ProviderRepository providerRepository;

  @Autowired
  private ModelMapper modelMapper;
  @Override
  public List<ProductResponse> findAll() {
    log.info("ProductServiceImpl - find all");

    List<ProductEntity> products = productRepository.findAll();

    return products.stream()
            .map(productEntity -> modelMapper.map(productEntity, ProductResponse.class))
            .collect(Collectors.toList());
  }

  @Override
  public ProductResponse save(ProductRequest productRequest) {
    log.info("ProductServiceImpl - save: {}", productRequest);

    ProviderEntity provider = providerRepository.findById(productRequest.getIdProvider())
            .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));

    try {
      ProductEntity productEntity = modelMapper.map(productRequest, ProductEntity.class);

      productEntity.setProvider(provider);
      ProductEntity saved = productRepository.save(productEntity);

      return modelMapper.map(saved, ProductResponse.class);
    } catch (Exception e) {
      log.error("Hubo un error al crear el proveedor : {}", e.getMessage());
      throw new InternalServerException(Message.SAVE_ERROR + "el proveedor");
    }
  }

  @Override
  public ProductResponse getById(Long id) {
    return null;
  }

  @Override
  public ProductResponse update(Long id, ProductRequest productRequest) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }
}
