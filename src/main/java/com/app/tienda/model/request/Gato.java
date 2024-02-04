package com.app.tienda.model.request;

import java.util.ArrayList;

public class Gato {
  private Long id;
  private String name;
  private Integer age;
  private String breed;
  private ArrayList<String> hobbies;

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

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public String getBreed() {
    return breed;
  }

  public void setBreed(String breed) {
    this.breed = breed;
  }

  public ArrayList<String> getHobbies() {
    return hobbies;
  }

  public void setHobbies(ArrayList<String> hobbies) {
    this.hobbies = hobbies;
  }

  @Override
  public String toString() {
    return "Gato{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", age=" + age +
            ", breed='" + breed + '\'' +
            ", hobbies=" + hobbies +
            '}';
  }
}
