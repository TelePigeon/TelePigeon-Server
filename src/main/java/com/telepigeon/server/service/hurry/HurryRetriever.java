package com.telepigeon.server.service.hurry;

import com.telepigeon.server.domain.Hurry;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import com.telepigeon.server.repository.HurryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HurryRetriever {
    private final HurryRepository hurryRepository;

    public boolean existsByRoomIdAndSenderId(Long roomId, Long senderId){
        return hurryRepository.existsById(roomId + ":" + senderId);
    }

    public Hurry findByRoomIdAndSenderId(Long roomId, Long senderId){
        return hurryRepository.findById(roomId + ":" + senderId).orElseThrow(
                () -> new NotFoundException(NotFoundErrorCode.NOT_FOUND_HURRY)
        );
    }
}
