package com.telepigeon.server.domain;

import com.telepigeon.server.dto.room.request.RoomCreateDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name="room")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Room {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    private LocalDateTime createdAt;

    @OneToMany(mappedBy="room", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<Profile> profiles;

    @Builder
    private Room(String name, String code, LocalDateTime createdAt) {
        this.name = name;
        this.code = code;
        this.createdAt = createdAt;
    }

    public static Room create(
            RoomCreateDto createDto,
            String code
    ) {
        return Room.builder()
                .name(createDto.name())
                .code(code)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
