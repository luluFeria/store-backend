package com.app.tienda.service;

import com.app.tienda.model.request.Policia;

import java.util.ArrayList;

public interface IPoliciaService {
  public ArrayList<Policia> getAll();

  public Policia getById(Long id);

  public Policia save(Policia policia);

  public Boolean update(Long id, Policia policia);

  public Boolean delete(Long id);
}
