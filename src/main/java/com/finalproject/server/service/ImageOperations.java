package com.finalproject.server.service;

import com.finalproject.server.entity.Image;

import java.util.Optional;

public interface ImageOperations {

    Iterable<Image> findAll();
    Optional<Image> findImageById(Long id);
    void deleteImageById(Long id);
    Image add(Image image);
    Long save(Image image);
    void updateAll(Iterable<Image> images);
}
