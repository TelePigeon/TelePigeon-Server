package com.telepigeon.server.service.profile;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.dto.type.AgeRange;
import com.telepigeon.server.dto.type.Gender;
import com.telepigeon.server.dto.type.Relation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileUpdater {

    public void updateProfileInfo(
            final Profile profile,
            final String keywords,
            final Gender gender,
            final AgeRange ageRange,
            final Relation relation
    ) {
        profile.updateProfileInfo(keywords, gender, ageRange, relation);
    }
}
