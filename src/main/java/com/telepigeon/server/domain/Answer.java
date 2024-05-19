package com.telepigeon.server.domain;

import com.telepigeon.server.dto.answer.request.AnswerCreateDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="answer")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class Answer {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String image;

    private boolean isViewed;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToOne(targetEntity=Question.class, fetch=FetchType.LAZY)
    @JoinColumn(name="question_id")
    private Question question;

    @ManyToOne(targetEntity=Profile.class, fetch=FetchType.LAZY)
    @JoinColumn(name="profile_id")
    private Profile profile;

    public static Answer create(
            AnswerCreateDto answerCreateDto,
            Question question,
            Profile profile
    ){
        return new Answer(
                answerCreateDto.content(),
                answerCreateDto.image(),
                question,
                profile);
    }

    private Answer(
            String content,
            String image,
            Question question,
            Profile profile
    ) {
        this.content = content;
        this.image = image;
        this.question = question;
        this.profile = profile;
        this.isViewed = false;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void updateIsViewed(boolean isViewed){
        this.isViewed = isViewed;
    }
}
