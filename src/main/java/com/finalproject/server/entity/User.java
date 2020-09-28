package com.finalproject.server.entity;

import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    String name;

    //@NaturalId
    @Column(name = "login", nullable = false)
    String username;

    @Column(name = "password", nullable = false, length = 255)
    String password;

    @Transient
    private String passwordConfirm;

    @Column(name = "is_banned", nullable = false)
    boolean isBanned;

    @Column(name = "enabled", nullable = false)
    boolean enabled;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    Image idImage;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "id_user"),
            inverseJoinColumns = @JoinColumn(name = "id_role")
    )
    private Set<Role> roles = new HashSet<>();

    public User() {
    }

    public User(String name, String username, String password, boolean isBanned, boolean enabled){
        this.name = name;
        this.username = username;
        this.password = password;
        this.isBanned = isBanned;
        this.enabled = enabled;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

//    @Override
//    public boolean isEnabled() {
//        return enabled;
//    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Image getIdImage() {
        return idImage;
    }

    public void setIdImage(Image idImage) {
        this.idImage = idImage;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", login='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isBanned=" + isBanned +
                ", isAdmin=" + enabled +
                ", idImage=" + idImage +
                '}';
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }



}
