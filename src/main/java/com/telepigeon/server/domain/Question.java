package com.telepigeon.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="question")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Question {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String content;

    private LocalDateTime createdAt;

    @ManyToOne(targetEntity=Profile.class, fetch=FetchType.LAZY)
    @JoinColumn(name="profile_id")
    private Profile profile;
}
