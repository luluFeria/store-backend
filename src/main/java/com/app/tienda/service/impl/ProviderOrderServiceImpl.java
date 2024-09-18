package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.ProductEntity;
import com.app.tienda.entity.ProviderEntity;
import com.app.tienda.entity.ProviderOrderEntity;
import com.app.tienda.entity.ProviderOrderProduct;
import com.app.tienda.enums.OrderStatus;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.ProviderOrderRequest;
import com.app.tienda.model.response.ISupplierOrderWithDetailsResponse;
import com.app.tienda.model.response.ProviderOrderProductResponse;
import com.app.tienda.model.response.ProviderOrderWithDetailsResponse;
import com.app.tienda.repository.ProductRepository;
import com.app.tienda.repository.ProviderOrderRepository;
import com.app.tienda.repository.ProviderRepository;
import com.app.tienda.service.IInventoryService;
import com.app.tienda.service.IProviderOrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@Service
public class ProviderOrderServiceImpl implements IProviderOrderService {

  @Autowired
  private ProviderOrderRepository providerOrderRepository;

  @Autowired
  private ProviderRepository providerRepository;

  @Autowired
  ProductRepository productRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private IInventoryService inventoryService;

  @Override
  public List<ProviderOrderWithDetailsResponse> findAll() {
    return null;
  }
  @Override
  public List<ProviderOrderWithDetailsResponse> findAllWithDetails() {
    log.info("ProviderOrderServiceImpl - find all");

    List<ISupplierOrderWithDetailsResponse> providerOrders = providerOrderRepository.findAllProviderOrders();

    return this.responseOrder(providerOrders);
  }

  private List<ProviderOrderWithDetailsResponse> responseOrder(List<ISupplierOrderWithDetailsResponse> providerOrders) {
    Map<Long, ProviderOrderWithDetailsResponse> orderMap = new LinkedHashMap<>();

    // Obtener o crear la orden proveedor si no existe en el mapa
    providerOrders.forEach(projection -> {
      Long orderId = projection.getOrderId();

      ProviderOrderWithDetailsResponse orderResponse = orderMap.computeIfAbsent(orderId, id -> {
        ProviderOrderWithDetailsResponse response = new ProviderOrderWithDetailsResponse();
        response.setId(id);
        response.setDate(projection.getDate());
        response.setStatus(projection.getStatus());
        response.setTotalAmount(projection.getTotalAmount());
        response.setProducts(new ArrayList<>());
        return response;
      });

      // Crear el producto de la orden proveedor y agregarlo a la lista de productos
      ProviderOrderProductResponse productResponse = new ProviderOrderProductResponse();
      productResponse.setName(projection.getProduct());
      productResponse.setUnitPrice(projection.getPrice());
      productResponse.setQuantity(projection.getQuantity());

      orderResponse.getProducts().add(productResponse);
    });

    return new ArrayList<>(orderMap.values());
  }

  @Override
  public ProviderOrderWithDetailsResponse findById(Long id) {
    log.info("ProviderOrderServiceImpl - find provider order by id {}", id);

    ProviderOrderEntity orderOptional = providerOrderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

    ProviderOrderWithDetailsResponse orderResponseDTO = new ProviderOrderWithDetailsResponse();
    orderResponseDTO.setId(orderOptional.getId());
    orderResponseDTO.setStatus(orderOptional.getStatus());
    orderResponseDTO.setTotalAmount(orderOptional.getTotalAmount());
    orderResponseDTO.setDate(orderOptional.getDate());

    List<ProviderOrderProductResponse> productsResponse = orderOptional.getProducts().stream()
      .map(product -> {
        ProductEntity productEntity = productRepository.findById(product.getProductId())
          .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        ProviderOrderProductResponse productResponse = new ProviderOrderProductResponse();
        productResponse.setName(productEntity.getName());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setUnitPrice(productEntity.getPrice());

        return productResponse;
      }).collect(Collectors.toList());

    orderResponseDTO.setProducts(productsResponse);

    return orderResponseDTO;
  }

  @Override
  public List<ProviderOrderWithDetailsResponse> getByProviderId(Long providerId) {
    log.info("ProviderOrderServiceImpl - find provider orders by providerId {}", providerId);

    List<ISupplierOrderWithDetailsResponse> providerOrders = providerOrderRepository.getByProviderId(providerId);

    return this.responseOrder(providerOrders);
  }

  @Override
  public List<ProviderOrderWithDetailsResponse> findByStatus(String status) {
    log.info("ProviderOrderServiceImpl - find provider orders by status {}", status);

    List<ISupplierOrderWithDetailsResponse> providerOrders = providerOrderRepository.findByStatus(status);

    return this.responseOrder(providerOrders);
  }

  @Override
  public String updateStatus(Long id, String status) {
    log.info("ProviderOrderServiceImpl - update status: {} - {}", id, status);

    if (!(status.equals(OrderStatus.COMPLETED.name()) || status.equals(OrderStatus.CANCELLED.name()))) {
      throw new IllegalArgumentException("Status is invalid");
    }

    ProviderOrderEntity orderOptional = providerOrderRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("The order was not found."));

    if (!(orderOptional.getStatus().equals(OrderStatus.PENDING.name()))) {
      throw new IllegalArgumentException("You are only allowed to update the status of pending orders");
    }

