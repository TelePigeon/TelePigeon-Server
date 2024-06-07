package com.telepigeon.server.service.fcm;

import com.google.firebase.messaging.*;
import com.telepigeon.server.dto.fcm.FcmMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class FcmService {

    public void send(
            final String fcmToken,
            final FcmMessageDto fcmMessageDto
    ){
        Message message = createMessage(fcmToken, fcmMessageDto);
        try{
            FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e){
            log.error("Failed to send message. fcm Token : {}", fcmToken);
        }
    }

    private Message createMessage(
            final String fcmToken,
            final FcmMessageDto fcmMessageDto
    ){
        return Message.builder()
                .setToken(fcmToken)
                .setNotification(
                        Notification.builder()
                                .setTitle(fcmMessageDto.title())
                                .setBody(fcmMessageDto.body())
                                .build()
                )
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setNotification(
                                        AndroidNotification.builder()
                                                .setTitle(fcmMessageDto.title())
                                                .setBody(fcmMessageDto.body())
                                                .setClickAction(fcmMessageDto.clickAction())
                                                .build()
                                )
                                .build()
                )
                .putData("type", fcmMessageDto.type())
                .putData("id", fcmMessageDto.id().toString())
                .build();
    }
}
