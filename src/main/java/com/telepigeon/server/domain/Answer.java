package com.telepigeon.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="answer")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Answer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String image;

    private boolean isViewed;

    private LocalDateTime createdAt;

    @OneToOne(targetEntity=Question.class, fetch=FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;

    @ManyToOne(targetEntity=Profile.class, fetch=FetchType.LAZY)
    @JoinColumn(name="profile_id")
    private Profile profile;
}