    try {
      orderOptional.setStatus(status);
      ProviderOrderEntity providerOrderEntity= providerOrderRepository.save(orderOptional);

      //TODO: Se va actualizar el inventario
      //inventoryService.update();

      if(providerOrderEntity.getStatus().equals(OrderStatus.COMPLETED.name())) {
        this.inventoryService.update(providerOrderEntity.getProducts());
      }

      return "Status updated successfully";
    } catch (DataAccessException e) {
      log.error("Error updating the order status: {}", e.getMessage());
      throw new InternalServerException(Message.UPDATE_ERROR + "the order status" + id, e);
    }
  }

  @Override
  public String save(ProviderOrderRequest providerOrderRequest) {
    log.info("ProviderOrderServiceImpl - save: {}", providerOrderRequest);

    ProviderEntity provider = providerRepository.findById(providerOrderRequest.getProviderId())
            .orElseThrow(() -> new ResourceNotFoundException("The provider was not found"));

    //verificar que los productos existan.
    List<ProviderOrderProduct> ordersProducts = providerOrderRequest.getProducts().stream().
            map(product -> {
              ProviderOrderProduct orderProduct = new ProviderOrderProduct();
              orderProduct.setProductId(product.getProductId());
              orderProduct.setQuantity(product.getQuantity());

              return orderProduct;
            }).collect(Collectors.toList());

    BigDecimal totalAmount = ordersProducts.stream()
            .map(productDto -> {
              ProductEntity productEntity = productRepository.findById(productDto.getProductId())
                      .orElseThrow(() -> new ResourceNotFoundException("Product not found")); //TODO: Siempre que se encuentre una excepción se detiene el programa y se dispara la excepción

              return productEntity.getPrice().multiply(BigDecimal.valueOf(productDto.getQuantity()));
            }).reduce(BigDecimal.ZERO, BigDecimal::add);

    try {
      ProviderOrderEntity orderEntity = new ProviderOrderEntity();
      orderEntity.setId(null);
      orderEntity.setStatus(OrderStatus.PENDING.name());
      orderEntity.setDate(LocalDateTime.now());
      orderEntity.setProducts(ordersProducts);
      orderEntity.setTotalAmount(totalAmount);
      orderEntity.setProvider(provider);
      ProviderOrderEntity saved = providerOrderRepository.save(orderEntity);
      log.info("saved: {}", saved);

      return Message.SAVE;

    } catch (Exception e) {
      log.error("Error creating the provider order: {}", e.getMessage());
      throw new InternalServerException(Message.SAVE_ERROR);
    }
  }

  @Override
  public ProviderOrderWithDetailsResponse update(Long id, ProviderOrderRequest providerOrderRequest) {
    log.info("ProviderOrderServiceImpl - update: {} {}", id, providerOrderRequest);

    // Verificar si la orden existe
    ProviderOrderEntity providerOrderEntity = providerOrderRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Provider order not found with ID: " + id));

    // Verificar si la orden existe
    ProviderEntity provider = providerRepository.findById(providerOrderRequest.getProviderId())
            .orElseThrow(() -> new ResourceNotFoundException("The provider was not found"));

    //verificar que los productos existan.
    List<ProviderOrderProduct> ordersProducts = providerOrderRequest.getProducts().stream().
            map(product -> {
              ProviderOrderProduct orderProduct = new ProviderOrderProduct();
              orderProduct.setProductId(product.getProductId());
              orderProduct.setQuantity(product.getQuantity());

              return orderProduct;
            }).collect(Collectors.toList());

    BigDecimal totalAmount = ordersProducts.stream()
            .map(productDto -> {
              ProductEntity productEntity = productRepository.findById(productDto.getProductId())
                      .orElseThrow(() -> new ResourceNotFoundException("Product not found")); //TODO: Siempre que se encuentre una excepción se detiene el programa y se dispara la excepción

              return productEntity.getPrice().multiply(BigDecimal.valueOf(productDto.getQuantity()));
            }).reduce(BigDecimal.ZERO, BigDecimal::add);

    try {
      providerOrderEntity.setProducts(ordersProducts);
      providerOrderEntity.setTotalAmount(totalAmount);
      providerOrderEntity.setProvider(provider);
        ProviderOrderEntity saved = providerOrderRepository.save(providerOrderEntity);
        return modelMapper.map(saved, ProviderOrderWithDetailsResponse.class);
    } catch (DataAccessException e) {
      log.error("Error updating the provider order: {}", e.getMessage());
      throw new InternalServerException(Message.UPDATE_ERROR + "the provider order with ID: " + id, e);
    }
  }

  @Override
  public void delete(Long id) {
    log.info("ProviderOrderServiceImpl - delete: {}", id);

    try {
      Optional<ProviderOrderEntity> providerOrderEntity = providerOrderRepository.findById(id);

      if (providerOrderEntity.isPresent()) {
        providerOrderRepository.deleteById(id);
      } else {
        throw new ResourceNotFoundException("Provider order not found with ID: " + id);
      }
    } catch (DataAccessException e) {
      log.error("Error deleting the provider order: {}", e.getMessage());
      throw new InternalServerException(Message.DELETE_ERROR + "the order: " + id);
    }
  }

}

