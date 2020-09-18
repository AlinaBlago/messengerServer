package com.finalproject.server.service.impl;

import com.finalproject.server.entity.Image;
import com.finalproject.server.repository.ImageRepository;
import com.finalproject.server.service.ImageOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageService implements ImageOperations {

    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

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

    @Override
    public Long save(Image image) {
        return imageRepository.save(image).getId();
    }

    @Override
    public void updateAll(Iterable<Image> images) {
        imageRepository.saveAll(images);
    }


}
