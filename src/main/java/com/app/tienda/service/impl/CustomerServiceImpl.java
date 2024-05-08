package com.app.tienda.service.impl;

import com.app.tienda.entity.AddressEntity;
import com.app.tienda.entity.CustomerEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.repository.AddressRepository;
import com.app.tienda.repository.CustomerRepository;
import com.app.tienda.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<CustomerResponse> findAllCustomer() {
    log.info("CustomerServiceImpl - find all");

    List<CustomerEntity> customers = customerRepository.findAll();

    return customers.stream()
            .map(customerEntity -> modelMapper.map(customerEntity, CustomerResponse.class))
            .collect(Collectors.toList());
  }

  @Transactional
  @Override
  public CustomerResponse save(CustomerRequest customerRequest) {
    log.info("CustomerServiceImpl - save: {}", customerRequest);

    try {
      log.info("Entrando al try");

      CustomerEntity customerEntity = modelMapper.map(customerRequest, CustomerEntity.class);

      // Obtener la dirección del customerEntity
      AddressEntity addressEntity = customerEntity.getAddress();

      // Guardar la dirección primero para obtener su ID generado
      addressRepository.save(addressEntity);

      // Asignar la dirección al cliente y guardarlo
      customerEntity.setAddress(addressEntity);
      CustomerEntity saved = customerRepository.save(customerEntity);

      return modelMapper.map(saved, CustomerResponse.class);
    } catch (Exception e) {
      log.info("Entrando al catch");
      log.error("Hubo un error al crear el cliente : {}", e.getMessage());
      throw new InternalServerException("Hubo un error al crear el cliente");
    }
  }



}
