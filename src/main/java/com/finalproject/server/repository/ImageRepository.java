package com.finalproject.server.repository;

import com.finalproject.server.entity.Image;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface ImageRepository extends CrudRepository<Image, Long> {
}
