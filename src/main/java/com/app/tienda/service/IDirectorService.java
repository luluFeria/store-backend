package com.app.tienda.service;

import com.app.tienda.model.request.Director;
import com.app.tienda.model.request.Policia;

import java.util.ArrayList;

public interface IDirectorService {
  public ArrayList<Director> getAll();
  public Director save(Director director);
  public Director getById(Long id);
  public Boolean update(Long id, Director director);
  public Boolean delete(Long id);
}
