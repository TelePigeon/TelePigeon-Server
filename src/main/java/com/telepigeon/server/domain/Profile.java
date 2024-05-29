package com.telepigeon.server.domain;

import com.telepigeon.server.dto.type.AgeRange;
import com.telepigeon.server.dto.type.Relation;
import com.telepigeon.server.dto.type.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="profile")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Profile {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private Double emotion;

    private Boolean isSenior;

    private String keywords;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private AgeRange ageRange;

    @Enumerated(EnumType.STRING)
    private Relation relation;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(targetEntity= User.class, fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(targetEntity=Room.class, fetch=FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;

    @Builder
    private Profile(User user, Room room, Relation relation) {
        this.user = user;
        this.room = room;
        this.relation = relation;
    }

    public static Profile create(
            User user,
            Room room
    ) {
        return Profile.builder()
                .user(user)
                .room(room)
                .build();
    }

    public static Profile createTest(
            User user,
            Room room,
            Relation relation
    ) {
        return Profile.builder()
                .user(user)
                .room(room)
                .relation(relation)
                .build();
    }
}
