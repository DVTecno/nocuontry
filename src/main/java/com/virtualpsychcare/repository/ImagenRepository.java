package com.virtualpsychcare.repository;

import com.virtualpsychcare.entities.Imagen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagenRepository extends JpaRepository<Imagen, Integer> {
    List<Imagen> findAllByOrderByIdDesc();
}
