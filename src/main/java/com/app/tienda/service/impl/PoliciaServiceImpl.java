package com.app.tienda.service.impl;

import com.app.tienda.model.request.Policia;
import com.app.tienda.service.IPoliciaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class PoliciaServiceImpl implements IPoliciaService {
  private final Logger log = LoggerFactory.getLogger(this.getClass());
  private ArrayList<Policia> listaPolicias = new ArrayList<>();

  @Override
  public ArrayList<Policia> getAll() {
    log.info("Entrando a  service - getAll");
    return listaPolicias;
  }

  @Override
  public Policia getById(Long id) {
    log.info("Entrando a service -getBiId: {} ", id);

    Policia policiaEncontrado = listaPolicias.stream()
            .filter(policia -> policia.getId() == id)
            .findFirst()
            .orElse(null);

    return policiaEncontrado;
  }

  @Override
  public Policia save(Policia policia) {
    log.info("Entrando a  service - save");
    log.info("policia: {}", policia);

    listaPolicias.add(policia);
    return policia;
  }

  @Override
  public Boolean update(Long id, Policia policia) {
    log.info("Entrando a  service - update");
    log.info("id: {}", id);
    log.info("policia: {}", policia);

    Optional<Policia> policiaOptional = listaPolicias.stream()
            .filter(poli -> poli.getId() == id)
            .findFirst();

    if (policiaOptional.isPresent()) {
      Policia policiaEncontrado = policiaOptional.get();

      policiaEncontrado.setName(policia.getName());
      policiaEncontrado.setLastName(policia.getLastName());
      policiaEncontrado.setSecondLastName(policia.getSecondLastName());
      policiaEncontrado.setAge(policia.getAge());
      policiaEncontrado.setEmail(policia.getEmail());
      policiaEncontrado.setPhone(policia.getPhone());
      policiaEncontrado.setAddress(policia.getAddress());

      return true;
    }

    return false;
  }

  @Override
  public Boolean delete(Long id) {
    log.info("Entrando a  service - delete");
    log.info("id: {}", id);

    boolean eliminarPolicia = listaPolicias.removeIf(policia -> policia.getId() == id);
    log.info("resultado: {}", eliminarPolicia);

    return eliminarPolicia;
  }
}
