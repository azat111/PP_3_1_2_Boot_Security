package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class UserDAOImp implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> index() {
        List<User> userlist = entityManager.createQuery("FROM User", User.class).getResultList();
        return userlist;
    }

    @Override
    public void save(User user) {
        List<Role> roles = user.getRoles().stream().map(role -> Role.findByRoleName(role.getRole(), entityManager)).collect(Collectors.toList());
        user.setRoles(roles);
        entityManager.persist(user);
    }

    @Override
    public void saveWothoutRole(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            user.getRoles().clear();
            entityManager.remove(user);
        }
    }

    @Override
    public User findById(int id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public void update(int id, String name, int age, String password, String role) {
        User user = findById(id);
        user.setAge(age);
        user.setName(name);
        user.setPassword(password);
        List<Role> newRoles = new ArrayList<>();
        newRoles.add(new Role("ROLE_" + role));
        user.setRoles(newRoles);
        entityManager.merge(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.name = :name", User.class)
                .setParameter("name", username)
                .getResultList();

        if (users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }
}
