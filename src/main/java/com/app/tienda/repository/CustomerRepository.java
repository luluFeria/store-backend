package com.app.tienda.repository;

import com.app.tienda.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
  //  Estas 2 funciones, son 2 formas distintas para filtrar por ciudad.
  // Usar cualquiera de las 2 que se adapte a tu necesidad

  // 1
  //List<CustomerEntity> findByAddressCity(String city);

  //2
  
  //Aqui se esta realizando una consulta en SQL nativo, por que estoy indicando: nativeQuery = true
  @Query(value = "SELECT * FROM customers c INNER JOIN address a ON c.address_id = a.id WHERE a.city = :city", nativeQuery = true)
  List<CustomerEntity> findByAddressCity(String city);

  //Aqui se esta realizando una consulta en JPQL
  @Query("SELECT c FROM CustomerEntity c WHERE c.id = :id")
  Optional<CustomerEntity> findCustomerById(Long id);

  @Query("SELECT c FROM CustomerEntity c WHERE c.name = :name")
  Optional<CustomerEntity> findCustomerByName(String name);

  @Query("SELECT c FROM CustomerEntity c WHERE c.email = :email")
  Optional<CustomerEntity> findCustomerByEmail(String email);
}
