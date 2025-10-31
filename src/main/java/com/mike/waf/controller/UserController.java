package com.mike.waf.controller;

import com.mike.waf.model.DTO.UserDTO;
import com.mike.waf.model.DTO.UserInfoDTO;
import com.mike.waf.model.entities.User;
import com.mike.waf.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public ResponseEntity<UserInfoDTO> findById(@PathVariable String id) {
        var uuid = UUID.fromString(id);

        return userRepository.findById(uuid).map(user -> ResponseEntity.ok(
                new UserInfoDTO(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getLocation()
                )
        )).orElse(ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid UserDTO userDTO) {

        User user = new User();
        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        user.setEmail(userDTO.email());
        user.setPhone(userDTO.phone());
        user.setLocation(userDTO.location());
        var u = userRepository.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(u.getId());
        return ResponseEntity.created(uri).build();

    }

}
