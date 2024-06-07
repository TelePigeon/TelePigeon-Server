package com.telepigeon.server.service.external;

import com.telepigeon.server.dto.naverCloud.request.ConfidenceCreateDto;
import com.telepigeon.server.dto.naverCloud.ConfidenceDto;
import com.telepigeon.server.dto.naverCloud.response.NaverCloudDto;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.IllegalArgumentException;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import com.telepigeon.server.exception.code.IllegalArgumentErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Objects;

@Component
@Slf4j
public class NaverCloudService {
    @Value("${spring.data.naver.clientId}")
    private String clientId;
    @Value("${spring.data.naver.clientSecret}")
    private String clientSecret;
    @Value("${spring.data.naver.url}")
    private String url;

    public ConfidenceDto getConfidence(
            final ConfidenceCreateDto confidenceCreateDto
    ){
        RestClient restClient = RestClient.create();
        NaverCloudDto response = restClient.post()
                .uri(url)
                .header("X-NCP-APIGW-API-KEY-ID", clientId)
                .header("X-NCP-APIGW-API-KEY", clientSecret)
                .contentType(MediaType.APPLICATION_JSON)
                .body(confidenceCreateDto)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, (req, res) -> {
                    throw new IllegalArgumentException(IllegalArgumentErrorCode.ILLEGAL_ARGUMENT_CONTENT);
                })
                .onStatus(HttpStatusCode::is5xxServerError, (req, res) -> {
                    throw new BusinessException(BusinessErrorCode.NAVER_CLOUD_SERVER_ERROR);
                })
                .toEntity(NaverCloudDto.class).getBody();
        if (Objects.isNull(response)) {
            throw new BusinessException(BusinessErrorCode.NAVER_CLOUD_SERVER_ERROR);
        }
        return response.document().confidence();
    }
}
