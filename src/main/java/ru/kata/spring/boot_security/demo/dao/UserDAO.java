package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

@Component
public interface UserDAO {
    List<User> index();

    void save(User user);

    void delete(int id);

    User findById(int id);

    void update(User user);

    Optional<User> findByUsername(String username);
}
