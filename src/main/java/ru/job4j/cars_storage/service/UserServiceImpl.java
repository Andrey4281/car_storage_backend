package ru.job4j.cars_storage.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.job4j.cars_storage.domain.Role;
import ru.job4j.cars_storage.service.model.LoginUser;
import ru.job4j.cars_storage.domain.User;
import ru.job4j.cars_storage.repository.RoleRepository;
import ru.job4j.cars_storage.repository.UserRepository;
import ru.job4j.cars_storage.service.model.NewUserResponse;

import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private BCryptPasswordEncoder bcryptEncoder;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = userRepository.findUserByLoginWithRoles(s);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        return user;
    }

    @Override
    public NewUserResponse save(LoginUser loginUser) {
        NewUserResponse response = new NewUserResponse();
        if (userRepository.findUserByLoginWithRoles(loginUser.getLogin()) != null) {
            response.setSuccess(false);
            response.setErrorMessage(String.format("User with login %s already exists", loginUser.getLogin()));
        } else {
            User user = new User();
            user.setLogin(loginUser.getLogin());
            user.setPassword(bcryptEncoder.encode(loginUser.getPassword()));
            user.setPhone(loginUser.getPhone());
            user.setRoles(new HashSet<>(Arrays.asList(roleRepository.findFirstByName("ROLE_USER"))));
            userRepository.save(user);
        }
        return response;
    }
}
