package com.finalproject.server.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    String name;

    @NaturalId
    @Column(name = "login", nullable = false)
    String login;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "is_banned", nullable = false)
    boolean isBanned;

    @Column(name = "is_admin", nullable = false)
    boolean isAdmin;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

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
