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

    @Column(name="content", nullable=false)
    private String content;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity=Profile.class, fetch=FetchType.LAZY)
    @JoinColumn(name="profile_id", nullable=false)
    private Profile profile;
}
