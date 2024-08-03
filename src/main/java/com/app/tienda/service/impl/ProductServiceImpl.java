package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.ProductEntity;
import com.app.tienda.entity.ProviderEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.ProductRequest;
import com.app.tienda.model.response.IProductResponse;
import com.app.tienda.model.response.ProductResponse;
import com.app.tienda.repository.ProductRepository;
import com.app.tienda.repository.ProviderRepository;
import com.app.tienda.service.IProductService;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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
      productEntity.setId(null);
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
    log.info("ProductServiceImpl - find product by id {}", id);

    Optional<ProductEntity> productOptional = productRepository.findById(id);

    return productOptional
            .map(productEntity -> modelMapper.map(productEntity, ProductResponse.class))
            .orElseThrow(() -> new ResourceNotFoundException(Message.ID_NOT_FOUND + ": " + id));
  }

  @Override
  public List<IProductResponse> findAllBySupplier(Long supplierId) {
    log.info("ProductServiceImpl - find products by supplierId {}", supplierId);

    return  productRepository.findProductsByProvider(supplierId);
  }

  @Override
  public List<ProductResponse> getByName(String name) {
    log.info("ProductServiceImpl - find product by name {}", name);

    List<ProductEntity> productName = productRepository.findByName(name);

    return productName.stream().
            map(productEntity -> modelMapper.map(productEntity, ProductResponse.class))
            .collect(Collectors.toList());
  }

  @Override
  public List<ProductResponse> getByCategory(String category) {
    log.info("ProductServiceImpl - find product by category {}", category);

    List<ProductEntity> productList = this.productRepository.findByCategory(category);

    return productList.stream()
            .map(productEntity -> modelMapper.map(productEntity, ProductResponse.class))
            .collect(Collectors.toList());
  }

  @Override
  public ProductResponse update(Long id, ProductRequest productRequest) {
    try {
      Optional<ProductEntity> productOptional = productRepository.findById(id);

      if (productOptional.isPresent()) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(id);
        productEntity.setName(productRequest.getName());
        productEntity.setCategory(productRequest.getCategory());
        //productEntity.setPrice(productRequest.getPrice());
        productEntity.setDescription(productRequest.getDescription());
        productEntity.setProvider(productOptional.get().getProvider());
        productEntity.setQuantity(productRequest.getQuantityInInventory());

        ProductEntity productUpdated = productRepository.save(productEntity);
        return modelMapper.map(productUpdated, ProductResponse.class);
      } else {
        throw new ResourceNotFoundException(Message.ID_NOT_FOUND);
      }

      //modelMapperSkipId.map(productRequest, productEntity);
    } catch (DataAccessException e) {
      log.error("Hubo un error al actualizar el producto: {}", e.getMessage());
      throw new InternalServerException(Message.UPDATE_ERROR + "el producto con ID: " + id, e);
    }
  }

  @Override
  public void delete(Long id) {
    log.info("ProductServiceImpl - delete: {}", id);

    try {
      Optional<ProductEntity> productOptional = productRepository.findById(id);

      if (productOptional.isPresent()) {
        productRepository.deleteById(id);
      } else {
        throw new ResourceNotFoundException(Message.ID_NOT_FOUND +  ": " + id);
      }
    } catch (DataAccessException e) {
      log.error("Hubo un error al eliminar el producto: {}", e.getMessage());
      throw new InternalServerException(Message.DELETE_ERROR + "el producto con ID: " + id);
    }
  }
}

