package com.app.tienda.service;

import com.app.tienda.entity.PersonEntity;
import com.app.tienda.model.request.PersonRequest;
import com.app.tienda.model.response.PersonResponse;

import java.util.List;

public interface IPersonService {
  public List<PersonResponse> findAllPerson();
  public PersonResponse save(PersonRequest personRequest);

  public PersonResponse getById(Long id);

  public PersonResponse update(Long id, PersonRequest personRequest);

  public void delete(Long id);

}
