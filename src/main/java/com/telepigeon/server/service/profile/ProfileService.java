package com.telepigeon.server.service.profile;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.User;
import com.telepigeon.server.dto.profile.request.ProfileDto;
import com.telepigeon.server.dto.profile.response.ProfileInfoDto;
import com.telepigeon.server.dto.profile.response.ProfileKeywordsDto;
import com.telepigeon.server.dto.type.AgeRange;
import com.telepigeon.server.dto.type.Gender;
import com.telepigeon.server.dto.type.Relation;
import com.telepigeon.server.service.room.RoomRetriever;
import com.telepigeon.server.service.user.UserRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final RoomRetriever roomRetriever;
    private final UserRetriever userRetriever;
    private final ProfileRetriever profileRetriever;
    private final ProfileUpdater profileUpdater;

    @Transactional(readOnly = true)
    public ProfileKeywordsDto getProfileKeywords(final Long roomId, final Long userId) {
        Room room = roomRetriever.findById(roomId);
        User user = userRetriever.findById(userId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);

        List<String> keywords = Arrays.asList(profile.getKeywords().split(","));
        return new ProfileKeywordsDto(keywords);
    }

    @Transactional(readOnly = true)
    public ProfileInfoDto getProfileExtraInfo(final Long roomId, final Long userId) {
        Room room = roomRetriever.findById(roomId);
        User user = userRetriever.findById(userId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);

        String gender = profile.getGender()!=null?profile.getGender().getContent():"-";
        String ageRange = profile.getAgeRange()!=null?profile.getAgeRange().getContent():"-";
        String relation = profile.getRelation()!=null?profile.getRelation().getContent():"-";

        return new ProfileInfoDto(
                gender,
                ageRange,
                relation
        );
    }

    @Transactional
    public void updateProfileInfo(
            final Long roomId,
            final Long userId,
            final ProfileDto profileDto
    ) {
        Room room = roomRetriever.findById(roomId);
        User user = userRetriever.findById(userId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);

        String keywords = "";
        if(profileDto.keywords()!=null) {
            keywords = String.join(",", profileDto.keywords());
        }

        profileUpdater.updateProfileInfo(
                profile,
                keywords,
                Gender.fromContent(profileDto.gender()),
                AgeRange.fromContent(profileDto.ageRange()),
                Relation.fromContent(profileDto.relation())
        );
    }
}
