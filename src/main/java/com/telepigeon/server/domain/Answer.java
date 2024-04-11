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

    @Column(name="content", nullable=false)
    private String content;

    @Column(name="image")
    private String image;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @OneToOne(targetEntity=Question.class, fetch=FetchType.LAZY)
    @JoinColumn(name="question_id", nullable=false)
    private Question question;

    @ManyToOne(targetEntity=Profile.class, fetch=FetchType.LAZY)
    @JoinColumn(name="profile_id", nullable=false)
    private Profile profile;
}
