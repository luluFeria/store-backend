package com.app.tienda.service.impl;

import com.app.tienda.entity.CustomerEntity;
import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.repository.CustomerRepository;
import com.app.tienda.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomerServiceImpl implements ICustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public List<CustomerResponse> findAllCustomer() {
    log.info("CustomerServiceImpl - find all customers");

    List<CustomerEntity> customers = customerRepository.findAll();

    return customers.stream()
            .map(customerEntity -> modelMapper.map(customerEntity, CustomerResponse.class))
            .collect(Collectors.toList());
  }
}
