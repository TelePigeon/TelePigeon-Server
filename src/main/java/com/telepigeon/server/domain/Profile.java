package com.telepigeon.server.domain;

import com.telepigeon.server.dto.type.AgeRange;
import com.telepigeon.server.dto.type.Relation;
import com.telepigeon.server.dto.type.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
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

    @ManyToOne(targetEntity=Users.class, fetch=FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Users user;

    @ManyToOne(targetEntity=Room.class, fetch=FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;

}
