package com.telepigeon.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="room")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Room {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="code", nullable=false, unique=true)
    private String code;

    @Column(name="created_at", nullable=false)
    private LocalDateTime createdAt;
}
