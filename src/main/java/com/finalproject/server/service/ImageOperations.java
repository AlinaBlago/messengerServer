package com.finalproject.server.service;

import com.finalproject.server.entity.Image;
import com.finalproject.server.entity.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface ImageOperations {

    Iterable<Image> findAll();
    Optional<Image> findImageById(Long id);
    void deleteImageById(Long id);
    Image add(Image image);
    Long save(Image image);
    void updateAll(Iterable<Image> images);
}
