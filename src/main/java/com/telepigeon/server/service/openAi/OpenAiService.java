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
import java.util.Random;

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
            final List<String> keywords
    ) {
        String keyword = keywords.get(new Random().nextInt(keywords.size()));
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
                "너는 나에게 " + relation +
                        "이고, 지금 너는 나와 연락을 주고 받으면서 서로 궁금한 점에 대해 물어보고 있어.\n 너는 나에게" + keyword +
                        "에 대한 궁금증이 생겨서 질문을 하려고 해.\n한글을 사용하고, 따뜻한 어조를 사용하면서 존댓말을 쓰지 말고 질문을 만들어 줘. 다른 말은 하지 말고 질문만 적어줘."));
        return messages;
    }
}
