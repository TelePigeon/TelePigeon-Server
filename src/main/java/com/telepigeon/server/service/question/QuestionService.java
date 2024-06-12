package com.telepigeon.server.service.question;

import com.telepigeon.server.domain.*;
import com.telepigeon.server.dto.fcm.FcmMessageDto;
import com.telepigeon.server.dto.question.response.GetLastQuestionDto;
import com.telepigeon.server.dto.type.FcmContent;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import com.telepigeon.server.service.answer.AnswerRetriever;
import com.telepigeon.server.service.external.FcmService;
import com.telepigeon.server.service.openAi.OpenAiService;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.room.RoomRetriever;
import com.telepigeon.server.service.user.UserRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionSaver questionSaver;
    private final QuestionRetriever questionRetriever;
    private final RoomRetriever roomRetriever;
    private final AnswerRetriever answerRetriever;
    private final ProfileRetriever profileRetriever;
    private final UserRetriever userRetriever;
    private final OpenAiService openAiService;
    private final FcmService fcmService;

    @Transactional
    public Question create(final Profile profile){
        Profile receiver = profileRetriever.findByUserNotAndRoom(
                profile.getUser(), profile.getRoom()
        );
        if (receiver.isDeleted())
            throw new BusinessException(BusinessErrorCode.PROFILE_DELETED_ERROR);
        Question prevQuestion = questionRetriever.findFirstByProfile(profile);  //최근 질문 가져오기
        if (
                prevQuestion != null &&
                        (!answerRetriever.existsByQuestion(prevQuestion) ||
                                prevQuestion.getCreatedAt().toLocalDate().isEqual(LocalDate.now())
                        )
        ) { //최근 질문이 있지만 답장이 없는 경우 혹은 오늘 질문을 보냈을 경우
            throw new BusinessException(BusinessErrorCode.QUESTION_ALREADY_EXISTS);
        }
        if (Objects.equals(profile.getKeywords(), "-")){
            throw new NotFoundException(NotFoundErrorCode.KEYWORD_NOT_FOUND);
        }
        String relation = receiver.getRelation() == null ? "지인" : receiver.getRelation().getContent();
        String keyword = getRandomKeyword(profile);
        String content = openAiService.createQuestion(
                relation,
                keyword
        );
        Question question = questionSaver.create(Question.create(
                keyword,
                content,
                profile
        ));
        fcmService.send(
                receiver.getUser().getFcmToken(),
                FcmMessageDto.of(
                        FcmContent.QUESTION,
                        profile.getRoom().getId()
                )
        );
        return question;
    }

    @Transactional(readOnly=true)
    public GetLastQuestionDto findLastQuestion(
            final Long userId,
            final Long roomId
    ) {
        User user = userRetriever.findById(userId);
        Room room = roomRetriever.findById(roomId);
        Profile profile = profileRetriever.findByUserNotAndRoom(user, room);
        Question question = questionRetriever.findFirstByProfile(profile);
        if (
                question == null ||
                        answerRetriever.existsByQuestion(question)
        ) {
            throw new NotFoundException(NotFoundErrorCode.QUESTION_NOT_FOUND);
        }
        boolean isPenalty = checkPenalty(question);
        return GetLastQuestionDto.of(question, isPenalty);
    }

    private boolean checkPenalty(final Question question) {
        LocalDate now = LocalDate.now();
        LocalDate date = question.getCreatedAt().toLocalDate();
        long days = DAYS.between(date, now);
        return days > 3;
    }

    private String getRandomKeyword(final Profile profile){
        if (Objects.equals(profile.getKeywords(), "-")){
            return "기분";
        }
        List<String> keywords = Arrays.stream(profile.getKeywords().split(",")).toList();
        List<String> alreadyKeywords = questionRetriever.findKeywordsByProfile(
                profile.getId(),
                keywords.size()
        );
        int i = 0;
        for ( ; i < keywords.size(); i++) {
            if (!alreadyKeywords.contains(keywords.get(i)) || i == keywords.size() - 1) {
                break;
            }
        }
        return keywords.get(i);
    }
}
