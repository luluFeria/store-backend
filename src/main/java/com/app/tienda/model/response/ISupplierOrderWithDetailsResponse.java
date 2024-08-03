package com.app.tienda.model.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ISupplierOrderWithDetailsResponse {
   Long getOrderId();
   LocalDateTime getDate();
   String getStatus();
   BigDecimal getTotalAmount();
   String getProduct();
   BigDecimal getPrice();
   Integer getQuantity();
}
