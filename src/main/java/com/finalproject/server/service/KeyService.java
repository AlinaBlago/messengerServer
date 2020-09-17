package com.finalproject.server.service;

import com.finalproject.server.entity.Key;
import com.finalproject.server.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface KeyService {

    boolean isExistByUserId(Long id);
    void registerNewSignUp(User id , String key);
    Key add(Key key);
    Key getKeyByUserId(Long id);

}
