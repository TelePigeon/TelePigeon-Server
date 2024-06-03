package com.telepigeon.server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.telepigeon.server.exception.NotFoundException;
import com.telepigeon.server.exception.code.NotFoundErrorCode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseConfig {
    @Bean
    public FirebaseApp firebaseApp() {
        if (!FirebaseApp.getApps().isEmpty())
            return FirebaseApp.getInstance();
        try {
            FileInputStream aboutFirebaseFile = new FileInputStream("src/main/resources/firebase.json");
            FirebaseOptions options = FirebaseOptions
                    .builder()
                    .setCredentials(GoogleCredentials.fromStream(aboutFirebaseFile))
                    .build();
            return FirebaseApp.initializeApp(options);
        } catch (IOException e) {
            throw new NotFoundException(NotFoundErrorCode.FIREBASE_JSON_NOT_FOUND);
        }
    }

    @Bean
    public FirebaseMessaging firebaseMessaging(FirebaseApp firebaseApp) {
        return FirebaseMessaging.getInstance(firebaseApp);
    }
}