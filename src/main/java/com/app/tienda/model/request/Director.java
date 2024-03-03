package com.app.tienda.model.request;

public class Director {
  private Long id;
  private String name;
  private String lastName;
  private String secondLastName;
  private Integer age;
  private String email;
  private String phone;
  private Domicilio address;

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

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getSecondLastName() {
    return secondLastName;
  }

  public void setSecondLastName(String secondLastName) {
    this.secondLastName = secondLastName;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Domicilio getAddress() {
    return address;
  }

  public void setAddress(Domicilio address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return "Director{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", lastName='" + lastName + '\'' +
            ", secondLastName='" + secondLastName + '\'' +
            ", age=" + age +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", address=" + address +
            '}';
  }
}
