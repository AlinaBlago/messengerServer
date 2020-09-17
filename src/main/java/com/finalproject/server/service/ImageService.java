package com.finalproject.server.service;

import com.finalproject.server.entity.Image;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ImageService {

    Iterable<Image> findAll();
    Optional<Image> findImageById(Long id);
    void deleteImageById(Long id);
    Image add(Image image);

}
