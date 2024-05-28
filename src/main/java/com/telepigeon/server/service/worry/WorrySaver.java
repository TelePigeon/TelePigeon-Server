package com.telepigeon.server.service.worry;

import com.telepigeon.server.domain.Worry;
import com.telepigeon.server.repository.WorryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorrySaver {

    private final WorryRepository worryRepository;

    public Worry create(final Worry worry){
        return worryRepository.save(worry);
    }
}
