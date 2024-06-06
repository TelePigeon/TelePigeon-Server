package com.telepigeon.server.service.worry;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Worry;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import com.telepigeon.server.repository.WorryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class WorryRetriever {

    private final WorryRepository worryRepository;

    public List<Worry> findAllByProfile(final Profile profile) {
        return worryRepository.findAllByProfile(profile);
    }

    public Worry findById(Long worryId) {
        return worryRepository.findById(worryId).orElseThrow(
                ()-> new NotFoundException(NotFoundErrorCode.WORRY_NOT_FOUND)
        );
    }
}
