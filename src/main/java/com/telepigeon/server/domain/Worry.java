package com.telepigeon.server.domain;

import com.telepigeon.server.dto.fcm.FcmMessageDto;
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

    private Worry(
        final String name,
        final String content,
        final String times,
        final Profile profile
    ) {
        this.name = name;
        this.content = content;
        this.times = times;
        this.profile = profile;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
    }

    public static Worry create(
        final String name,
        final String content,
        final String times,
        final Profile profile
    ) {
       return new Worry(name, content, times, profile);
    }

    public FcmMessageDto toFcmMessageDto() {
        return FcmMessageDto.builder()
                .title(this.name)
                .body(this.content)
                .type("worry")
                .id(this.getProfile().getRoom().getId())
                .clickAction("ROOM_CLICK")
                .build();
    }
}
