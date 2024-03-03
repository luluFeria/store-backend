package com.app.tienda.optional;

import java.util.Optional;

public class EjemploOptional1 {
  public static void main(String[] args) {
  String nombre = null;

  // El optional solo puede recibir 2 valores (null, y el tipo de valor que se indique en el generico)
  Optional<String> longitud = Optional.ofNullable(nombre);

  //Nota: Hacer uso de cualquiera de los 2 if, dependiendo el caso

    if (longitud.isPresent()) { //Verifica si existe un valor distinto a null en el optional
      System.out.println("Hay valor: ");
      System.out.println(longitud.get());  // con .get se obtiene el valor que tiene el optional

      System.out.println("Longitud del nombre: " + longitud.get().length());
    }
    if (longitud.isEmpty()) { //Verifica si existe o llega un valor null en el optional
      System.out.println("Hay un valor null ");
    }
  }
}
