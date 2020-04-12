package ru.job4j.cars_storage.service.model;

import java.util.Objects;

public class AuthTokenResponse {
    private String token;
    private String username;

    public AuthTokenResponse(){

    }

    public AuthTokenResponse(String token, String username){
        this.token = token;
        this.username = username;
    }

    public AuthTokenResponse(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthTokenResponse authTokenResponse = (AuthTokenResponse) o;
        return Objects.equals(token, authTokenResponse.token) &&
                Objects.equals(username, authTokenResponse.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, username);
    }
}
