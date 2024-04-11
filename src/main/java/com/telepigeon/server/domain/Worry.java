package com.telepigeon.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name="worry")
@NoArgsConstructor( access = AccessLevel.PROTECTED)
public class Worry {
    @Id
    @Column(name="worry_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(targetEntity = Profile.class, fetch = FetchType.LAZY)
    @JoinColumn(name="profile_id", nullable = false)
    private Profile profile;

    @OneToMany(mappedBy = "worry", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<WorryTime> worryTimes;
}
