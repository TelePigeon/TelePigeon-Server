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
    @Value("${openai.system-content}")
    private String roleContent;
    @Value("${openai.assistant-content}")
    private String assistantContent;


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
                roleContent));
        messages.add(MessageDto.of(
                "assistant",
                assistantContent));
        messages.add(MessageDto.of(
                "user",
                "역할은 " + relation + "이고, 주제는 " + keyword + "이야. 만들어 줘."));
        return messages;
    }
}