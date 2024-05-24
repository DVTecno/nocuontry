package com.virtualpsychcare.service.interfaces;

import com.virtualpsychcare.entities.Imagen;

import java.util.List;
import java.util.Optional;

public interface IImagenService {
    List<Imagen> findAll();
    Imagen save(Imagen imagen);
     void delete(int id);

    boolean existsById(int id);

    Optional<Imagen> getOne(int id);
}
