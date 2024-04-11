package com.telepigeon.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="worry_time")
@NoArgsConstructor( access = AccessLevel.PROTECTED)
public class WorryTime {
    @Id
    @Column(name="worry_time_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="time", nullable = false)
    private LocalDateTime time;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(targetEntity = Profile.class, fetch = FetchType.LAZY)
    @JoinColumn(name="worry_id", nullable = false)
    private Worry worry;
}
