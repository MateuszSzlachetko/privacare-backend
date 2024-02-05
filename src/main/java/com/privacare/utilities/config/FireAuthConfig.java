package com.privacare.utilities.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
public class FireAuthConfig {

    @Bean
    public FirebaseAuth initializeFireAuthApp() throws IOException {
        FileInputStream serviceAccount;
        try {
            serviceAccount = new FileInputStream("C:\\Users\\Mateusz\\Desktop\\Programming\\Thesis\\firebase-admin-key\\privacare.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Firebase credentials not found");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();
        return FirebaseAuth.getInstance(FirebaseApp.initializeApp(options));
    }
}

