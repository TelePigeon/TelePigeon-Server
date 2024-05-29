package com.telepigeon.server.service.question;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Question;
import com.telepigeon.server.domain.Room;
import com.telepigeon.server.domain.User;
import com.telepigeon.server.dto.question.response.GetLastQuestionDto;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import com.telepigeon.server.repository.UserRepository;
import com.telepigeon.server.service.answer.AnswerRetriever;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.room.RoomRetriever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionService {
    private final QuestionSaver questionSaver;
    private final QuestionRetriever questionRetriever;
    private final UserRepository userRepository;
    private final RoomRetriever roomRetriever;
    private final AnswerRetriever answerRetriever;
    private final ProfileRetriever profileRetriever;

    @Scheduled(cron="0 0 12 * * *")
    public void createSchedule(){
        profileRetriever.findAll().forEach(
                this::create
        );
    }

    public Question create(final Profile profile){
//        상대방 토큰 가져오기 위해 사용(나중에 사용)
        Profile receiver = profileRetriever.findByUserNotAndRoom(
                profile.getUser(), profile.getRoom()
        );
        Question prevQuestion = questionRetriever.findFirstByProfile(profile);  //최근 질문 가져오기
        if (
                prevQuestion != null &&
                        !answerRetriever.existsByQuestion(prevQuestion)
        ) { //최근 질문이 있지만 답장이 없는 경우
            throw new BusinessException(BusinessErrorCode.QUESTION_ALREADY_EXISTS);
        }
        String content = createContent();
        Question question = Question.create(content, profile);
        questionSaver.create(question);
        //fcm 나중에 연결 예정
        return question;
    }

    public GetLastQuestionDto findLastQuestion(
            final Long userId,
            final Long roomId
    ) {
        User user = userRepository.findByIdOrThrow(userId);
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

    private String createContent() {  // ai 추후에 추가 예정
        return "밥은 먹었나요?";
    }

}
