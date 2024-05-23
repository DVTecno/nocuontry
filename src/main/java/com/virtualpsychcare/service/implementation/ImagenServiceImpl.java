package com.virtualpsychcare.service.implementation;

import com.virtualpsychcare.entities.Imagen;
import com.virtualpsychcare.repository.ImagenRepository;
import com.virtualpsychcare.service.interfaces.IImagenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class ImagenServiceImpl implements IImagenService {
    private final ImagenRepository imagenRepository;

    public ImagenServiceImpl(ImagenRepository imagenRepository) {
        this.imagenRepository = imagenRepository;
    }

    @Override
    public List<Imagen> findAll() {
        return imagenRepository.findAllByOrderByIdDesc();
    }

    @Override
    public Imagen save(Imagen imagen) {
        return imagenRepository.save(imagen);
    }

    @Override
    public void delete(int id) {
        imagenRepository.deleteById(id);
    }
}
