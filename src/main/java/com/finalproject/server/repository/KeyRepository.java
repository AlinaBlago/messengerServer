package com.finalproject.server.repository;

import com.finalproject.server.entity.Key;
import com.finalproject.server.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyRepository extends CrudRepository<Key, Long> {

    Key getKeyByUserId(Long id);
    boolean existsByUserId(Long id);

}
