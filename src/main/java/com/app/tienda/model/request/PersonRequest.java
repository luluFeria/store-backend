package com.app.tienda.model.request;

import javax.validation.constraints.*;

public class PersonRequest {
  @NotBlank(message = "El nombre no puede estar en blanco")
  @Size(min = 3, max = 20, message = "El nombre debe de contener de 3 a 20 caracteres")
  private String name;

  @NotBlank(message = "El apellido paterno no puede estar en blanco")
  private String firstName;

  private String secondName;

  @NotBlank(message = "La edad no puede estar en blanco")
  private String age;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getSecondName() {
    return secondName;
  }

  public void setSecondName(String secondName) {
    this.secondName = secondName;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  @Override
  public String toString() {
    return "PersonRequest{" +
            "name='" + name + '\'' +
            ", firstName='" + firstName + '\'' +
            ", secondName='" + secondName + '\'' +
            ", age='" + age + '\'' +
            '}';
  }
}

