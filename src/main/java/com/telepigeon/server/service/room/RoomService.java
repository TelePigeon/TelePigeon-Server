package com.telepigeon.server.service.room;

import com.telepigeon.server.domain.*;
import com.telepigeon.server.dto.fcm.FcmMessageDto;
import com.telepigeon.server.dto.room.request.RoomCreateDto;
import com.telepigeon.server.dto.room.request.RoomEnterDto;
import com.telepigeon.server.dto.room.response.RoomInfoDto;
import com.telepigeon.server.dto.room.response.RoomListDto;
import com.telepigeon.server.dto.type.FcmContent;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.repository.RoomRepository;
import com.telepigeon.server.service.answer.AnswerRemover;
import com.telepigeon.server.service.answer.AnswerRetriever;
import com.telepigeon.server.service.fcm.FcmService;
import com.telepigeon.server.service.profile.ProfileRemover;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.profile.ProfileSaver;
import com.telepigeon.server.service.question.QuestionRemover;
import com.telepigeon.server.service.question.QuestionRetriever;
import com.telepigeon.server.service.question.QuestionSaver;
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
    private final QuestionSaver questionSaver;
    private final FcmService fcmService;

    @Transactional
    public Room createRoom(final RoomCreateDto roomCreateDto, final Long userId){
        User user = userRetriever.findById(userId);

        String code = createCode();

        Room room = Room.create(roomCreateDto, code);
        Room savedRoom = roomSaver.save(room);

        profileSaver.save(Profile.create(user, savedRoom));

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
    public RoomListDto.RoomDto createRoomDto(final User user, final Room room) {
        int sentence = 3, emotion = 0;
        Profile myProfile = profileRetriever.findByUserAndRoom(user, room);
        if (!profileRetriever.existsByUserNotAndRoom(user, room)){
            return RoomListDto.RoomDto.of(
                    room.getId(),
                    room.getName(),
                    "-",
                    myProfile.getRelation().getContent(),
                    "-",
                    emotion,
                    sentence
            );
        }
        Profile opponentProfile = profileRetriever.findByUserNotAndRoom(user, room);
        Answer myAnswer = answerRetriever.findFirstByProfile(myProfile);
        Answer opponentAnswer = answerRetriever.findFirstByProfile(opponentProfile);

        boolean myState = myAnswer.getContent() != null;
        boolean opponentState = opponentAnswer.getContent() != null;

        // 감정 측정 시 업데이트
        emotion = getEmotion(opponentProfile.getEmotion());

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

        if (profileRetriever.existsByUserAndRoom(user, room)) {
            throw new BusinessException(BusinessErrorCode.REENTER_ERROR);
        }

        if ((profileRetriever.findAll()).size()==2) {
            throw new BusinessException(BusinessErrorCode.ROOM_FULL_ERROR);
        }

        Profile profile = profileSaver.save(Profile.create(user, room));
        Profile receiver = profileRetriever.findByUserNotAndRoom(user, room);
        sendQuestionFirst(receiver, profile);
        sendQuestionFirst(profile, receiver);
        return profile;
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
    private void sendQuestionFirst(Profile profile, Profile receiver){
        String content = "오늘 기분은 어때?(키워드를 설정해 질문을 보낼 수 있어요)";
        questionSaver.create(Question.create(null, content, profile));
        fcmService.send(
                receiver.getUser().getFcmToken(),
                FcmMessageDto.of(
                        FcmContent.QUESTION,
                        profile.getRoom().getId()
                )
        );

    }
    private int getEmotion(Double emotion){
        if (emotion < -0.5)
            return 3;
        else if (emotion < 0)
            return 2;
        else if (emotion < 0.5)
            return 1;
        else
            return 0;
    }
}
