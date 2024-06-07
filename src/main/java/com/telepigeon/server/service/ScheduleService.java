package com.telepigeon.server.service;

import com.telepigeon.server.domain.Profile;
import com.telepigeon.server.domain.Worry;
import com.telepigeon.server.service.external.FcmService;
import com.telepigeon.server.service.question.QuestionService;
import com.telepigeon.server.service.worry.WorryRetriever;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.telepigeon.server.service.profile.ProfileRetriever;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleService {
    private final QuestionService questionService;
    private final ProfileRetriever profileRetriever;
    private final WorryRetriever worryRetriever;
    private final FcmService fcmService;

    @Scheduled(cron="0 0 12 * * *")
    public void createSchedule() {
        for (Profile profile : profileRetriever.findAll()) {
            try {
                questionService.create(profile);
            } catch (Exception e) {
                log.error("Failed to create question for profile {}", profile.getId());
            }
        }
    }

    @Scheduled(cron="0 0 * * * *")
    public void sendWorries() {
        String currentHour = LocalTime.now().format(DateTimeFormatter.ofPattern("HH시"));
        List<Worry> worries = worryRetriever.findAllByTime(currentHour);
        for (Worry worry : worries) {
            try {
                sendWorry(worry);
            } catch (Exception e) {
                log.error("Failed to send worry for worry {}", worry.getId());
            }
        }
    }

    private void sendWorry(Worry worry) {
        Profile profile = profileRetriever.findByOpponentProfile(worry.getProfile());
        fcmService.send(
                profile.getUser().getFcmToken(),
                worry.toFcmMessageDto()
                );
    }
}
