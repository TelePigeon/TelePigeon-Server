package com.telepigeon.server.service.room;

import com.telepigeon.server.domain.Answer;
import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.Users;
import com.telepigeon.server.dto.room.request.RoomCreateDto;
import com.telepigeon.server.dto.room.response.RoomInfoDto;
import com.telepigeon.server.dto.room.response.RoomListDto;
import com.telepigeon.server.repository.RoomRepository;
import com.telepigeon.server.service.answer.AnswerRetriever;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.profile.ProfileSaver;
import com.telepigeon.server.service.user.UserRetriever;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomSaver roomSaver;
    private final ProfileSaver profileSaver;
    private final UserRetriever userRetriever;
    private final ProfileRetriever profileRetriever;
    private final AnswerRetriever answerRetriever;
    private final RoomRetriever roomRetriever;

    @Transactional
    public Room createRoom(final RoomCreateDto roomCreateDto, final Long userId){
        Users user = userRetriever.findById(userId);

        String code = createCode();

        Room room = Room.create(roomCreateDto, code);
        Room savedRoom = roomSaver.save(room);

        Profile profile = Profile.create(user, savedRoom);
        Profile savedProfile = profileSaver.save(profile);

        return savedRoom;
    }

    private String createCode() {
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        String code;

        do {
            StringBuilder codeBuilder = new StringBuilder();
            for (int i = 0; i < 10; i++) {
                int index = random.nextInt(characters.length());
                codeBuilder.append(characters.charAt(index));
            }
            code = codeBuilder.toString();
        } while (roomRepository.existsByCode(code));


        return code;
    }

    public List<RoomListDto> getAllRooms(final Long userId) {
        Users user = userRetriever.findById(userId);
        List<Profile> profileList = profileRetriever.findByUserId(userId);
        List<Room> roomList = profileList.stream().map(Profile::getRoom).toList();

        return roomList.stream().map(room -> {
            Profile myProfile = profileRetriever.findByUserAndRoom(user, room);
            Profile opponentProfile = profileRetriever.findByUserNotAndRoom(user, room);
            Answer myAnswer = answerRetriever.findFirstByProfile(myProfile);
            Answer opponentAnswer = answerRetriever.findFirstByProfile(opponentProfile);

            return RoomListDto.of(room, myProfile, opponentProfile, myAnswer, opponentAnswer);
        }).collect(Collectors.toList());

    }

    public RoomInfoDto getRoomInfo(final Long roomId) {
        Room room = roomRetriever.findById(roomId);

        return RoomInfoDto.of(room);
    }
}
