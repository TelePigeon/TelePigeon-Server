package com.telepigeon.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="worry")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Worry {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    private String times;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne(targetEntity=Profile.class, fetch=FetchType.LAZY)
    @JoinColumn(name="profile_id")
    private Profile profile;
}
