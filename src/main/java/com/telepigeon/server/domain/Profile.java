package com.telepigeon.server.domain;

import com.telepigeon.server.dto.type.AgeRange;
import com.telepigeon.server.dto.type.Relation;
import com.telepigeon.server.dto.type.Sex;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="profile")
@NoArgsConstructor( access = AccessLevel.PROTECTED)
public class Profile {
    @Id
    @Column(name="profile_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="emotion", nullable = false, columnDefinition = "double default 0")
    private Double emotion;

    @Column(name="is_senior", nullable = false, columnDefinition = "boolean default false")
    private Boolean isSenior;

    @Column(name="keywords")
    private String keywords;

    @Column(name="sex")
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(name="age_range")
    @Enumerated(EnumType.STRING)
    private AgeRange ageRange;

    @Column(name="relation")
    @Enumerated(EnumType.STRING)
    private Relation relation;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(targetEntity = Users.class, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private Users user;

    @ManyToOne(targetEntity = Room.class, fetch = FetchType.LAZY)
    @JoinColumn(name="room_id", nullable = false)
    private Room room;

}
