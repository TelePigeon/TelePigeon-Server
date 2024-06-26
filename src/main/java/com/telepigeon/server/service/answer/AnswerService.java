package com.telepigeon.server.service.answer;

import com.telepigeon.server.domain.*;
import com.telepigeon.server.dto.answer.RankAnswerDto;
import com.telepigeon.server.dto.answer.request.AnswerCreateDto;
import com.telepigeon.server.dto.answer.response.MonthlyKeywordsDto;
import com.telepigeon.server.dto.answer.response.QuestionAnswerDto;
import com.telepigeon.server.dto.answer.response.QuestionAnswerListDto;
import com.telepigeon.server.dto.fcm.FcmMessageDto;
import com.telepigeon.server.dto.naverCloud.request.ConfidenceCreateDto;
import com.telepigeon.server.dto.naverCloud.ConfidenceDto;
import com.telepigeon.server.dto.room.response.RoomStateDto;
import com.telepigeon.server.dto.type.FcmContent;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.service.external.S3Service;
import com.telepigeon.server.service.external.FcmService;
import com.telepigeon.server.service.external.NaverCloudService;
import com.telepigeon.server.service.hurry.HurryRemover;
import com.telepigeon.server.service.user.UserRetriever;
import com.telepigeon.server.service.hurry.HurryRetriever;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.question.QuestionRetriever;
import com.telepigeon.server.service.room.RoomRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Collections;
import java.util.List;

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
    private final FcmService fcmService;
    private final S3Service s3Service;
    private final HurryRemover hurryRemover;

    private static String ANSWER_S3_UPLOAD_FOLDER = "answer/";

    @Transactional
    public Answer create(
            final Long userId,
            final Long roomId,
            final Long questionId,
            final AnswerCreateDto answerCreateDto
    ) throws IOException {
        User user = userRetriever.findById(userId);
        Room room = roomRetriever.findById(roomId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);
        Profile receiver = profileRetriever.findByUserNotAndRoom(user, room);
        if (receiver.isDeleted())
            throw new BusinessException(BusinessErrorCode.PROFILE_DELETED_ERROR);
        Question question = questionRetriever.findById(questionId);
        ConfidenceDto confidence = naverCloudService.getConfidence(
                ConfidenceCreateDto.of(answerCreateDto.content())
        );
        Double emotion = (confidence.positive() - confidence.negative()) * 0.01;
        Answer answer = answerSaver.create(
                Answer.create(
                        answerCreateDto.content(),
                        uploadImage(answerCreateDto.image()),
                        emotion,
                        question,
                        profile)
        );

        profile.updateEmotion(
                CalculateEmotion(
                        profile.getEmotion(),
                        emotion
                )
        );
        if (profile.getEmotion() < -0.5){
            fcmService.send(
                    receiver.getUser().getFcmToken(),
                    FcmMessageDto.of(
                            FcmContent.EMOTION,
                            roomId
                    )
            );
        }
        if (hurryRetriever.existsByProfileId(receiver.getId()))
            hurryRemover.remove(hurryRetriever.findByProfileId(receiver.getId()));
        fcmService.send(
                receiver.getUser().getFcmToken(),
                FcmMessageDto.of(
                        FcmContent.ANSWER,
                        roomId
                )
        );
        return answer;
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
        if (hurryRetriever.existsByProfileId(myProfile.getId())) //내가 보낸 재촉하기가 있는 경우
            return Pair.of(1, 0L);
        if (hurryRetriever.existsByProfileId(oppoProfile.getId()))   //상대방이 보낸 재촉하기가 있는 경우
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

    @Transactional(readOnly=true)
    public MonthlyKeywordsDto getMonthlyKeywords(
            final Long userId,
            final Long roomId,
            final YearMonth date
    ) {
        User user = userRetriever.findById(userId);
        Room room = roomRetriever.findById(roomId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);
        List<RankAnswerDto> rankAnswerDtoList = answerRetriever.findAvgEmotion(
                profile,
                date
        );
        List<String> positiveKeywords = new java.util.ArrayList<>(List.of("-", "-", "-"));
        List<String> negativeKeywords = new java.util.ArrayList<>(List.of("-", "-", "-"));
        for (int i = 0 ; i < rankAnswerDtoList.size() ; i++){
            if (i == 3)
                break;
            if (rankAnswerDtoList.get(i).emotion() >= 0){
                positiveKeywords.set(i, rankAnswerDtoList.get(i).keyword());
            }
            if (rankAnswerDtoList.get(rankAnswerDtoList.size() - i - 1).emotion() < 0){
                negativeKeywords.set(i, rankAnswerDtoList.get(rankAnswerDtoList.size() - i - 1).keyword());
            }
        }
        return MonthlyKeywordsDto.of(positiveKeywords, negativeKeywords);
    }

    private double CalculateEmotion(
            final Double totEmotion,
            final Double emotion
    ) {
        if (totEmotion == 0.0)
            return emotion;
        return totEmotion * 0.5 + emotion * 0.5;
    }

    private String uploadImage(MultipartFile image) throws IOException {
        if (image != null)
            return s3Service.uploadImage(ANSWER_S3_UPLOAD_FOLDER, image);
        return null;
    }
}
