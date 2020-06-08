package com.example.SpringClient.web.service;

import com.example.SpringClient.web.dto.UserDTO;
import com.example.SpringClient.web.model.Role;
import com.example.SpringClient.web.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class RestTemplateServiceImpl implements RestTemplateService, UserDetailsService {

    private RestTemplate restTemplate;

    public RestTemplateServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public List<UserDTO> getAllUsers() {
        ResponseEntity<List<UserDTO>> listResponseEntity = restTemplate
                .exchange("/admin/allUsers",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<UserDTO>>() {
                        });
        return listResponseEntity.getBody();
    }

    public UserDTO addUser(UserDTO userDTO) {
        HttpEntity<UserDTO> requestCreate = new HttpEntity<>(userDTO);
        ResponseEntity<UserDTO> userResponseEntity = restTemplate
                .exchange("/admin/addUser",
                        HttpMethod.POST,
                        requestCreate,
                        UserDTO.class);
        return  userResponseEntity.getBody();
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        HttpEntity<UserDTO>  requestBody = new HttpEntity<>(userDTO);
        return  restTemplate
                .exchange("/admin/update",
                        HttpMethod.PUT,
                        requestBody,
                        UserDTO.class)
                .getBody();
    }

    @Override
    public void removeUser(Long id) {
        restTemplate
                .exchange("/admin/remove?id=" + id,
                        HttpMethod.DELETE,
                        null,
                        Void.class);
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

    @Override
    public UserDetails loadUserByUsername(String email) {
        ResponseEntity<User> userDTOResponseEntity = restTemplate.
                exchange("/user/getUser?email=" + email,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<User>() {
                        });
        return userDTOResponseEntity.getBody();
    }
}
