package ru.kata.spring.boot_security.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import java.util.Arrays;

@Component
public class CreateTestUsers implements CommandLineRunner {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userDAO.findByUsername("admin").isEmpty()) {
            User adminUser = new User("admin", 30, "admin");
            Role adminRole = new Role("ROLE_ADMIN", Arrays.asList(adminUser));
            adminUser.setRoles(Arrays.asList(adminRole));
            userDAO.saveWothoutRole(adminUser);
        }

        if (userDAO.findByUsername("user").isEmpty()) {
            User normalUser = new User("user", 25, "user");
            Role userRole = new Role("ROLE_USER", Arrays.asList(normalUser));
            normalUser.setRoles(Arrays.asList(userRole));
            userDAO.saveWothoutRole(normalUser);
        }
    }
}
