package com.finalproject.server.repository;

import com.finalproject.server.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntityRepository extends CrudRepository<User, Long> {

}
