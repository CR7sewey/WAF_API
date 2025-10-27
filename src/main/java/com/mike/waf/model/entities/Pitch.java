package com.mike.waf.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_pitch")
@EntityListeners(AuditingEntityListener.class) // listening for some anotations (created etc)
@Data
public class Pitch implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 1024)
    private String location;

    @Column
    private String photoUrl;

    @Column(nullable = false, length = 24)
    private String type;

    @Column
    private Double length;

    @Column
    private Double width;

    @Column(nullable = false, length = 16)
    private String pitchDimensions;

    public Pitch() {}

    public Pitch(String name, String location, String photoUrl, String type, Double length, Double width, String pitchDimensions) {
        this.name = name;
        this.location = location;
        this.photoUrl = photoUrl;
        this.type = type;
        this.length = length;
        this.width = width;
        this.pitchDimensions = pitchDimensions;
    }

}
