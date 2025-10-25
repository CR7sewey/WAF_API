package com.mike.waf.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class) // listening for some anotations (created etc)
@Table(name = "tb_team")
public class Team implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = true, length = 30)
    private String name;

    @Column(name = "rating")
    private Integer rating;

    public Team() {}

    public Team(String name, Integer rating) {
        this.name = name;
        this.rating = rating;
    }

}
