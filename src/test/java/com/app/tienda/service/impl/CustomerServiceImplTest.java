package com.app.tienda.service.impl;

import com.app.tienda.entity.AddressEntity;
import com.app.tienda.entity.CustomerEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.AddressRequest;
import com.app.tienda.model.request.CustomerRequest;
import com.app.tienda.model.response.CustomerResponse;
import com.app.tienda.repository.AddressRepository;
import com.app.tienda.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {
  @Mock
  private CustomerRepository customerRepository;
  @Mock
  private AddressRepository addressRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private CustomerServiceImpl customerService;

  @Test
  @DisplayName("Retrieves all customers and returns a list of customers")
  void findAllCustomer() {
    List<CustomerEntity> customerEntityList = new ArrayList<>();
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(1L);
    customerEntity.setName("Vanessa");
    customerEntityList.add(customerEntity);

    when(this.customerRepository.findAll()).thenReturn(customerEntityList);

    List<CustomerResponse> customers = this.customerService.findAllCustomer();

    assertEquals(1, customers.size());
  }

  @Test
  void save() {
    CustomerRequest customerRequest = new CustomerRequest();
    customerRequest.setName("Santiago Pérez");
    customerRequest.setPhone("5512345678");
    customerRequest.setEmail("santiago.perez@gmail.com");
    customerRequest.setGender("M");
    AddressRequest addressRequest = new AddressRequest();
    addressRequest.setStreet("Avenida de la Libertad");
    addressRequest.setCity("Guadalajara");
    addressRequest.setColony("Colonia Americana");
    addressRequest.setMunicipality("Guadalajara");
    customerRequest.setAddress(addressRequest);

    CustomerEntity customerEntity = new CustomerEntity();
    AddressEntity addressEntity = new AddressEntity();

    when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);
    when(customerRepository.save(any(CustomerEntity.class))).thenReturn(customerEntity);

    when(modelMapper.map(any(CustomerEntity.class), eq(CustomerResponse.class))).thenReturn(new CustomerResponse());

    CustomerResponse response = this.customerService.save(customerRequest);

    log.info("Customer: {}", response);
    assertNotNull(response);
  }

  @Test
  @DisplayName("Throws the exception InternalServerException when an error occurs while creating the customer")
  void saveError() {
    CustomerRequest customerRequest = new CustomerRequest();
    customerRequest.setName("Customer");
    customerRequest.setPhone("1234567890");
    customerRequest.setEmail("customer@example.com");
    customerRequest.setGender("F");

    AddressRequest addressRequest = new AddressRequest();
    addressRequest.setStreet("Street 1");
    addressRequest.setCity("City 1");
    addressRequest.setColony("Colony 1");
    addressRequest.setMunicipality("Municipality 1");
    customerRequest.setAddress(addressRequest);

    when(addressRepository.save(any(AddressEntity.class))).thenThrow(new RuntimeException("Error saving address"));

    assertThrows(InternalServerException.class, () -> {
      customerService.save(customerRequest);
    });
  }

  @Test
  @DisplayName("Throws ResourceNotFoundException when the customer is not found")
  void getById() {
    //Configura el mock para que devuelva un Optional con valor nulo
    when(customerRepository.findCustomerById(1L)).thenReturn(Optional.empty());

    //Verifica que se lance una excepción de tipo ResourceNotFoundException
    ResourceNotFoundException thrownException = assertThrows(ResourceNotFoundException.class, () -> {
      this.customerService.getById(1L);
    });

    //Verifica que el mensaje de la excepción sea el esperado
    assertEquals("Cliente no encontrado con ID: 1", thrownException.getMessage());
  }

  @Test
  @DisplayName("Successfully returns CustomerResponse when customer is found by ID")
  void getByIdSuccess() {
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(1L);
    customerEntity.setName("John Doe");

    // Configura otros campos si es necesario
    CustomerResponse customerResponse = new CustomerResponse();
    customerResponse.setId(1L);
    customerResponse.setName("John Doe");

    // Configurar el comportamiento simulado de customerRepository.findCustomerById
    when(customerRepository.findCustomerById(2L)).thenReturn(Optional.of(customerEntity));
    when(modelMapper.map(customerEntity, CustomerResponse.class)).thenReturn(customerResponse);

    // Llamar al método que estamos probando
    CustomerResponse response = this.customerService.getById(2L);

    // Verificar el resultado
    assertNotNull(response);
    assertEquals(1L, response.getId());
    assertEquals("John Doe", response.getName());
  }

  @Test
  @DisplayName("Returns a list of customers from the specified city")
  void getByCity() {
    List<CustomerEntity> customers = new ArrayList<>();
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(3L);
    customerEntity.setName("Jane");
    customers.add(customerEntity);

    // Configurar el mock para que devuelva la lista de clientes cuando se busque por ciudad
    when(customerRepository.findByAddressCity("Oaxaca")).thenReturn(customers);

    // Llamar al método getByCity del servicio para obtener los clientes de la ciudad "Oaxaca"
    List<CustomerResponse> customerResponses = this.customerService.getByCity("Oaxaca");

    // Verificar que la lista de respuestas de clientes no sea nula
    assertNotNull(customerResponses);

    // Verificar que el tamaño de la lista es el esperado
    assertEquals(1, customerResponses.size());
  }

  @Test
  @DisplayName("Successfully returns a customer when found by name")
  void getByNameSuccess() {
    // Configura la entidad de cliente y la respuesta esperada
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(2L);
    customerEntity.setName("Laura");

    // Configura la respuesta esperada
    CustomerResponse customer = new CustomerResponse();
    customer.setId(2L);
    customer.setName("Laura");

    // Configura el mock para que devuelva el cliente cuando se busque por nombre
    when(customerRepository.findCustomerByName("Laura")).thenReturn(Optional.of(customerEntity));
    when(modelMapper.map(customerEntity, CustomerResponse.class)).thenReturn(customer);

    // Llama al método del servicio y verifica que se devuelve el cliente esperado
    CustomerResponse customerResponse = this.customerService.getByName("Laura");

    // Verifica que el cliente es correcto y es el esperado
    assertNotNull(customerResponse);
    assertEquals(2L, customerResponse.getId());
    assertEquals("Laura", customerResponse.getName());
  }

  @Test
  @DisplayName("Throws ResourceNotFoundException when a customer is not found by name")
  void getByNameNotFoundException() {
    // Configura el mock del repositorio para devolver un Optional vacío cuando se busque el cliente por nombre
    when(customerRepository.findCustomerByName("Laura")).thenReturn(Optional.empty());

    // Llama al método del servicio y verifica que se lanza una ResourceNotFoundException
    ResourceNotFoundException thrownException = assertThrows(ResourceNotFoundException.class, () -> {
      customerService.getByName("Laura");
    });

    // Verifica que el mensaje de la excepción es el esperado
    assertEquals("Cliente no encontrado con nombre: Laura", thrownException.getMessage());
  }

  @Test
  @DisplayName("Returns a CustomerResponse when a customer is found by email")
  void getByEmailSuccess() {
    // Configura la entidad del cliente
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(4L);
    customerEntity.setName("David");
    customerEntity.setEmail("david@gmail.com");

    // Configura la respuesta esperada del mapper
    CustomerResponse customer = new CustomerResponse();
    customer.setId(4L);
    customer.setName("David");
    customer.setEmail("david@gmail.com");

    // Configura el mock del repositorio para devolver la entidad de cliente
    when(customerRepository.findCustomerByEmail("david@gmail.com")).thenReturn(Optional.of(customerEntity));

    // Configura el mock del modelMapper para devolver la respuesta esperada
    when(modelMapper.map(customerEntity, CustomerResponse.class)).thenReturn(customer);

    // Llama al método del servicio
    CustomerResponse customerResponse = this.customerService.getByEmail("david@gmail.com");

    // Verifica que el cliente es correcto y es el esperado
    assertNotNull(customerResponse);
    assertEquals(4L, customerResponse.getId());
    assertEquals("David", customerResponse.getName());
    assertEquals("david@gmail.com", customerResponse.getEmail());
  }

  @Test
  @DisplayName("Throws ResourceNotFoundException when no customer is found by email")
  void getByEmailNotFoundException() {
    // Configura el mock del repositorio para devolver un Optional vacío cuando se busque el cliente por email
    when(customerRepository.findCustomerByEmail("luis@gmail.com")).thenReturn(Optional.empty());

    // Llama al método del servicio y verifica que se lanza una ResourceNotFoundException
    ResourceNotFoundException thrownException = assertThrows(ResourceNotFoundException.class, () -> {
      customerService.getByEmail("luis@gmail.com");
    });

    // Verifica que el mensaje de la excepción es el esperado
    assertEquals("Cliente no encontrado con email: luis@gmail.com", thrownException.getMessage());
  }

  @Test
  @DisplayName("Successfully update customer details by ID")
  void update() {
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(2L);
    customerEntity.setName("Jane");
    customerEntity.setPhone("9876543210");
    customerEntity.setEmail("jane@gmail.com");

    CustomerRequest customerRequest = new CustomerRequest();
    customerRequest.setName("Jane");
    customerRequest.setPhone("5432101525");
    customerRequest.setEmail("jane@gmail.com");

    when(customerRepository.findById(4L)).thenReturn(Optional.of(customerEntity));

    CustomerResponse response = customerService.update(4L, customerRequest);

    assertNull(response);
  }

  @Test
  @DisplayName("Throws ResourceNotFoundException when trying to update a non existent customer")
  void updateCustomerNotFound() {
    CustomerRequest customerRequest = new CustomerRequest();
    customerRequest.setName("Jane");
    customerRequest.setPhone("5432101525");
    customerRequest.setEmail("jane@gmail.com");

    // Simula que no se encuentra el cliente en el repositorio devolviendo un Optional vacío
    when(customerRepository.findById(4L)).thenReturn(Optional.empty());

    // Llamada al método a probar y verifica que se lance la excepción esperada
    assertThrows(ResourceNotFoundException.class, () -> {
      customerService.update(4L, customerRequest);
    });

    // Verifica que el repositorio fue llamado una vez para buscar el cliente
    verify(customerRepository, times(1)).findById(4L);
  }

  @Test
  @DisplayName("Throws InternalServerException when a DataAccessException occurs during customer update")
  void updateCustomerThrowsDataAccessException() {
    // Configura la entidad del cliente existente
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(2L);
    customerEntity.setName("Jane");
    customerEntity.setPhone("9876543210");
    customerEntity.setEmail("jane@gmail.com");

    // Configura la solicitud del cliente
    CustomerRequest customerRequest = new CustomerRequest();
    customerRequest.setName("Jane");
    customerRequest.setPhone("5432101525");
    customerRequest.setEmail("jane@gmail.com");

    // Simula la búsqueda exitosa del cliente
    when(customerRepository.findById(2L)).thenReturn(Optional.of(customerEntity));

    // Simula una excepción al intentar guardar el cliente para que entre en el bloque catch
    when(customerRepository.save(any(CustomerEntity.class))).thenThrow(new DataAccessException("Simulated exception") {});

    // Ejecuta el método y verifica que se lanza la excepción esperada
    assertThrows(InternalServerException.class, () -> customerService.update(2L, customerRequest));

    // Verifica que el método de búsqueda se llamó una vez y que se intentó guardar antes de fallar
    verify(customerRepository, times(1)).findById(2L);
    verify(customerRepository, times(1)).save(any(CustomerEntity.class));
  }

  @Test
  @DisplayName("Successfully delete customer by ID")
  void delete() {
    // Crear una instancia de CustomerEntity para simular un cliente existente
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(2L);
    customerEntity.setName("Eduardo");

    // Configura el mock para que devuelva el cliente cuando se busque por ID
    when(customerRepository.findById(5L)).thenReturn(Optional.of(customerEntity));

    // Llama al método delete del servicio para eliminar el cliente con ID 5
    customerService.delete(5L);

    // Verifica que el método deleteById del repositorio ejecutó la eliminación del cliente con ID 5 exactamente una vez
    verify(customerRepository, times(1)).deleteById(5L);
  }

  @Test
  @DisplayName("Throws ResourceNotFoundException when trying to delete a non existent customer")
  void deleteCustomerNotFound() {
    // Configura el mock para que devuelva un Optional vacío cuando se busque por ID
    when(customerRepository.findById(5L)).thenReturn(Optional.empty());

    // Llama al método delete del servicio para eliminar el cliente con ID 5 y verifica que se lanza una ResourceNotFoundException
    ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
      customerService.delete(5L);
    });

    // Verifica que el mensaje de la excepción es el esperado
    assertEquals("No se encontró el cliente con ID: 5", thrown.getMessage());

    // Verifica que el método deleteById del repositorio no se ejecutó al intentar eliminar un cliente inexistente
    verify(customerRepository, never()).deleteById(5L);
  }

  @Test
  @DisplayName("Throws InternalServerException when a DataAccessException occurs while deleting a customer")
  void deleteCustomerError() {
    // Crea una instancia de CustomerEntity para simular un cliente
    CustomerEntity customerEntity = new CustomerEntity();
    customerEntity.setId(5L);
    customerEntity.setName("Eduardo");

    // Configura el mock para que devuelva el cliente simulado cuando se busque por ID
    when(customerRepository.findById(5L)).thenReturn(Optional.of(customerEntity));

    // Configura el mock para que lance una DataAccessException cuando se intente eliminar el cliente por ID
    doThrow(new DataAccessException("Simulated exception") {}).when(customerRepository).deleteById(5L);

    // Verifica que el método delete del servicio lanza una InternalServerException
    InternalServerException thrown = assertThrows(InternalServerException.class, () -> {
      customerService.delete(5L);
    });

    // Verifica que el mensaje de la excepción es el esperado
    assertEquals("Error al eliminar el cliente con ID: 5", thrown.getMessage());
  }
}

