package com.mike.waf.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class) // listening for some anotations (created etc)
@Table(name = "tb_user")
public class User implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 255, nullable = false, unique = true)
    private String username;

    @Column(length = 2048, nullable = false)
    private String password;

    @Column(length = 1024, nullable = false, unique = true)
    private String email;

    @Column(length = 24, unique = true)
    private String phone;

    @Column(length = 1024)
    private String location;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public User() {}

    public User(String username, String hash_password, String email, String phone, String location) {
        this.username = username;
        this.password = hash_password;
        this.email = email;
        this.phone = phone;
        this.location = location;
    }

}
