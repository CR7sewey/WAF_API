package com.mike.waf.controller;

import com.mike.waf.model.DTO.UserDTO;
import com.mike.waf.model.DTO.UserInfoDTO;
import com.mike.waf.model.entities.User;
import com.mike.waf.repository.UserRepository;
import com.mike.waf.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

/*    @GetMapping("/{id}")
    public ResponseEntity<UserInfoDTO> findById(@PathVariable String id) {
        var uuid = UUID.fromString(id);

        return userService.findById(uuid).map(user -> ResponseEntity.ok(
                new UserInfoDTO(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getLocation()
                )
        )).orElse(ResponseEntity.notFound().build());

    }*/

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid UserDTO userDTO) {

        User user = new User();
        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        user.setEmail(userDTO.email());
        user.setPhone(userDTO.phone());
        user.setLocation(userDTO.location());
        user.setRoles(userDTO.roles());
        var u = userService.save(user);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").build(u.getId());
        return ResponseEntity.created(uri).build();

    }

    @GetMapping("/{username}")
    public ResponseEntity<UserInfoDTO> findByUsername(@PathVariable String username) {
        return userService.findByUsername(username).map(user -> ResponseEntity.ok(
                new UserInfoDTO(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getLocation(),
                        user.getRoles()
                )
        )).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable String id, @RequestBody @Valid UserDTO userDTO) {
        var uuid = UUID.fromString(id);

        var userFound = userService.findById(uuid);
        if (userFound.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var user = userFound.get();
        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        user.setEmail(userDTO.email());
        user.setPhone(userDTO.phone());
        user.setLocation(userDTO.location());
        user.setRoles(userDTO.roles());
        userService.update(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<UserInfoDTO>> findAllUsers() {
        List<User> list = userService.findAll();
        List<UserInfoDTO> userInfo = list.stream().map(user -> new UserInfoDTO(
                user.getUsername(),
                user.getEmail(),
                user.getPhone(),
                user.getLocation(),
                user.getRoles()
        )).toList();
        return ResponseEntity.ok().body(userInfo);

    }

}
