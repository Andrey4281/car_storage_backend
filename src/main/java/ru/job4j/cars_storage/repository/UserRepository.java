package ru.job4j.cars_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.cars_storage.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByLogin(String login);
}
