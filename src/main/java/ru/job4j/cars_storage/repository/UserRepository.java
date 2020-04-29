package ru.job4j.cars_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.job4j.cars_storage.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT distinct user from User user join fetch user.roles WHERE user.login = :name")
    User findUserByLoginWithRoles(@Param("name") String name);
}
