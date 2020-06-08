package com.example.SpringClient.web.service;

import com.example.SpringClient.web.dto.UserDTO;
import com.example.SpringClient.web.model.Role;
import com.example.SpringClient.web.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

 public interface RestTemplateService {

     List<UserDTO> getAllUsers();

     UserDTO addUser(UserDTO userDTO);

     UserDTO updateUser(UserDTO userDTO);

     void removeUser(Long id);

     Set<Role> getRoleForUser(String roles);
}
