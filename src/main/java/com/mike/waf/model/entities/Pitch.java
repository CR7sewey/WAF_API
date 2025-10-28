package com.mike.waf.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
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

    @JsonIgnore // problem with LazyLoading - Type definition error: [simple type, class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor]
    @OneToMany(mappedBy = "pitch", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Match> matches;

    @CreatedDate
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @LastModifiedDate
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

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
