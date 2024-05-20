package com.telepigeon.server.service.profile;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileSaver {

    private final ProfileRepository profileRepository;

    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }
}
