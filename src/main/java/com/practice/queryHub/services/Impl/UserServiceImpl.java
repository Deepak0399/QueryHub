package com.practice.queryHub.services.Impl;

import com.practice.queryHub.dtos.UserDTO;
import com.practice.queryHub.exception.UserNotFoundException;
import com.practice.queryHub.model.User;
import com.practice.queryHub.repositories.TagRepository;
import com.practice.queryHub.repositories.UserRepository;
import com.practice.queryHub.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TagRepository tagRepository;

    @Override
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        return userRepository.save(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(UUID userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()) {
            return user;
        }
        throw new UserNotFoundException("User not found");
    }
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

}
