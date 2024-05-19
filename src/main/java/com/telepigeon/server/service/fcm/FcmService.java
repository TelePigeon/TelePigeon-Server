package com.telepigeon.server.service.fcm;

import com.google.firebase.messaging.*;
import com.telepigeon.server.dto.fcm.FcmMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {
    private final FirebaseMessaging firebaseMessaging;
    public String sendByToken(Message message){
        try{
            String response = firebaseMessaging.send(message);
            log.info("FcmResponse : " + response);
            return response;
        } catch (FirebaseMessagingException e){
            log.info("FcmExcept : " + e.getMessage());
            return "fail";
        }
    }
    public Message createMessage(
            String fcmToken,
            FcmMessageDto fcmMessageDto
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
