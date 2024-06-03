package com.telepigeon.server.service.answer;

import com.telepigeon.server.domain.*;
import com.telepigeon.server.dto.answer.request.AnswerCreateDto;
import com.telepigeon.server.dto.answer.response.QuestionAnswerDto;
import com.telepigeon.server.dto.answer.response.QuestionAnswerListDto;
import com.telepigeon.server.dto.naverCloud.request.ConfidenceCreateDto;
import com.telepigeon.server.dto.naverCloud.ConfidenceDto;
import com.telepigeon.server.dto.room.response.RoomStateDto;
import com.telepigeon.server.service.naverCloud.NaverCloudService;
import com.telepigeon.server.service.user.UserRetriever;
import com.telepigeon.server.service.hurry.HurryRetriever;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.question.QuestionRetriever;
import com.telepigeon.server.service.room.RoomRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerEditor answerEditor;
    private final AnswerRetriever answerRetriever;
    private final AnswerSaver answerSaver;
    private final ProfileRetriever profileRetriever;
    private final UserRetriever userRetriever;
    private final RoomRetriever roomRetriever;
    private final QuestionRetriever questionRetriever;
    private final HurryRetriever hurryRetriever;
    private final NaverCloudService naverCloudService;

    @Transactional
    public String create(
            final Long userId,
            final Long roomId,
            final Long questionId,
            final AnswerCreateDto answerCreateDto
    ){
        User user = userRetriever.findById(userId);
        Room room = roomRetriever.findById(roomId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);
        Question question = questionRetriever.findById(questionId);
        Answer answer = answerSaver.create(
                Answer.create(answerCreateDto, question, profile)
        );
        profile.updateEmotion(
                CalculateEmotion(
                        profile.getEmotion(),
                        naverCloudService.getConfidence(
                                ConfidenceCreateDto.of(answer.getContent())
                        )
                )
        );
        return answer.getId().toString();
    }

    @Transactional
    public QuestionAnswerListDto getAllQuestionAndAnswerByDate(
            final Long userId,
            final Long roomId,
            final LocalDate date,
            final boolean respondent
    ) {
        User user = userRetriever.findById(userId);
        Room room = roomRetriever.findById(roomId);
        if (respondent) {
            Profile opponentProfile = profileRetriever.findByUserNotAndRoom(user, room);
            Answer answer = answerRetriever.findFirstByProfile(opponentProfile);
            answerEditor.updateIsViewed(answer, true);  //isViewed 업데이트
            return QuestionAnswerListDto.of(
                    Collections.singletonList(
                            QuestionAnswerDto.of(answer.getQuestion(), answer)
                    )
            );
        } else {
            return QuestionAnswerListDto.of(
                    answerRetriever.findAllByRoomAndDate(
                                    room.getProfiles(),
                                    date
                            ).stream()
                            .map(
                                    answer -> QuestionAnswerDto.of(
                                            answer.getQuestion(),
                                            answer
                                    )
                            ).toList()
            );
        }
    }

    @Transactional(readOnly=true)
    public RoomStateDto getRoomState(
            final Long userId,
            final Long roomId
    ) {
        User user = userRetriever.findById(userId);
        Room room = roomRetriever.findById(roomId);
        Pair<Integer, Long> numbers = getRoomStateNumber(user, room);
        return RoomStateDto.of(room.getName(), numbers.getFirst(), numbers.getSecond());
    }

    @Transactional(readOnly=true)
    public Pair<Integer, Long> getRoomStateNumber(
            final User user,
            final Room room
    ) {
        if (!profileRetriever.existsByUserNotAndRoom(user, room)) //상대방이 아직 없을 경우
            return Pair.of(0, 0L);
        Profile myProfile = profileRetriever.findByUserAndRoom(user, room);
        Profile oppoProfile = profileRetriever.findByUserNotAndRoom(user, room);
        if (!questionRetriever.existsByProfile(oppoProfile) &&
                !questionRetriever.existsByProfile(myProfile)
            ) //나, 상대방 질문 모두 안 만들어졌을 경우
            return Pair.of(7, 0L);
        if (hurryRetriever.existsByRoomIdAndSenderId(room.getId(), user.getId())) //내가 보낸 재촉하기가 있는 경우
            return Pair.of(1, 0L);
        if (hurryRetriever.existsByRoomIdAndSenderId(
                room.getId(),
                oppoProfile.getUser().getId()
            )
        )   //상대방이 보낸 재촉하기가 있는 경우
            return Pair.of(2, 0L);
        if (questionRetriever.existsByProfile(oppoProfile)) { //상대방의 질문이 있는지 확인
            Question oppoQuestion = questionRetriever.findFirstByProfile(oppoProfile);
            if (answerRetriever.existsByQuestion(oppoQuestion)) { //내 답변이 있을 경우
                if (questionRetriever.existsByProfile(myProfile)) { //내 질문이 있는지 확인
                    Question myQuestion = questionRetriever.findFirstByProfile(myProfile);
                    if (answerRetriever.existsByQuestion(myQuestion)) { //상대 답변이 있을 경우
                        Answer oppoAnswer = answerRetriever.findByQuestion(myQuestion);
                        if (!oppoAnswer.isViewed()) //확인한 답변이 아닌 경우
                            return Pair.of(3, 0L);
                        else    //확인한 답변일 경우
                            return Pair.of(4, 0L);
                    } else  //상대 답변이 없을 경우
                        return Pair.of(5, 0L);
                }
            }
        }
        LocalDate now = LocalDate.now();
        LocalDate date = questionRetriever
                .findFirstByProfile(oppoProfile)
                .getCreatedAt()
                .toLocalDate();
        Long days = DAYS.between(date, now);
        return Pair.of(6, days);
    }
    private double CalculateEmotion(
            final Double emotion,
            final ConfidenceDto confidenceDto
    ) {
        double confidence = (confidenceDto.positive() - confidenceDto.negative()) * 0.001;
        return emotion * 0.9 + confidence;
    }
}
