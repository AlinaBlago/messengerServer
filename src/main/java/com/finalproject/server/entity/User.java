package com.finalproject.server.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User extends AbstractEntity {

    String name;
    String login;
    String password;
    boolean isBanned;
    boolean isAdmin;
    Image idImage;

    public User() {
    }

    public User(String name, String login, String password, boolean isBanned, boolean isAdmin){
        this.name = name;
        this.login = login;
        this.password = password;
        this.isBanned = isBanned;
        this.isAdmin = isAdmin;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NaturalId
    @Column(name = "login", nullable = false)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password", nullable = false)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "is_banned", nullable = false)
    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    @Column(name = "is_admin", nullable = false)
    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    public Image getIdImage() {
        return idImage;
    }

    public void setIdImage(Image idImage) {
        this.idImage = idImage;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", isBanned=" + isBanned +
                ", isAdmin=" + isAdmin +
                ", idImage=" + idImage +
                '}';
    }
}
