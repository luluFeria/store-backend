package com.app.tienda.service.impl;

import com.app.tienda.entity.AddressEntity;
import com.app.tienda.entity.CustomerEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.repository.AddressRepository;
import com.app.tienda.repository.CustomerRepository;
import com.app.tienda.service.ICustomerService;
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
      log.error("Hubo un error al crear el cliente : {}", e.getMessage());
      throw new InternalServerException("Hubo un error al crear el cliente");
    }
  }

  @Override
  public CustomerResponse getById(Long id) {
    log.info("CustomerServiceImpl - find customer by id {}", id);

    Optional<CustomerEntity> customerOptional = customerRepository.findCustomerById(id);

    return customerOptional
            .map(customerEntity -> modelMapper.map(customerEntity, CustomerResponse.class))
            .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + id));
  }

  @Override
  public List<CustomerResponse> getByCity(String city) {
    log.info("CustomerServiceImpl - find customer by city {}", city);

    List<CustomerEntity> customerList = this.customerRepository.findByAddressCity(city);

    return customerList.stream()
            .map(customerEntity -> modelMapper.map(customerEntity, CustomerResponse.class))
            .collect(Collectors.toList());
  }

  @Override
  public CustomerResponse getByName(String name) {
    log.info("CustomerServiceImpl - find customer by name {}", name);

    Optional<CustomerEntity> customerOptional = customerRepository.findCustomerByName(name);

    return customerOptional
            .map(customerEntity -> modelMapper.map(customerEntity, CustomerResponse.class))
            .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con nombre: " + name));
  }

  @Override
  public CustomerResponse getByEmail(String email) {
    log.info("CustomerServiceImpl - find customer by email {}", email);

    Optional<CustomerEntity> customerOptional = customerRepository.findCustomerByEmail(email);

    return customerOptional
            .map(customerEntity -> modelMapper.map(customerEntity, CustomerResponse.class))
            .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con email: " + email));
  }

  @Override
  public CustomerResponse update(Long id, CustomerRequest customerRequest) {
    log.info("CustomerServiceImpl - update: {} {}", id, customerRequest);

    try {
      Optional<CustomerEntity> customerOptional = customerRepository.findById(id);

      if (customerOptional.isPresent()) {
        CustomerEntity customerEntity = customerOptional.get();
        modelMapper.map(customerRequest, customerEntity);

        CustomerEntity customerUpdated = customerRepository.save(customerEntity);
        return modelMapper.map(customerUpdated, CustomerResponse.class);
      } else {
        throw new ResourceNotFoundException("No se encontró el cliente con ID: " + id);
      }
    } catch (DataAccessException e) {
      log.error("Hubo un error al actualizar el cliente: {}", e.getMessage());
      throw new InternalServerException("Error al actualizar el cliente con ID: " + id, e);
    }
  }

  @Override
  public void delete(Long id) {
    log.info("CustomerServiceImpl - delete: {}", id);

    try {
      log.info("Entrando al try");

      Optional<CustomerEntity> customerOptional = customerRepository.findById(id);

      if (customerOptional.isPresent()) {
        customerRepository.deleteById(id);
      } else {
        throw new ResourceNotFoundException("No se encontró el cliente con ID: " + id);
      }
    } catch (DataAccessException e) {
      log.error("Hubo un error al eliminar el cliente: {}", e.getMessage());
      throw new InternalServerException("Error al eliminar el cliente con ID: " + id);
    }
  }
}
