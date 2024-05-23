package com.virtualpsychcare.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Imagen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String imagenUrl;
    private String imagenId;

    public Imagen(String originalFilename, String url, String publicId) {
        this.name = originalFilename;
        this.imagenUrl = url;
        this.imagenId = publicId;
    }
}
