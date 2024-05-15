package com.telepigeon.server.service.hurry;

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
}
