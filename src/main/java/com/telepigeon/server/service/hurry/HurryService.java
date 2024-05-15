package com.telepigeon.server.service.hurry;

import com.telepigeon.server.domain.Hurry;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HurryService {
    private final HurrySaver hurrySaver;
    private final HurryRetriever hurryRetriever;

    public void create(Long roomId){
        Long userId = 1L; // User서비스 구현완료 시 인가코드 삽입 예정
        //Room서비스 구현완료 시 인가코드 삽입 예정
        if (hurryRetriever.existsByRoomIdAndSenderId(roomId, userId))
            throw new BusinessException(BusinessErrorCode.HURRY_ALREADY_EXISTS);
        hurrySaver.save(Hurry.create(roomId, userId));
    }
}
