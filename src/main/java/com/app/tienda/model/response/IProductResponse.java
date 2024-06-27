package com.app.tienda.model.response;

public interface IProductResponse {

   Long getId();

   String getName();

   String getDescription();

   Integer getPrice();

   Integer getInventory();

   String getCategory();

   String getProvider();
}
