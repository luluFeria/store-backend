package com.app.tienda.repository;

import com.app.tienda.constant.Querys;
import com.app.tienda.entity.ProviderOrderEntity;
import com.app.tienda.model.response.IProductResponse;
import com.app.tienda.model.response.ISupplierOrderWithDetailsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProviderOrderRepository extends JpaRepository<ProviderOrderEntity, Long> {
  /**
   * Recupera todas las órdenes de proveedor con sus detalles.
   *
   * Ejecuta una consulta nativa para obtener los detalles de las órdenes, incluyendo el ID de la orden,
   * la fecha, el estado, el monto total, el ID del producto, el nombre, el precio y la cantidad.
   *
   * @return una lista de objetos {@link ISupplierOrderWithDetailsResponse} con los detalles de las órdenes de proveedor.
   */
  @Query(value = Querys.QUERY_FIND_ALL_PROVIDER_ORDERS, nativeQuery = true)
  List<ISupplierOrderWithDetailsResponse> findAllProviderOrders();

  @Query(value = Querys.QUERY_FILTER_PROVIDER_ORDER_BY_PROVIDER_ID, nativeQuery = true)
  List<ISupplierOrderWithDetailsResponse> getByProviderId(Long providerId);

  @Query(value = Querys.QUERY_FILTER_ORDERS_BY_STATUS, nativeQuery = true)
  List<ISupplierOrderWithDetailsResponse> findByStatus(String status);

}
