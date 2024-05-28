package com.telepigeon.server.service.worry;

import com.telepigeon.server.domain.Worry;
import com.telepigeon.server.repository.WorryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorryRemover {

    private final WorryRepository worryRepository;

    public void remove(final Worry worry) {
        worryRepository.delete(worry);
    }
}
