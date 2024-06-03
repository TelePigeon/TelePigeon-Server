package com.telepigeon.server.service.profile;

import com.telepigeon.server.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileUpdater {

    public void updateProfileInfo(
            final Profile profile,
            final String keywords,
            final String gender,
            final String ageRange,
            final String relation
    ) {
        profile.updateProfileInfo(keywords, gender, ageRange, relation);
    }
}
