package com.mike.waf.service;

import com.mike.waf.model.entities.Player;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface IService<T> {

    Optional<T> findById(UUID id);
    T save(Player player);
    Page<Player> findAllByNameAndRating(String name, Integer rating, Integer pageNumber, Integer pageSize);
    void update(Player player);
    void delete(Player player);

}
