package com.mike.waf.service;

import com.mike.waf.model.entities.User;
import com.mike.waf.repository.UserRepository;
import com.mike.waf.security.AuthorizationValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IService<User> {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorizationValidatorService authorizationValidatorService;

    @Override
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        var password = user.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void update(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User must have an id");
        }
        authorizationValidatorService.validate(user);
        userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("User must have an id");
        }
        authorizationValidatorService.validate(user);
        userRepository.delete(user);
    }

    @Transactional
    public void deleteByUsername(String username) {
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        authorizationValidatorService.validate(user.get());
        userRepository.deleteByUsername(username);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

}
