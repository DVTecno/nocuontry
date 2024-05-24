package com.virtualpsychcare.controller;

import com.virtualpsychcare.dto.MessageDto;
import com.virtualpsychcare.entities.Imagen;
import com.virtualpsychcare.service.implementation.CloudinaryServiceImpl;
import com.virtualpsychcare.service.implementation.ImagenServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/cloudinary")
@CrossOrigin
public class ImagenController {
    private final CloudinaryServiceImpl cloudinaryService;
    private final ImagenServiceImpl imagenServiceImpl;

    public ImagenController(CloudinaryServiceImpl cloudinaryService, ImagenServiceImpl imagenServiceImpl) {
        this.cloudinaryService = cloudinaryService;
        this.imagenServiceImpl = imagenServiceImpl;
    }

    @GetMapping("/images")
    public ResponseEntity<List<Imagen>> getImages() {
        List<Imagen> images = imagenServiceImpl.findAll();
        return ResponseEntity.ok().body(images);
    }

    @PostMapping("/upload")
    public ResponseEntity<Imagen> upload(@RequestParam MultipartFile file) {

        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

            if(bufferedImage == null) {
                return new ResponseEntity(new MessageDto("The file is not a valid image"), HttpStatus.BAD_REQUEST);
            }
            Map<String, Object> result = cloudinaryService.uploadImage(file);
            Imagen imagen = new Imagen((String) result.get("original_filename"), (String) result.get("url"), (String) result.get("public_id"));

            imagenServiceImpl.save(imagen);

            return ResponseEntity.ok(imagen);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new Imagen());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {
        if(!imagenServiceImpl.existsById(id)) {
            return ResponseEntity.badRequest().body(Map.of("error", "The image does not exist"));
        }
        try {
            Imagen imagen = imagenServiceImpl.getOne(id).get();

            Map<String, Object> result = cloudinaryService.delete(imagen.getImagenId());

            imagenServiceImpl.delete(id);
            return ResponseEntity.ok().body(Map.of("message", "Image successfully deleted", "result", result));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
