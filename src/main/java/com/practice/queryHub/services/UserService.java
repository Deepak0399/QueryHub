package com.practice.queryHub.services;

import com.practice.queryHub.dtos.UserDTO;
import com.practice.queryHub.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface UserService {
    User createUser(UserDTO userDTO);
    List<User> getAllUsers();
    Optional<User> getUserById(UUID userId);
    void deleteUser(UUID id);


}
