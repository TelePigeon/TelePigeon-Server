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

    public boolean existsByProfileId(
            final Long profileId
    ){
        return hurryRepository.existsById(profileId.toString());
    }

    public Hurry findByRoomIdAndSenderId(
            final Long profileId
    ){
        return hurryRepository.findById(profileId.toString())
                .orElseThrow(
                () -> new NotFoundException(NotFoundErrorCode.NOT_FOUND_HURRY)
        );
    }
}
