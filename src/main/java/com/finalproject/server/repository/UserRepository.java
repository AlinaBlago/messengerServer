package com.finalproject.server.repository;

import com.finalproject.server.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findUserByLogin(String login);
    User findUserByLoginAndPassword(String login, String password);
}
