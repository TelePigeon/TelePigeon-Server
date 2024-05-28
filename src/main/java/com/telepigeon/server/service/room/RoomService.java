package com.telepigeon.server.service.room;

import com.telepigeon.server.domain.*;
import com.telepigeon.server.dto.room.request.RoomCreateDto;
import com.telepigeon.server.dto.room.request.RoomEnterDto;
import com.telepigeon.server.dto.room.response.RoomInfoDto;
import com.telepigeon.server.dto.room.response.RoomListDto;
import com.telepigeon.server.repository.RoomRepository;
import com.telepigeon.server.service.answer.AnswerRemover;
import com.telepigeon.server.service.answer.AnswerRetriever;
import com.telepigeon.server.service.profile.ProfileRemover;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.profile.ProfileSaver;
import com.telepigeon.server.service.question.QuestionRemover;
import com.telepigeon.server.service.question.QuestionRetriever;
import com.telepigeon.server.service.user.UserRetriever;
import com.telepigeon.server.service.worry.WorryRemover;
import com.telepigeon.server.service.worry.WorryRetriever;
import org.springframework.transaction.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

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
    private final QuestionRetriever questionRetriever;
    private final WorryRetriever worryRetriever;
    private final ProfileRemover profileRemover;
    private final QuestionRemover questionRemover;
    private final WorryRemover worryRemover;
    private final AnswerRemover answerRemover;

    @Transactional
    public Room createRoom(final RoomCreateDto roomCreateDto, final Long userId){
        User user = userRetriever.findById(userId);

        String code = createCode();

        Room room = Room.create(roomCreateDto, code);
        Room savedRoom = roomSaver.save(room);

        Profile profile = Profile.create(user, savedRoom);
        Profile savedProfile = profileSaver.save(profile);

        return savedRoom;
    }

    @Transactional(readOnly = true)
    public RoomListDto getAllRooms(final Long userId) {
        User user = userRetriever.findById(userId);
        List<Profile> profileList = profileRetriever.findByUserId(userId);
        List<Room> roomList = profileList.stream().map(Profile::getRoom).toList();

        List<RoomListDto.RoomDto> roomDtos = roomList.stream()
                .map(room -> createRoomDto(user, room))
                .toList();

        return RoomListDto.of(roomDtos);
    }

    @Transactional(readOnly = true)
    public RoomListDto.RoomDto createRoomDto(User user, Room room) {
        Profile myProfile = profileRetriever.findByUserAndRoom(user, room);
        Profile opponentProfile = profileRetriever.findByUserNotAndRoom(user, room);
        Answer myAnswer = answerRetriever.findFirstByProfile(myProfile);
        Answer opponentAnswer = answerRetriever.findFirstByProfile(opponentProfile);

        boolean myState = myAnswer.getContent() != null;
        boolean opponentState = opponentAnswer.getContent() != null;

        // 감정 측정 시 업데이트
        int emotion = 0;

        int sentence;
        if (myState && opponentState) {
            sentence = 0;
        } else if (myState) {
            sentence = 1;
        } else {
            sentence = 2;
        }

        return RoomListDto.RoomDto.of(
                room.getId(),
                room.getName(),
                opponentProfile.getUser().getName(),
                myProfile.getRelation().getContent(),
                opponentProfile.getRelation().getContent(),
                emotion,
                sentence
        );
    }

    @Transactional(readOnly = true)
    public RoomInfoDto getRoomInfo(final Long roomId) {
        Room room = roomRetriever.findById(roomId);

        return RoomInfoDto.of(room);
    }

    @Transactional
    public Profile enterRoom(final RoomEnterDto roomEnterDto, final Long userId) {
        User user = userRetriever.findById(userId);
        Room room = roomRetriever.findByCode(roomEnterDto.code());

        Profile profile = Profile.create(user, room);
        return profileSaver.save(profile);
    }

    @Transactional
    public Room deleteRoom(final Long roomId, final Long userId) {
        Room room = roomRetriever.findById(roomId);
        User user = userRetriever.findById(userId);

        Profile profile = profileRetriever.findByUserAndRoom(user, room);
        List<Answer> answerList = answerRetriever.findAllByProfile(profile);
        List<Question> questionList = questionRetriever.findAllByProfile(profile);
        List<Worry> worryList = worryRetriever.findAllByProfile(profile);

        profileRemover.remove(profile);
        answerList.forEach(answerRemover::remove);
        questionList.forEach(questionRemover::remove);
        worryList.forEach(worryRemover::remove);

        return room;
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

}
