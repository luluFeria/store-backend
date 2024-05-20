package com.app.tienda.service.impl;

import com.app.tienda.entity.ProviderEntity;
import com.app.tienda.model.response.ProviderResponse;
import com.app.tienda.repository.ProviderRepository;
import com.app.tienda.service.IProviderService;
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
public class ProviderServiceImpl implements IProviderService {

  @Autowired
  private ProviderRepository providerRepository;

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
}
