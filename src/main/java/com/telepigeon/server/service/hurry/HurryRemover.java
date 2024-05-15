package com.telepigeon.server.service.hurry;

import com.telepigeon.server.domain.Hurry;
import com.telepigeon.server.repository.HurryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HurryRemover {
    private final HurryRepository hurryRepository;

    public void remove(final Hurry hurry){
        hurryRepository.delete(hurry);
    }
}
