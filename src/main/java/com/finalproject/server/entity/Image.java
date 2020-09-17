package com.finalproject.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "images")
public class Image extends AbstractEntity {

    byte image;

    public Image() {
    }

    public Image(byte image){
        this.image = image;
    }

    @Column(name = "image", nullable = false)
    public byte getImage() {
        return image;
    }

    public void setImage(byte image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Image{" +
                "image=" + image +
                '}';
    }
}
