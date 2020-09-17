package com.finalproject.server.entity;

import javax.persistence.*;

@MappedSuperclass
public abstract class AbstractEntity {

    private Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
