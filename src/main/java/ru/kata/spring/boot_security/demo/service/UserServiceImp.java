package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.security.UserDetail;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<User> index() {
        return userDAO.index();
    }

    @Override
    public void save(User user) {
        userDAO.save(user);
    }

    @Override
    public void delete(int id) {
        userDAO.delete(id);
    }

    @Override
    public User findById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public void update(User user) {
        userDAO.update(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = Optional.ofNullable(userDAO.findByUsername(username).orElse(null));

        if(user.isEmpty())
            throw new UsernameNotFoundException("User not found");

        System.out.println("User found: " + user.get().getName());
        return new UserDetail(user.get());
    }
}
