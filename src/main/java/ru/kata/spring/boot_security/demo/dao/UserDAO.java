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

    void update(int id, String name, int age, String password);

    Optional<User> findByUsername(String username);

    void saveWothoutRole(User user);
}
