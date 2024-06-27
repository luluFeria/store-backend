package com.app.tienda.constant;

public class Querys {
  //Estoy definiendo una constante, porque viene la palabra final
  public static final String QUERY_FILTER_PRODUCTS_BY_PROVIDER = "SELECT \n" +
          "    p.id,\n" +
          "    p.name,\n" +
          "    p.description,\n" +
          "    p.price,\n" +
          "    p.quantity_In_Inventory as inventory,\n" +
          "    p.category,\n" +
          "    pr.name AS provider\n" +
          "FROM \n" +
          "    products p\n" +
          "INNER JOIN \n" +
          "    providers pr ON  pr.id =   p.provider_id\n" +
          "WHERE \n" +
          "    pr.id = :id";
  public static final String QUERY_FILTER_PRODUCTS_BY_NAME = "SELECT * FROM PRODUCTS WHERE name = :name";

  public static final String QUERY_FILTER_PRODUCTS_BY_CATEGORY = "SELECT * FROM PRODUCTS WHERE category = :category";

}
