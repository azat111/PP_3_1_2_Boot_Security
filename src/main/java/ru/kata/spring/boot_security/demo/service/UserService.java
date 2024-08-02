package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    List<User> index();

    void save(User user);

    void delete(int id);

    User findById(int id);

    void update(int id, String name, int age);

    Optional<User> findByUsername(String username);
}
