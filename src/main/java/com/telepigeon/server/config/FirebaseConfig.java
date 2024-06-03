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
import java.util.List;

@Configuration
public class FirebaseConfig {
    @Bean
    FirebaseMessaging firebaseMessaging() {
        try {
            FileInputStream serviceAccount =
                    new FileInputStream("src/main/resources/firebase.json");
            FirebaseApp firebaseApp = null;
            List<FirebaseApp> firebaseAppList = FirebaseApp.getApps();
            if (firebaseAppList != null && !firebaseAppList.isEmpty()) {
                for(FirebaseApp app : firebaseAppList){
                    if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
                        firebaseApp = app;
                    }
                }
            } else {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        .build();
                firebaseApp = FirebaseApp.initializeApp(options);
            }
            assert firebaseApp != null;
            return FirebaseMessaging.getInstance(firebaseApp);
        } catch (IOException e){
            throw new NotFoundException(NotFoundErrorCode.NOT_FOUND_FILE);
        }
    }
}
