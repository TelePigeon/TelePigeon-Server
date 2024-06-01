package com.telepigeon.server.service.hurry;

import com.telepigeon.server.domain.Hurry;
import com.telepigeon.server.domain.User;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.service.user.UserRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HurryService {
    private final HurrySaver hurrySaver;
    private final HurryRetriever hurryRetriever;
    private final UserRetriever userRetriever;
    public void create(
            final Long userId,
            final Long roomId
    ){
        User user = userRetriever.findById(userId);
        if (hurryRetriever.existsByRoomIdAndSenderId(roomId, user.getId()))
            throw new BusinessException(BusinessErrorCode.HURRY_ALREADY_EXISTS);
        hurrySaver.save(Hurry.create(roomId, userId));
    }
}
