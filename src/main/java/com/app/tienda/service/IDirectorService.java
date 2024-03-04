package com.app.tienda.service;

import java.util.ArrayList;

public interface IDirectorService {
  public ArrayList<Director> getAll();
  public Director save(Director director);
  public Director getById(Long id);
  public Boolean update(Long id, Director director);
  public Boolean delete(Long id);
}
