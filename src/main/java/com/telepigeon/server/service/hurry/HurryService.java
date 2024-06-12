package com.telepigeon.server.service.hurry;

import com.telepigeon.server.domain.Hurry;
import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.User;
import com.telepigeon.server.dto.fcm.FcmMessageDto;
import com.telepigeon.server.dto.type.FcmContent;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.service.external.FcmService;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.room.RoomRetriever;
import com.telepigeon.server.service.user.UserRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HurryService {
    private final HurrySaver hurrySaver;
    private final HurryRetriever hurryRetriever;
    private final UserRetriever userRetriever;
    private final RoomRetriever roomRetriever;
    private final ProfileRetriever profileRetriever;
    private final FcmService fcmService;

    @Transactional
    public void create(
            final Long userId,
            final Long roomId
    ){
        User user = userRetriever.findById(userId);
        Room room = roomRetriever.findById(roomId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);
        if (hurryRetriever.existsByProfileId(profile.getId()))
            throw new BusinessException(BusinessErrorCode.HURRY_ALREADY_EXISTS);
        Profile receiver = profileRetriever.findByUserNotAndRoom(user, room);
        if (receiver.isDeleted())
            throw new BusinessException(BusinessErrorCode.PROFILE_DELETED_ERROR);
        hurrySaver.save(Hurry.create(profile.getId().toString()));
        fcmService.send(
                receiver.getUser().getFcmToken(),
                FcmMessageDto.of(
                        FcmContent.HURRY,
                        roomId
                )
        );
    }
}
