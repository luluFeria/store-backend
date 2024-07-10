package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.ProviderEntity;
import com.app.tienda.entity.ProviderOrderEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.ProviderOrderRequest;
import com.app.tienda.model.response.ProviderOrderResponse;
import com.app.tienda.repository.ProviderOrderRepository;
import com.app.tienda.repository.ProviderRepository;
import com.app.tienda.service.IProviderOrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Id;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
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
  private ModelMapper modelMapper;

  @Override
  public List<ProviderOrderResponse> findAll() {
    log.info("ProviderOrderServiceImpl - find all");

    List<ProviderOrderEntity> providerOrders = providerOrderRepository.findAll();

    return providerOrders.stream()
            .map(providerOrderEntity -> modelMapper.map(providerOrderEntity, ProviderOrderResponse.class))
            .collect(Collectors.toList());
  }

  @Override
  public ProviderOrderResponse getById(Long id) {
    log.info("ProviderOrderServiceImpl - find provider order by id");

    Optional<ProviderOrderEntity> providerOrderOptional = providerOrderRepository.findById(id);

    return providerOrderOptional
            .map(providerOrderEntity -> modelMapper.map(providerOrderEntity, ProviderOrderResponse.class))
            .orElseThrow(() -> new ResourceNotFoundException(Message.ID_NOT_FOUND + ": " + id));
  }

  @Override
  public ProviderOrderResponse save(ProviderOrderRequest providerOrderRequest) {
    log.info("ProviderOrderServiceImpl - save: {}", providerOrderRequest);

    ProviderEntity provider = providerRepository.findById(providerOrderRequest.getProviderId())
            .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado"));

    try {
      ProviderOrderEntity providerOrderEntity = modelMapper.map(providerOrderRequest, ProviderOrderEntity.class);
      providerOrderEntity.setId(null);
      providerOrderEntity.setProvider(provider);
      ProviderOrderEntity savedEntity = providerOrderRepository.save(providerOrderEntity);

      return modelMapper.map(savedEntity, ProviderOrderResponse.class);
    } catch (Exception e) {
      log.error("Error al crear la orden del proveedor: {}", e.getMessage());
      throw new InternalServerException(Message.SAVE_ERROR + "la orden del proveed");
    }
  }

  @Override
  public ProviderOrderResponse update(Long id, ProviderOrderRequest providerOrderRequest) {
    return null;
  }

  @Override
  public void delete(Long id) {

  }


}

