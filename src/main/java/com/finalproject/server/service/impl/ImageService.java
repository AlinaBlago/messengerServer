package com.finalproject.server.service.impl;

import com.finalproject.server.repository.ImageRepository;
import com.finalproject.server.service.ImageOperations;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ImageService implements ImageOperations {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

}
