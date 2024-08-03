package com.app.tienda.constant;

public class Querys {
  //Estoy definiendo una constante, porque viene la palabra final
  public static final String QUERY_FIND_ALL_PROVIDER_ORDERS = "SELECT po.id as orderId, po.date, po.status, po.total_amount as totalAmount, \n" +
          "p.id as productId, p.name as product, p.price, pop.quantity \n" +
          "FROM PROVIDER_ORDERS po\n" +
          "JOIN PROVIDER_ORDER_PRODUCT pop on po.id = pop.provider_order_id\n" +
          "JOIN PRODUCTS p on pop.product_id = p.id";

  public static final String QUERY_FILTER_PRODUCTS_BY_PROVIDER = "SELECT \n" +
          "    p.id,\n" +
          "    p.name,\n" +
          "    p.description,\n" +
          "    p.price,\n" +
          "    p.quantity as inventory,\n" +
          "    p.category,\n" +
          "    pr.name AS provider\n" +
          "FROM \n" +
          "    products p\n" +
          "INNER JOIN \n" +
          "    providers pr ON  pr.id =   p.provider_id\n" +
          "WHERE \n" +
          "    pr.id = :id";

  public static final String QUERY_FILTER_PROVIDER_ORDER_BY_PROVIDER_ID = "SELECT po.id AS orderId, po.date, po.status, po.total_amount AS totalAmount, \n" +
    "p.id AS productId, p.name AS product, p.price, pop.quantity \n" +
    "FROM PROVIDER_ORDERS po \n" +
    "JOIN PROVIDER_ORDER_PRODUCT pop ON po.id = pop.provider_order_id \n" +
    "JOIN PRODUCTS p ON pop.product_id = p.id \n" +
    "WHERE po.provider_id = :providerId";

  public static final String QUERY_FILTER_ORDERS_BY_STATUS = "SELECT po.id AS orderId, po.date, po.status, po.total_amount AS totalAmount, \n" +
    "p.id AS productId, p.name AS product, p.price, pop.quantity \n" +
    "FROM PROVIDER_ORDERS po \n" +
    "JOIN PROVIDER_ORDER_PRODUCT pop ON po.id = pop.provider_order_id \n" +
    "JOIN PRODUCTS p ON pop.product_id = p.id \n" +
    "WHERE po.status = :status";
  public static final String QUERY_FILTER_PRODUCTS_BY_NAME = "SELECT * FROM PRODUCTS WHERE name = :name";

  public static final String QUERY_FILTER_PRODUCTS_BY_CATEGORY = "SELECT * FROM PRODUCTS WHERE category = :category";

}
