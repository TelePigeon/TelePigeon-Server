package com.telepigeon.server.service.answer;

import com.telepigeon.server.domain.*;
import com.telepigeon.server.dto.answer.request.AnswerCreateDto;
import com.telepigeon.server.dto.answer.response.QuestionAnswerDto;
import com.telepigeon.server.dto.answer.response.QuestionAnswerListDto;
import com.telepigeon.server.repository.UserRepository;
import com.telepigeon.server.service.hurry.HurryRetriever;
import com.telepigeon.server.service.profile.ProfileRetriever;
import com.telepigeon.server.service.question.QuestionRetriever;
import com.telepigeon.server.service.room.RoomRetriever;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerEditor answerEditor;
    private final AnswerRetriever answerRetriever;
    private final AnswerSaver answerSaver;
    private final ProfileRetriever profileRetriever;
    private final UserRepository userRepository;
    private final RoomRetriever roomRetriever;
    private final QuestionRetriever questionRetriever;
    private final HurryRetriever hurryRetriever;

    public String create(
            final Long userId,
            final Long roomId,
            final Long questionId,
            final AnswerCreateDto answerCreateDto
    ){
        Users user = userRepository.findByIdOrThrow(userId);
        Room room = roomRetriever.findById(roomId);
        Profile profile = profileRetriever.findByUserAndRoom(user, room);
        Question question = questionRetriever.findById(questionId);
        Answer answer = answerSaver.create(
                Answer.create(answerCreateDto, question, profile)
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
        Users user = userRepository.findByIdOrThrow(userId);
        Room room = roomRetriever.findById(roomId);
        if (respondent) {
            Profile opponentProfile = profileRetriever.findByUserNotAndRoom(user, room);
            Answer answer = answerRetriever.findFirstByProfile(opponentProfile);
            answerEditor.updateIsViewed(answer, true);
            return QuestionAnswerListDto.of(
                    Collections.singletonList(
                            QuestionAnswerDto.of(answer.getQuestion(), answer)
                    )
            );
        } else {
            List<QuestionAnswerDto> questionAnswerDtoList =
                    answerRetriever.findAllByRoomAndDate(
                                    room.getProfiles(),
                                    date
                            ).stream()
                            .map(
                                    answer -> QuestionAnswerDto.of(
                                            answer.getQuestion(),
                                            answer
                                    )
                            ).toList();
            return QuestionAnswerListDto.of(questionAnswerDtoList);
        }
    }

    public Integer getRoomState(
            final Users user,
            final Room room
    ) {
        if (!profileRetriever.existsByUserNotAndRoom(user, room))
            return 0;
        if (hurryRetriever.existsByRoomIdAndSenderId(room.getId(), user.getId()))
            return 1;
        if (hurryRetriever.existsByRoomIdAndSenderId(
                room.getId(),
                profileRetriever.findByUserNotAndRoom(user, room).getUser().getId()
            )
        )
            return 2;
        return 0;
    }
}
