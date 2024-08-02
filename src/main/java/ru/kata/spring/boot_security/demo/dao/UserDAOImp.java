package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImp implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<User> index() {
        List<User> userlist = entityManager.createQuery("FROM User", User.class).getResultList();
        return userlist;
    }

    @Override
    @Transactional
    public void save(User user) {
        Role role = new Role("ROLE_USER",user );
        user.setRoles(Arrays.asList(role));
        user.setPassword("111");
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void delete(int id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public User findById(int id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    @Transactional
    public void update(User user) {
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
