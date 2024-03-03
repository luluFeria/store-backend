package com.app.tienda.model.response;

import javax.validation.constraints.NotBlank;

public class PersonResponse {
  private Long id;
  private String name;
  private String firstName;
  private String secondName;
  private String age;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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
}
