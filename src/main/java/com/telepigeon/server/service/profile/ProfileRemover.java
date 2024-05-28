package com.telepigeon.server.service.profile;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileRemover {
    private final ProfileRepository profileRepository;

    public void remove(final Profile profile) {
        profileRepository.delete(profile);
    }
}
