package com.app.tienda.repository;


import com.app.tienda.constant.Querys;
import com.app.tienda.entity.ProductEntity;
import com.app.tienda.entity.ProviderEntity;
import com.app.tienda.model.response.IProductResponse;
import com.app.tienda.model.response.ProductResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
  /*
   TODO: Pendiente, esta por corregir
   */
  @Query(value = Querys.QUERY_FILTER_PRODUCTS_BY_PROVIDER, nativeQuery = true)
  List<IProductResponse> findProductsByProvider(Long id);

  /**
   *  Filtrando los productos por nombre.
   *
   * @param name filtrar los productos por nombre.
   * @return retorna una lista de productos que coincidan con el nombre.
   */
  @Query(value = Querys.QUERY_FILTER_PRODUCTS_BY_NAME, nativeQuery = true)
  List<ProductEntity> findByName(String name);

  /**
   *  Filtrando los productos por category.
   *
   * @param category filtrar los productos por category.
   * @return retorna una lista de productos que coincidan con el category.
   */
  @Query(value = Querys.QUERY_FILTER_PRODUCTS_BY_CATEGORY, nativeQuery = true)
  List<ProductEntity> findByCategory(String category);
}
