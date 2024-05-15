package com.telepigeon.server.service.hurry;

import com.telepigeon.server.domain.Hurry;
import com.telepigeon.server.repository.HurryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HurrySaver {
    private final HurryRepository hurryRepository;

    public Hurry save(Hurry hurry){
        return hurryRepository.save(hurry);
    }
}
