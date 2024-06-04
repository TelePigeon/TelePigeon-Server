package com.telepigeon.server.service.openAi;

import com.telepigeon.server.dto.openAi.MessageDto;
import com.telepigeon.server.dto.openAi.request.QuestionCreateDto;
import com.telepigeon.server.dto.openAi.response.OpenAiResponseDto;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class OpenAiService {
    @Value("${openai.model}")
    private String model;
    @Value("${openai.api.key}")
    private String apiKey;
    @Value("${openai.api.url}")
    private String url;

    public String createQuestion(
            final String relation,
            final String keyword
    ) {
        QuestionCreateDto questionCreateDto = QuestionCreateDto.of(model, createPrompt(relation, keyword));
        RestClient restClient = RestClient.create();
        OpenAiResponseDto response = restClient.post()
                .uri(url)
                .header("Authorization", "Bearer " + apiKey)
                .contentType(MediaType.APPLICATION_JSON)
                .body(questionCreateDto)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    throw new BusinessException(BusinessErrorCode.OPEN_AI_SERVER_ERROR);
                })
                .toEntity(OpenAiResponseDto.class).getBody();
        if (Objects.isNull(response)) {
            throw new BusinessException(BusinessErrorCode.OPEN_AI_SERVER_ERROR);
        }
        return response.choices().get(0).message().content();
    }

    private List<MessageDto> createPrompt(
            final String relation,
            final String keyword
    ) {
        List<MessageDto> messages = new ArrayList<>();
        //프롬프팅은 같이 찾아보면서 해야 할 것 같음. 좀 오락가락하는 경향이 많음
        messages.add(MessageDto.of(
                "system",
                """
                        너는 훌륭한 질문 생성기야.
                        내가 너에게 역할과 주제를 알려주면 너는 역할의 입장에서 주제에 대해 물어 보고 싶은 질문을 생성해 주는 거지.
                        그럼 지금 부터 시작해 볼까? 질문이 잘 생성 되면 너에게 1000만 달러 팁을 줄게.
                        """));
        messages.add(MessageDto.of(
                "assistant",
                """
                        역할은 나와의 관계를 나타 내는 말이야.예를 들어, '친구', '가족', '연인' 등이 있어.
                        그리고 주제는 질문의 주제를 나타 내는 말이야.'건강', '음식', '취미' 등이 있어.
                        예를 들어, 역할이 '엄마' 이고 주제가 '건강' 이라면, 너는 '요즘 건강은 어때?', '건강을 지키기 위해 어떤 노력을 하고 있니?' 와 같은 질문을 만들어 주면 돼.
                        단, 질문은 하나만 만들고 '따뜻한 어조' 와 함께 '반말 체' 로 만들어 줘. 다른 말은 하지 말고 질문만 만들어 주면 돼.
                        """));
        messages.add(MessageDto.of(
                "user",
                "역할은 " + relation + "이고, 주제는 " + keyword + "이야. 만들어 줘."));
        return messages;
    }
}