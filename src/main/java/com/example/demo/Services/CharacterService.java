package com.example.demo.Services;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Model.Entities.Character;
import com.example.demo.Model.Entities.CharacterResponse;


@Service
@AllArgsConstructor
public class CharacterService {
        private static final String API_URL = "https://rickandmortyapi.com/api/character/";

        @Autowired
        private RestTemplate restTemplate;

        public List<Character> getCharacters() throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.GET, HttpEntity.EMPTY, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseMap = mapper.readValue(response.getBody(), Map.class);
        
        return mapper.convertValue(
            responseMap.get("results"),
            mapper.getTypeFactory().constructCollectionType(List.class, Character.class)
        );
    }
    
    public Character getCharacterById(int id) throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(API_URL + id, HttpMethod.GET, HttpEntity.EMPTY, String.class);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response.getBody(), Character.class);
    }

    public List<Character> getCharactersByName(String name) throws Exception {
        ResponseEntity<String> response = restTemplate.exchange(API_URL + "?name=" + name, HttpMethod.GET, HttpEntity.EMPTY, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> responseMap = mapper.readValue(response.getBody(), Map.class);

        return mapper.convertValue(
            responseMap.get("results"),
            mapper.getTypeFactory().constructCollectionType(List.class, Character.class)
        );
    }
}
