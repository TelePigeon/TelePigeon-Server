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
        this.keywords = "-";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.emotion = 0.0;
    }

    private Profile(User user, Room room, Relation relation, String keywords) {
        this.user = user;
        this.room = room;
        this.relation = relation;
        this.keywords = keywords;
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

    public static Profile create(
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

    public static Profile create(
            User user,
            Room room,
            Relation relation,
            String keywords
    ) {
        return new Profile(user, room, relation, keywords);
    }

    public void updateEmotion(Double emotion) {
        this.emotion = emotion;
        this.updatedAt = LocalDateTime.now();
    }
  
    public void updateProfileInfo(
            String keywords,
            Gender gender,
            AgeRange ageRange,
            Relation relation
    ) {
        this.keywords = keywords;
        this.gender = gender;
        this.ageRange = ageRange;
        this.relation = relation;
        this.updatedAt = LocalDateTime.now();
    }
}
