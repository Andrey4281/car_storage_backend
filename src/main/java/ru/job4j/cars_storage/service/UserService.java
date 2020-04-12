package ru.job4j.cars_storage.service;

import ru.job4j.cars_storage.service.model.LoginUser;
import ru.job4j.cars_storage.service.model.NewUserResponse;

public interface UserService {
    NewUserResponse save(LoginUser loginUser);
}
