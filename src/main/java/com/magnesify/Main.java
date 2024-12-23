package com.magnesify;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.magnesify.senders.Request;
import com.moandjiezana.toml.Toml;

import java.io.File;
import java.io.IOException;

public class Main {

    public static String url_get;

    public static void loadToml() {
        File file = new File("licenses.toml");
        if (file.exists()) {
            Toml toml = new Toml().read(file);
            url_get = toml.getString("url.url_get");
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void main(String[] args) {
        loadToml();
        Request request = new Request("hacimertgokhan@gmail.com", "431007b7-eb9f-495d-8aad-997fcbf40364");
        String a = request.getData();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String prettyJson;
        Object jsonObj = null;
        try {
            jsonObj = objectMapper.readValue(a, Object.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try {
            prettyJson = objectMapper.writeValueAsString(jsonObj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(prettyJson);
    }

}