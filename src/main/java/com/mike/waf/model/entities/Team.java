package com.mike.waf.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tb_teams_players",  joinColumns = @JoinColumn(name = "team_id"), inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<Player> players = new ArrayList<>();

    @Column(name = "size", nullable = false)
    private Integer size;

    @CreatedDate
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @LastModifiedDate
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    public Team() {}

    public Team(String name, Integer rating, Integer size) {
        this.name = name;
        this.rating = rating;
        this.size = size;
    }

}
