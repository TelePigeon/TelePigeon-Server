package com.telepigeon.server.service;

import com.telepigeon.server.dto.naverCloud.ConfidenceCreateDto;
import com.telepigeon.server.dto.naverCloud.ConfidenceDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Objects;

@Component
public class NaverCloudService {
    @Value("${spring.data.naver.clientId}")
    private String clientId;
    @Value("${spring.data.naver.clientSecret}")
    private String clientSecret;
    @Value("${spring.data.naver.url}")
    private String url;

    public ConfidenceDto getConfidence(ConfidenceCreateDto confidenceCreateDto){
        RestClient restClient = RestClient.create();
        // Response -> Nullable False
        Map response = Objects.requireNonNull(restClient.post()
                .uri(url)
                .header("X-NCP-APIGW-API-KEY-ID", clientId)
                .header("X-NCP-APIGW-API-KEY", clientSecret)
                .contentType(MediaType.APPLICATION_JSON)
                .body(confidenceCreateDto)
                .retrieve()
                .toEntity(Map.class).getBody()
        );

        Map<String, Object> document = (Map<String, Object>) response.get("document");
        Map<String, Object> confidence = (Map<String, Object>) document.get("confidence");

        Double neutral = (Double) confidence.get("neutral");
        Double positive = (Double) confidence.get("positive");
        Double negative = (Double) confidence.get("negative");

        return ConfidenceDto.of(neutral, positive, negative);
    }
}
