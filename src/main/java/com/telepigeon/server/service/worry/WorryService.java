package com.telepigeon.server.service.worry;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.User;
import com.telepigeon.server.domain.Worry;
import com.telepigeon.server.dto.worry.request.WorryCreateDto;
import com.telepigeon.server.dto.worry.response.WorriesDto;
import com.telepigeon.server.exception.ForbiddenException;
import com.telepigeon.server.exception.code.ForbiddenErrorCode;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.room.RoomRetriever;
import com.telepigeon.server.service.user.UserRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorryService {

    private final WorryRetriever worryRetriever;
    private final WorrySaver worrySaver;
    private final WorryRemover worryRemover;
    private final ProfileRetriever profileRetriever;
    private final RoomRetriever roomRetriever;
    private final UserRetriever userRetriever;

    @Transactional(readOnly = true)
    public WorriesDto getWorries(final Long userId, final Long roomId) {
        User user = userRetriever.findById(userId);
        Room room = roomRetriever.findById(roomId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);
        List<Worry> worries = worryRetriever.findAllByProfile(profile);
        return WorriesDto.of(worries);
    }

    @Transactional
    public void createWorry(
            final Long userId,
            final Long roomId,
            final WorryCreateDto request
    ) {
        User user = userRetriever.findById(userId);
        Room room = roomRetriever.findById(roomId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);
        Worry worry = Worry.create(
                request.name(),
                request.content(),
                String.join("|", request.times()),
                profile
        );
        worrySaver.save(worry);
    }

    @Transactional
    public void deleteWorry(Long userId, Long worryId) {
        User user = userRetriever.findById(userId);
        Worry worry = worryRetriever.findById(worryId);
        if (!worry.getProfile().getUser().getId().equals(user.getId()))
            throw new ForbiddenException(ForbiddenErrorCode.FORBIDDEN);
        worryRemover.remove(worry);
    }
}
