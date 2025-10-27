package com.mike.waf.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_match")
@EntityListeners(AuditingEntityListener.class) // listening for some anotations (created etc)
public class Match implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column()
    private String result;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "GMT")
    private Instant date;

    @Column(name = "team1_id")
    private UUID team1;

    @Column(name = "team2_id")
    private UUID team2;

    @Column(nullable = false)
    private Boolean status;

    @CreatedDate
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @LastModifiedDate
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    public Match() {}

    public Match(String result, Instant date, UUID team1, UUID team2, Boolean status) {
        this.result = result;
        this.date = date;
        this.team1 = team1;
        this.team2 = team2;
        this.status = status;
    }

}
