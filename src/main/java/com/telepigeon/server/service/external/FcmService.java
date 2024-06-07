package com.telepigeon.server.service.external;

import com.google.firebase.messaging.*;
import com.telepigeon.server.dto.fcm.FcmMessageDto;
import com.telepigeon.server.exception.BusinessException;
import com.telepigeon.server.exception.code.BusinessErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


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
            throw new BusinessException(BusinessErrorCode.FCM_SERVER_ERROR);
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