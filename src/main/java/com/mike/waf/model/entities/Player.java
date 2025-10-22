package com.mike.waf.model.entities;

import com.mike.waf.model.enums.Positions;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class) // listening for some anotations (created etc)
@Table(name = "tb_player")
public class Player implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false)
    private BigInteger age;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Positions favoritePosition;

    @Column(nullable = true)
    private String favoriteTeam;

    @Column(nullable = true)
    private String favoritePlayer;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Positions mostPlayedPosition;

    @Column(nullable = true)
    private BigInteger goals;

    @Column(nullable = true)
    private BigInteger assists;

    @Column(nullable = true)
    private BigInteger rating;

    @Type(ListArrayType.class) // traduction from list to array
    @Column(name = "skill", columnDefinition = "varchar[]")
    private List<String> skill;

    @CreatedDate
    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @LastModifiedDate
    @Column(name = "last_update_date")
    private LocalDateTime lastUpdateDate;

    public Player() {
    }

    public Player(List<String> skill, String favoritePlayer, String favoriteTeam, Positions favoritePosition, String name, BigInteger age) {
        this.skill = skill;
        this.favoritePlayer = favoritePlayer;
        this.favoriteTeam = favoriteTeam;
        this.favoritePosition = favoritePosition;
        this.name = name;
        this.age = age;
    }
}
