package com.app.tienda.repository;

import com.app.tienda.entity.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProviderRepository extends JpaRepository<ProviderEntity, Long> {

  /**
   * Obtiene una lista de proveedores que viven en la ciudad especificada.
   *
   * @param city la ciudad para filtrar los proveedores.
   * @return una lista de proveedores en la ciudad dada.
   */
  @Query(value = "SELECT * FROM PROVIDERS p INNER JOIN ADDRESS a ON p.address_id = a.id WHERE a.city = :city", nativeQuery = true)
  List<ProviderEntity> findByAddressCity(String city);

  /**
   *  Filtrando los proveedores por nombre.
   *
   * @param name filtrar los proveedores por nombre.
   * @return retorna una lista de proveedores que coincidan con el nombre.
   */
  @Query(value = "SELECT * FROM PROVIDERS WHERE name = :name", nativeQuery = true)
  List<ProviderEntity> findByName(String name);
}


