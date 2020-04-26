package ru.job4j.cars_storage.service.model;

import java.util.Objects;

public class LoginUser {
    private String login;
    private String password;
    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginUser loginUser = (LoginUser) o;
        return Objects.equals(login, loginUser.login) &&
                Objects.equals(password, loginUser.password) &&
                Objects.equals(phone, loginUser.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, phone);
    }

    @Override
    public String toString() {
        return "LoginUser{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
