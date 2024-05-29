package com.telepigeon.server.service.profile;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.Users;
import com.telepigeon.server.dto.profile.request.ProfileDto;
import com.telepigeon.server.dto.profile.response.ProfileInfoDto;
import com.telepigeon.server.dto.profile.response.ProfileKeywordDto;
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
    private final ProfileSaver profileSaver;

    @Transactional(readOnly = true)
    public ProfileKeywordDto getProfileKeyword(final Long roomId, final Long userId) {
        Room room = roomRetriever.findById(roomId);
        Users user = userRetriever.findById(userId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);

        List<String> keywords = Arrays.asList(profile.getKeywords().split(","));
        return new ProfileKeywordDto(keywords);
    }

    @Transactional(readOnly = true)
    public ProfileInfoDto getProfileExtraInfo(final Long roomId, final Long userId) {
        Room room = roomRetriever.findById(roomId);
        Users user = userRetriever.findById(userId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);

        return new ProfileInfoDto(
                profile.getGender().getContent(),
                profile.getAgeRange().getContent(),
                profile.getRelation().getContent()
        );
    }

    @Transactional
    public Profile updateProfileInfo(
            final Long roomId,
            final Long userId,
            final ProfileDto profileDto
    ) {
        Room room = roomRetriever.findById(roomId);
        Users user = userRetriever.findById(userId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);

        String keywords = "";
        if(profileDto.keywords()!=null) {
            keywords = String.join(",", profileDto.keywords());
        };

        profileUpdater.updateProfileInfo(
                profile,
                keywords,
                profileDto.gender(),
                profileDto.ageRange(),
                profileDto.relation()
        );

        return profileSaver.save(profile);
    }
}
