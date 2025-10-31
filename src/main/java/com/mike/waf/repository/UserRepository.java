package com.mike.waf.repository;

import com.mike.waf.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    void deleteByUsername(String username);
    Optional<User> findByUsername(String username);
}
