package com.app.tienda.service.impl;

import com.app.tienda.constant.Message;
import com.app.tienda.entity.AddressEntity;
import com.app.tienda.entity.ProviderEntity;
import com.app.tienda.exception.InternalServerException;
import com.app.tienda.exception.ResourceNotFoundException;
import com.app.tienda.model.request.AddressRequest;
import com.app.tienda.model.request.ProviderRequest;
import com.app.tienda.model.response.ProviderResponse;
import com.app.tienda.repository.AddressRepository;
import com.app.tienda.repository.ProviderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ProviderServiceImplTest {
  @Mock
  private ProviderRepository providerRepository;

  @Mock
  private AddressRepository addressRepository;

  @Mock
  private ModelMapper modelMapper;

  @InjectMocks
  private ProviderServiceImpl providerService;

  @Test
  @DisplayName("Retrieves all providers and returns a list of providers")
  void findAll() {
    // Crea un mock de una lista de proveedores con un proveedor ficticio
    List<ProviderEntity> providersList = new ArrayList<>();
    ProviderEntity providerEntity = new ProviderEntity();
    providerEntity.setId(2L);
    providerEntity.setName("Luz");
    providerEntity.setEmail("luz@gmail.com");
    providersList.add(providerEntity);

    // Configura el mock del repositorio para devolver la lista de proveedores ficticios
    when(this.providerRepository.findAll()).thenReturn(providersList);

    // Configura el mock del modelMapper para mapear los objetos de tipo ProviderEntity a ProviderResponse
    when(modelMapper.map(any(ProviderEntity.class), eq(ProviderResponse.class)))
      .thenReturn(new ProviderResponse());

    // Llama al método del servicio para obtener la lista de proveedores
    List<ProviderResponse> providers = this.providerService.findAll();

    // Verifica que la lista de proveedores contiene exactamente un elemento
    assertEquals(1, providers.size());
  }

  @Test
  @DisplayName("Save a provider and return a ProviderResponse with the correct data")
  void save() {
    // Configura la solicitud del proveedor
    ProviderRequest providerRequest = new ProviderRequest();
    providerRequest.setName("Daniel");
    providerRequest.setPhone("56274067");
    providerRequest.setEmail("daniel@gmail.com");

    // Configura la dirección del proveedor
    AddressRequest addressRequest = new AddressRequest();
    addressRequest.setStreet("Morelos");
    addressRequest.setCity("Tlaxiaco");
    addressRequest.setColony("San Diego");
    addressRequest.setMunicipality("Tlaxiaco");
    providerRequest.setAddress(addressRequest);

    // Crea las entidades necesarias para simular las operaciones
    ProviderEntity providerEntity = new ProviderEntity();
    AddressEntity addressEntity = new AddressEntity();

    // Simula el mock de guardar en el repositorio de dirección
    when(addressRepository.save(any(AddressEntity.class))).thenReturn(addressEntity);

    // Simula el mock de guardar en el repositorio de proveedores
    when(providerRepository.save(any(ProviderEntity.class))).thenReturn(providerEntity);

    // Simula el mock del modelMapper para convertir la entidad en respuesta
    when(modelMapper.map(any(ProviderEntity.class), eq(ProviderResponse.class))).thenReturn(new ProviderResponse());

    // Llama al método save del servicio para guardar la entidad de proveedor
    ProviderResponse response = this.providerService.save(providerRequest);

    // Verifica que la respuesta no sea nula
    log.info("Provider: {}", response);
    assertNotNull(response);
  }

  @Test
  @DisplayName("Throws the exception InternalServerException when an error occurs while creating the provider")
  void saveError() {
    // Simulación de los datos de un nuevo proveedor
    ProviderRequest providerRequest = new ProviderRequest();
    providerRequest.setName("Daniel");
    providerRequest.setPhone("56274067");
    providerRequest.setEmail("daniel@gmail.com");

    // Configura la dirección del proveedor
    AddressRequest addressRequest = new AddressRequest();
    addressRequest.setStreet("Morelos");
    addressRequest.setCity("Tlaxiaco");
    addressRequest.setColony("San Diego");
    addressRequest.setMunicipality("Tlaxiaco");
    providerRequest.setAddress(addressRequest);

    // Simula el mock para lanzar una excepción al intentar guardar la dirección
    when(addressRepository.save(any(AddressEntity.class))).thenThrow(new RuntimeException("Error saving address"));

    // Verifica que se lance una InternalServerException al fallar el guardado de la dirección
    assertThrows(InternalServerException.class, () -> {
      providerService.save(providerRequest);
    });
  }

  @Test
  void getById() {
    // Crear una entidad ficticia de proveedor
    ProviderEntity providerEntity = new ProviderEntity();
    providerEntity.setId(4L);
    providerEntity.setName("Carlos");
    providerEntity.setEmail("carlos@gmail.com");

    // Crear una respuesta ficticia de proveedor
    ProviderResponse providerResponse = new ProviderResponse();
    providerResponse.setId(4L);
    providerResponse.setName("Carlos");
    providerResponse.setEmail("carlos@gmail.com");

    // Configura el mock del repositorio para devolver la entidad ficticia cuando se busca por ID
    when(this.providerRepository.findById(4L)).thenReturn(Optional.of(providerEntity));

    // Configura el mock del modelMapper para mapear la entidad a la respuesta de proveedor
    when(modelMapper.map(providerEntity, ProviderResponse.class)).thenReturn(providerResponse);

    // Llama al método getById del servicio para obtener la respuesta del proveedor
    ProviderResponse response = this.providerService.getById(4L);

    // Verificar que la respuesta no sea nula y que los datos sean los esperados
    assertNotNull(response);
    assertEquals(4L, response.getId());
    assertEquals("Carlos", response.getName());
    assertEquals("carlos@gmail.com", response.getEmail());
  }

  @Test
  @DisplayName("Este Test devuelve una excepción si no encuentra un customer")
  void getByIdNotFound() {
    assertThrows(ResourceNotFoundException.class, () -> {
      providerService.getById(1L);
    });
  }

  @Test
  @DisplayName("Returns a list of providers for the specified city")
  void getByCity() {
    List<ProviderEntity> providers = new ArrayList<>();
    ProviderEntity providerEntity = new ProviderEntity();
    providerEntity.setId(5L);
    providerEntity.setName("Melisa");
    providers.add(providerEntity);

    // Configura el mock para que devuelva la lista de proveedores cuando se busque por ciudad
    when(providerRepository.findByAddressCity("Monterrey")).thenReturn(providers);

    // Llama al método getByCity del servicio para obtener los proveedores de la ciudad "Monterrey"
    List<ProviderResponse> providerResponses = providerService.getByCity("Monterrey");

    // Verifica que la lista de respuestas de proveedores no sea nula
    assertNotNull(providerResponses);

    // Verificar que el tamaño de la lista es el esperado
    assertEquals(1, providerResponses.size());
  }

  @Test
  @DisplayName("Successfully returns ProviderResponse list when provider is found by name")
  void getByName() {
    // Configura la entidad de proveedor y la respuesta esperada
    ProviderEntity providerEntity = new ProviderEntity();
    providerEntity.setId(3L);
    providerEntity.setName("Daniel");

    // Configura la respuesta esperada
    ProviderResponse providerResponse = new ProviderResponse();
    providerResponse.setId(3L);
    providerResponse.setName("Daniel");

    // Simula el comportamiento de providerRepository y modelMapper
    when(providerRepository.findByName("Daniel")).thenReturn(Collections.singletonList(providerEntity));
    when(modelMapper.map(providerEntity, ProviderResponse.class)).thenReturn(providerResponse);

    // Ejecuta el método de prueba
    List<ProviderResponse> response = providerService.getByName("Daniel");

    // Verifica el resultado
    assertNotNull(response);
    assertEquals(1, response.size());
    assertEquals(providerResponse, response.get(0));
  }

  @Test
  @DisplayName("Returns a ProviderResponse when a provider is found by email")
  void getByEmail() {
    // Configura la entidad del provider
    ProviderEntity providerEntity = new ProviderEntity();
    providerEntity.setId(4L);
    providerEntity.setName("David");
    providerEntity.setEmail("david@gmail.com");

    // Configura la respuesta esperada
    ProviderResponse provider = new ProviderResponse();
    provider.setId(4L);
    provider.setName("David");
    provider.setEmail("david@gmail.com");

    // Simula el mock del repositorio para devolver la entidad de provider
    when(providerRepository.findByEmail("david@gmail.com")).thenReturn(Optional.of(providerEntity));

    // Simula el mock del modelMapper para mapear la entidad a la respuesta de provider
    when(modelMapper.map(providerEntity, ProviderResponse.class)).thenReturn(provider);

    // Llama al método de prueba para buscar un proveedor por email
    ProviderResponse response = providerService.getByEmail("david@gmail.com");

    // Verifica que el provider es correcto y es el esperado
    assertEquals(provider, response);
  }

  @Test
  @DisplayName("Throws ResourceNotFoundException when no provider is found by email")
  void getByEmailNotFoundException() {
    // Configura el mock del repositorio para devolver un Optional vacío cuando se busque el proveedor por email
    when(providerRepository.findByEmail("david@gmail.com")).thenReturn(Optional.empty());

    // Llama al método del servicio y verifica que se lanza una ResourceNotFoundException
    ResourceNotFoundException thrownException = assertThrows(ResourceNotFoundException.class, () -> {
      providerService.getByEmail("david@gmail.com");
    });

    // Verifica que el mensaje de la excepción es el esperado
    assertEquals("Proveedor no encontrado con email: david@gmail.com", thrownException.getMessage());
  }

  @Test
  void update() {
    ProviderEntity providerMock = new ProviderEntity();
    providerMock.setId(12L);
    providerMock.setName("Luz");
    providerMock.setPhone("65436910");
    providerMock.setEmail("luz@gmail.com");

    ProviderRequest providerUpdate = new ProviderRequest();
    providerUpdate.setName("Luz");
    providerUpdate.setPhone("65436910");
    providerUpdate.setEmail("luz@gmail.com");

    ProviderResponse providerResponseMock = new ProviderResponse();
    providerResponseMock.setId(12L);
    providerResponseMock.setName("Luz");
    providerResponseMock.setPhone("65436910");
    providerResponseMock.setEmail("luz@gmail.com");

    // Configurar el comportamiento de los mocks
    when(providerRepository.findById(12L)).thenReturn(Optional.of(providerMock));
    when(modelMapper.map(any(), eq(ProviderResponse.class))).thenReturn(providerResponseMock);

    // Llamar al método que estamos probando
    ProviderResponse providerResponse = this.providerService.update(12L, providerUpdate);
    log.info("ProviderResponse {}", providerResponse);

    // Verificar que la respuesta no sea nula
    assertNotNull(providerResponse);
  }

  @Test
  @DisplayName("An exception is thrown when trying to update a non-existent client.")
  void updateNonExistentProviderRequest() {
    ProviderRequest providerRequest = new ProviderRequest();
    providerRequest.setName("Jane Updated");
    providerRequest.setPhone("5432101525");
    providerRequest.setEmail("jane_updated@gmail.com");

    // Simulamos que no se encuentra el cliente
    when(providerRepository.findById(2L)).thenReturn(Optional.empty());

    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      providerService.update(2L, providerRequest);
    });

    assertEquals(Message.ID_NOT_FOUND, exception.getMessage());
  }

  @Test
  @DisplayName("An exception is thrown when trying to update a non-existent client.")
  void updateError() {
    ProviderRequest providerRequest = new ProviderRequest();
    providerRequest.setName("Stasy");
    providerRequest.setPhone("5432101525");
    providerRequest.setEmail("stasy@gmail.com");

    // Simula que el proveedor existe en la base de datos
    ProviderEntity supplierEntity = new ProviderEntity();
    when(providerRepository.findById(8L)).thenReturn(Optional.of(supplierEntity));

    // Simula que ocurre un DataAccessException al intentar guardar el proveedor actualizado
    when(providerRepository.save(any(ProviderEntity.class))).thenThrow(new DataAccessException("Error accessing database") {});

    // Verifica que se lanza la InternalServerException cuando ocurre un DataAccessException
    InternalServerException exception = assertThrows(InternalServerException.class, () -> {
      this.providerService.update(8L, providerRequest);
    });

    log.info("exception: {}", exception.getMessage());

    // Verifica que el mensaje de excepción es correcto
    assertTrue(exception.getMessage().contains(Message.UPDATE_ERROR));
  }

  @Test
  void delete() {
    ProviderEntity providerEntity = new ProviderEntity();
    providerEntity.setId(8L);
    providerEntity.setName("Karla");

    when(providerRepository.findById(8L)).thenReturn(Optional.of(providerEntity));

    this.providerService.delete(8L);
  }

  /**
   * The provider that does not exist is removed.
   * @throws ResourceNotFoundException provider not found
   */
  @Test
  @DisplayName("The provider that does not exist is removed")
  void providerNotFound() {
    ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
      providerService.delete(2L);
    });

    log.info("exception: {}", exception.getMessage());

    assertEquals("The id does not exist: 2", exception.getMessage());
  }

  @Test
  void providerDeleteError() {
    ProviderEntity providerEntity = new ProviderEntity();
    providerEntity.setId(8L);
    providerEntity.setName("Karla");

    when(providerRepository.findById(8L)).thenReturn(Optional.of(providerEntity));

    // Simula que ocurre un DataAccessException al intentar eliminar
    // doThrow solo se utiliza en metodos
    doThrow(new DataAccessException("Error accessing database") {}).when(providerRepository).deleteById(any());

    InternalServerException exception = assertThrows(InternalServerException.class, () -> {
      this.providerService.delete(8L);
    });

    log.info("exception: {}", exception.getMessage());

    assertEquals("Error deleting the provider with ID 8", exception.getMessage());
   }

  }