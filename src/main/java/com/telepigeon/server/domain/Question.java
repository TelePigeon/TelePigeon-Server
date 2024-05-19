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

    public static Question create(String content, Profile profile){
        return new Question(content, profile);
    }

    public Question(String content, Profile profile) {
        this.content = content;
        this.profile = profile;
        this.createdAt = LocalDateTime.now();
    }
}
