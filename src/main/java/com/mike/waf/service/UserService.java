package com.mike.waf.service;

import com.mike.waf.model.entities.User;
import com.mike.waf.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IService<User> {

    private final UserRepository userRepository;
    //private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        var password = user.getPassword();
       // user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    @Override
    public void update(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User must have an id");
        }
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User must have an id");
        }
        userRepository.delete(user);
    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
