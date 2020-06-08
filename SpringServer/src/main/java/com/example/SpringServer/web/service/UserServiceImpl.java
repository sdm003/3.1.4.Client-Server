package com.example.SpringServer.web.service;


import com.example.SpringServer.web.dao.UserDAO;
import com.example.SpringServer.web.model.Role;
import com.example.SpringServer.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Transactional
    @Override
    public boolean addUser(User user) {
        if (userExist(user.getEmail())) {
            return false;
        } else if (user.getFirstname().trim().length() == 0 || user.getPassword().trim().length() == 0 ||
                user.getEmail().trim().length() == 0 || user.getLastname().trim().length() == 0 || user.getRoles().equals("null")) {
            return false;
        } else {
            userDAO.addUser(user);
            return true;
        }
    }

    @Transactional
    @Override
    public User getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    @Transactional
    @Override
    public void removeUser(Long id) {
        userDAO.removeUser(id);
    }

    @Transactional
    @Override
    public boolean updateUser(User user) {
        if (user.getFirstname().trim().length() == 0 || user.getPassword().trim().length() == 0 ||
                user.getEmail().trim().length() == 0 || user.getLastname().trim().length() == 0 || user.getRoles().equals("null")) {
            return false;
        } else {
            userDAO.updateUser(user);
            return true;
        }
    }

        @Transactional
        @Override
        public void addFirstAdminAndUser() {
            if (!userExist("admin@gmail.com") && !userExist("user@gmail.com")) {
                Set<Role> adminRoles = new HashSet<>();
                adminRoles.add(new Role("ADMIN"));
                adminRoles.add(new Role("USER"));
                userDAO.addUser(new User("admin@gmail.com", "admin", "Bilbo", "Baggins", 111, adminRoles));
                Set<Role> userRoles = new HashSet<>();
                userRoles.add(new Role("USER"));
                userDAO.addUser(new User("user@gmail.com", "user", "Senya", "Rozenbaum", 98, userRoles));
            }
        }

    @Override
    public Set<Role> getRoleForUser(String roles) {
        Set<Role> rolesSet = new HashSet<>();
        try {
            String[] partsRole = roles.split(",");
            for (String role : partsRole) {
                rolesSet.add(new Role(role));
            }
            return rolesSet;
        } catch (Exception e) {
            e.printStackTrace();
        }
        rolesSet.add(new Role(roles));
        return rolesSet;
    }

    @Transactional
    @Override
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Transactional
    @Override
    public boolean userExist(String email) {
        return userDAO.userExist(email);
    }

}
