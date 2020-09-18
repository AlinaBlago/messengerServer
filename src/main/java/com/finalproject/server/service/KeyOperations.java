package com.finalproject.server.service;

import com.finalproject.server.entity.Key;
import com.finalproject.server.entity.Message;
import com.finalproject.server.entity.User;
import org.springframework.stereotype.Service;

public interface KeyOperations {

    boolean isExistByUserId(Long id);
    void registerNewSignUp(User id , String key);
    Key add(Key key);
    Key getKeyByUserId(Long id);
    Long save(Key key);
    void updateAll(Iterable<Key> keys);
}
