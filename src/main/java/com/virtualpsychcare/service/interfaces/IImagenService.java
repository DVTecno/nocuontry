package com.virtualpsychcare.service.interfaces;

import com.virtualpsychcare.entities.Imagen;

import java.util.List;

public interface IImagenService {
    public List<Imagen> findAll();
    public Imagen save(Imagen imagen);
    public void delete(int id);
}
