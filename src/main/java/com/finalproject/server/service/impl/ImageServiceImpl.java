package com.finalproject.server.service.impl;

import com.finalproject.server.entity.Image;
import com.finalproject.server.repository.ImageRepository;
import com.finalproject.server.repository.UserRepository;
import com.finalproject.server.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ImageServiceImpl implements ImageService {

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Iterable<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> findImageById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public Image add(Image image) {
        return imageRepository.save(image);
    }



}
