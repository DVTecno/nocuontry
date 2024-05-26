package com.virtualpsychcare.service.implementation;

import com.virtualpsychcare.entities.Imagen;
import com.virtualpsychcare.repository.ImagenRepository;
import com.virtualpsychcare.service.interfaces.IImagenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Transactional
@Service
public class ImagenServiceImpl implements IImagenService {
    private final CloudinaryServiceImpl cloudinaryService;
    private final ImagenRepository imagenRepository;

    public ImagenServiceImpl(CloudinaryServiceImpl cloudinaryService, ImagenRepository imagenRepository) {
        this.cloudinaryService = cloudinaryService;
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

    @Override
    public boolean existsById(int id) {
        return imagenRepository.existsById(id);
    }


    public Optional<Imagen> getOne(int id) {
        return imagenRepository.findById(id);
    }

    public Imagen upload(MultipartFile file)  {
        Map<String, Object> result = null;

        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

            if (bufferedImage == null) {
                throw new RuntimeException("The file is not a valid image");
            }

            result = cloudinaryService.uploadImage(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Imagen imagen = new Imagen((String) result.get("original_filename"), (String) result.get("url"), (String) result.get("public_id"));

        this.save(imagen);

        return imagen;
    }
}
