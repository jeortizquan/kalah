package com.lighthouse.kalah.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lighthouse.kalah.game.Kalahah;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KalahConfiguration {

    public static final Integer STONES = 4;
     @Bean
    public Kalahah board() {
         return new Kalahah(STONES);
    }

//    @Bean
//            public Gson gsonService() {
//
//
//    return new GsonBuilder().registerTypeAdapter() /*new GsonBuilder()
//            .registerTypeAdapter(Id.class,
//                    new IdTypeAdapter())
//            .enableComplexMapKeySerialization()
//            .serializeNulls()
//
//            .create();
}       //
