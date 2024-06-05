package com.telepigeon.server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Slf4j
@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void init() {
        if (!FirebaseApp.getApps().isEmpty()) {
            log.info("FirebaseApp already initialized");
            return;
        }
        try {
            FileInputStream aboutFirebaseFile = new FileInputStream("src/main/resources/firebase.json");
            FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(aboutFirebaseFile))
                    .build();
            log.info("FirebaseApp initialize");
            FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            log.error("FirebaseApp initialize failed : {}", e.getMessage());
            throw new NotFoundException(NotFoundErrorCode.FIREBASE_JSON_NOT_FOUND);
        }
    }
}