package com.telepigeon.server.service.profile;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.Users;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import com.telepigeon.server.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileRetriever {

    private final ProfileRepository profileRepository;

    public Profile findByUserAndRoom(Users user, Room room) {
        return profileRepository.findByUserAndRoom(user, room)
                .orElseThrow(() -> new NotFoundException(NotFoundErrorCode.PROFILE_NOT_FOUND));
    }

    public Profile findByUserNotAndRoom(Users user, Room room) {
        return profileRepository.findByUserNotAndRoom(user, room)
                .orElseThrow(() -> new NotFoundException(NotFoundErrorCode.PROFILE_NOT_FOUND));
    }

    public List<Profile> findByUserId(final long userId) {
        List<Profile> profileList = profileRepository.findByUserId(userId);
        if (profileList.isEmpty()) {
            throw new NotFoundException(NotFoundErrorCode.PROFILE_NOT_FOUND);
        }
        return profileList;
    }

}
