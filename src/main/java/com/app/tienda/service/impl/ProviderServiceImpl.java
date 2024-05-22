package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.AddressEntity;
import com.app.tienda.entity.ProviderEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.ProviderRequest;
import com.app.tienda.model.response.ProviderResponse;
import com.app.tienda.repository.AddressRepository;
import com.app.tienda.repository.ProviderRepository;
import com.app.tienda.service.IProviderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Slf4j
@Service
public class ProviderServiceImpl implements IProviderService {

  @Autowired
  private ProviderRepository providerRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<ProviderResponse> findAll() {
    log.info("ProviderServiceImpl - find all");

    List<ProviderEntity> providers = providerRepository.findAll();

    return providers.stream()
            .map(providerEntity -> modelMapper.map(providerEntity, ProviderResponse.class))
            .collect(Collectors.toList());
  }

  @Override
  public ProviderResponse save(ProviderRequest providerRequest) {
    log.info("ProviderServiceImpl - save: {}", providerRequest);

    try {
      ProviderEntity providerEntity = modelMapper.map(providerRequest, ProviderEntity.class);

      AddressEntity addressEntity = providerEntity.getAddress();

      addressRepository.save(addressEntity);

      providerEntity.setAddress(addressEntity);
      ProviderEntity saved = providerRepository.save(providerEntity);

      return modelMapper.map(saved, ProviderResponse.class);
    } catch (Exception e) {
      log.error("Hubo un error al crear el proveedor : {}", e.getMessage());
      throw new InternalServerException(Message.SAVE_ERROR + "el proveedor");
    }
  }

  @Override
  public ProviderResponse getById(Long id) {
    log.info("ProviderServiceImpl - find provider by id {}", id);

    Optional<ProviderEntity> providerOptional = providerRepository.findById(id);

    return providerOptional
            .map(providerEntity -> modelMapper.map(providerEntity, ProviderResponse.class))
            .orElseThrow(() -> new ResourceNotFoundException(Message.ID_NOT_FOUND + ": " + id));
  }


}
