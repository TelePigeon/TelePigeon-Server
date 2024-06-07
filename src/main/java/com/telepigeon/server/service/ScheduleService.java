package com.telepigeon.server.service;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.service.question.QuestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.telepigeon.server.service.profile.ProfileRetriever;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleService {
    private final QuestionService questionService;
    private final ProfileRetriever profileRetriever;

    @Scheduled(cron="0 0 12 * * *")
    public void createSchedule(){
        for (Profile profile : profileRetriever.findAll()) {
            try {
                questionService.create(profile);
            } catch (Exception e) {
                log.error("Failed to create question for profile: {}", profile.getUser().getName(), e);
            }
        }
    }
}
