package com.telepigeon.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="users")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String fcmToken;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Builder
    private Users(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Users create(
            Long id,
            String name
    ) {
        return Users.builder()
                .id(id)
                .name(name)
                .build();
    }

}
