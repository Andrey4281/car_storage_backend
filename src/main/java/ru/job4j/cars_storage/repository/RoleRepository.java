package ru.job4j.cars_storage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.cars_storage.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findFirstByName(String name);
}
