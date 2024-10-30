package com.example.demo.Services;

import com.example.demo.Model.Entities.Character;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CharacterServiceTest {

    @InjectMocks
    private CharacterService characterService;

    @Mock
    private RestTemplate restTemplate;

    private final ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetCharacters() throws Exception {
        String jsonResponse = "{\"results\": [{\"id\": 1, \"name\": \"Rick Sanchez\"}]}";
        ResponseEntity<String> responseEntity = ResponseEntity.ok(jsonResponse);


        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
            .thenReturn(responseEntity);

        List<Character> characters = characterService.getCharacters();

        assertEquals(1, characters.size());
        assertEquals("Rick Sanchez", characters.get(0).getName());
    }

    @Test
    void testGetCharacterById() throws Exception {
        String jsonResponse = "{\"id\": 1, \"name\": \"Rick Sanchez\"}";
        ResponseEntity<String> responseEntity = ResponseEntity.ok(jsonResponse);


        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
            .thenReturn(responseEntity);

        Character character = characterService.getCharacterById(1);

        assertEquals("Rick Sanchez", character.getName());
    }

    @Test
    void testGetCharactersByName() throws Exception {
        String jsonResponse = "{\"results\": [{\"id\": 1, \"name\": \"Rick Sanchez\"}]}";
        ResponseEntity<String> responseEntity = ResponseEntity.ok(jsonResponse);

        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
            .thenReturn(responseEntity);

        List<Character> characters = characterService.getCharactersByName("Rick");

        assertEquals(1, characters.size());
        assertEquals("Rick Sanchez", characters.get(0).getName());
    }
}
