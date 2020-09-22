package com.finalproject.server.service.impl;

import com.finalproject.server.entity.Key;
import com.finalproject.server.entity.User;
import com.finalproject.server.repository.KeyRepository;
import com.finalproject.server.service.KeyOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class KeyService implements KeyOperations {
    private final KeyRepository keyRepository;

    public KeyService(KeyRepository keyRepository) {
        this.keyRepository = keyRepository;
    }

    @Override
    public boolean isExistByUserId(Long id) {
        return keyRepository.existsByUserId(id);
    }

    @Override
    public void registerNewSignUp(User user, String key) {
        if(this.isExistByUserId(user.getId())){
            keyRepository.getKeyByUserId(user.getId()).setKey(key);
        }else{
            Key tmpKey = new Key();
            tmpKey.setUserId(user);
            tmpKey.setKey(key);
            add(tmpKey);
        }
    }

    @Override
    public Key add(Key key) {
        return keyRepository.save(key);
    }

    @Override
    public Key getKeyByUserId(Long id) {
        return keyRepository.getKeyByUserId(id);
    }

    @Override
    public Long save(Key key) {
        return keyRepository.save(key).getId();
    }

    @Override
    public void updateAll(Iterable<Key> keys) {
        keyRepository.saveAll(keys);
    }
}