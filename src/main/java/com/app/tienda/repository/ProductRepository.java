package com.app.tienda.repository;


import com.app.tienda.entity.ProductEntity;
import com.app.tienda.entity.ProviderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

  /*
   TODO: Pendiente, esta por corregir
   */
  @Query(value = "SELECT * FROM PROVIDERS p INNER JOIN ADDRESS a ON p.address_id = a.id WHERE a.city = :city", nativeQuery = true)
  List<ProviderEntity> findProductsByProvider(String city);

  /**
   *  Filtrando los productos por nombre.
   *
   * @param name filtrar los productos por nombre.
   * @return retorna una lista de productos que coincidan con el nombre.
   */
  @Query(value = "SELECT * FROM PRODUCTS WHERE name = :name", nativeQuery = true)
  List<ProductEntity> findByName(String name);

}
