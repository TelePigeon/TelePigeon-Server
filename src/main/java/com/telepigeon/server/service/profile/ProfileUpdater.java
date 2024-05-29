package com.telepigeon.server.service.profile;

import com.telepigeon.server.domain.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileUpdater {

    public void updateProfileInfo(
            Profile profile,
            String keywords,
            String gender,
            String ageRange,
            String relation
    ) {
        profile.updateProfileInfo(keywords, gender, ageRange, relation);
    }
}
