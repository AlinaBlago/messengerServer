package com.finalproject.server.service.impl;

import com.finalproject.server.entity.Image;
import com.finalproject.server.repository.ImageRepository;
import com.finalproject.server.service.ImageOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ImageService implements ImageOperations {
    private final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

}
