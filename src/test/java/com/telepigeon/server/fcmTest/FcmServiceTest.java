package com.telepigeon.server.fcmTest;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.telepigeon.server.dto.fcm.FcmMessageDto;
import com.telepigeon.server.service.fcm.FcmService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class FcmServiceTest {
    @MockBean
    private FirebaseMessaging firebaseMessaging;

    @Test
    @DisplayName("Fcm메시지 전송 테스트")
    public void checkSendFcm() throws FirebaseMessagingException {
        FcmService fcmService = new FcmService(firebaseMessaging);
        FcmMessageDto fcmMessage = FcmMessageDto.of("hi", "i'm minwoo", "GO_ROOM", "ANSWER", 1L);
        Message message = fcmService.createMessage("token", fcmMessage);
        Mockito.doReturn("success").when(firebaseMessaging).send(message);
        String result = fcmService.sendByToken(message);
        Assertions.assertEquals("success", result);
    }
}
