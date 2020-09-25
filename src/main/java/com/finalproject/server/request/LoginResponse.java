package com.finalproject.server.request;

import java.util.Objects;

public class LoginResponse {
    private String login;
    private String password;

    public static LoginResponse fromMessage(LoginResponse response) {
        return new LoginResponse(
                response.getLogin(),
                response.getPassword()
        );
    }

    LoginResponse() {}

    public LoginResponse(String login, String password) {
        this.login = login;
        this.password = password;
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

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof LoginResponse)) return false;
//        LoginResponse that = (LoginResponse) o;
//        return login.equals(that.login) &&
//                password.equals(that.password);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(login, password);
//    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
