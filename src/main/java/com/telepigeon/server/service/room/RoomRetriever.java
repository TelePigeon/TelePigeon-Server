package com.telepigeon.server.service.room;

import com.telepigeon.server.domain.Room;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import com.telepigeon.server.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RoomRetriever {

    private final RoomRepository roomRepository;

    public boolean existsByName(final String name) {
        return roomRepository.existsByName(name);
    }

    public Room findById(final long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(NotFoundErrorCode.ROOM_NOT_FOUND));
    }

    public Room findByCode(final String code) {
        return roomRepository.findByCode(code)
                .orElseThrow(() -> new NotFoundException(NotFoundErrorCode.ROOM_NOT_FOUND));
    }
}
