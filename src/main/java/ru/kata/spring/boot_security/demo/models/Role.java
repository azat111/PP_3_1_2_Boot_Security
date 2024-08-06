package ru.kata.spring.boot_security.demo.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String role;

    @ManyToMany(mappedBy = "roles")
    private List<User> user;

    public Role() {
    }

    public Role(String role, List<User> user) {
        this.role = role;
        this.user = user;
    }

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public Role(String role) {
        this.role = role;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public static Role findByRoleName(String roleName, EntityManager entityManager) {
        List<Role> roles = entityManager.createQuery("SELECT r FROM Role r WHERE r.role = :role", Role.class)
                .setParameter("role", roleName)
                .getResultList();

        if (roles.isEmpty()) {
            return null;
        }
        return roles.get(0);
    }

    @Override
    public String toString() {
        return role;
    }
}
