package com.telepigeon.server.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="users")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String name;

    private String fcmToken;

    private String serialId;

    private String provider;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private User(
                 String name,
                 String email,
                 String serialId,
                 String provider
    ) {
        this.name = name;
        this.email = email;
        this.serialId = serialId;
        this.provider = provider;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public static User create(
            String name,
            String email,
            String serialId,
            String provider) {
        return new User(name, email, serialId, provider);
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
