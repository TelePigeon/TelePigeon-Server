package com.telepigeon.server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void init() {
        try {
            InputStream aboutFirebaseFile = new ClassPathResource("firebase.json").getInputStream();
            FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(aboutFirebaseFile))
                    .build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("FirebaseApp initialized {}", FirebaseApp.getInstance().getName());
            }
        } catch (IOException e) {
            log.error("FirebaseApp initialize failed : {}", e.getMessage());
            throw new NotFoundException(NotFoundErrorCode.FIREBASE_JSON_NOT_FOUND);
        }
    }
}