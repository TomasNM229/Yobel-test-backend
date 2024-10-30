package com.example.demo.Controllers;

import com.example.demo.Model.Entities.Character;
import com.example.demo.Services.CharacterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CharacterControllerTest {

    @InjectMocks
    private CharacterController characterController;

    @Mock
    private CharacterService characterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckSalud() {
        String response = characterController.checkSalud();
        assertEquals("Estoy trabajando correctamente", response);
    }

    @Test
    void testGetDetails() throws Exception {
        Character character1 = new Character(); 
        character1.setId(1);
        character1.setName("Rick Sanchez");
        
        Character character2 = new Character();
        character2.setId(2);
        character2.setName("Morty Smith");

        List<Character> characters = Arrays.asList(character1, character2);
        when(characterService.getCharacters()).thenReturn(characters);

        ResponseEntity<?> response = characterController.getDetails();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(characters, response.getBody());
    }

    @Test
    void testGetCharacterById() throws Exception {
        Character character = new Character(); 
        character.setId(1);
        character.setName("Rick Sanchez");

        when(characterService.getCharacterById(1)).thenReturn(character);

        ResponseEntity<?> response = characterController.getCharacterById(1);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(character, response.getBody());
    }

    @Test
    void testGetCharacterById_NotFound() throws Exception {
        when(characterService.getCharacterById(999)).thenThrow(new Exception("Character not found"));

        ResponseEntity<?> response = characterController.getCharacterById(999);

        assertEquals(500, response.getStatusCode().value());
        assertEquals("Error al obtener los datos: Character not found", response.getBody());
    }

    @Test
    void testGetCharactersByName() throws Exception {
        Character character = new Character(); 
        character.setId(1);
        character.setName("Rick Sanchez");

        when(characterService.getCharactersByName("Rick")).thenReturn(Arrays.asList(character));

        ResponseEntity<?> response = characterController.getCharactersByName("Rick");

        assertEquals(200, response.getStatusCode().value());
        assertEquals(Arrays.asList(character), response.getBody());
    }

    @Test
    void testGetCharactersByName_Error() throws Exception {
        when(characterService.getCharactersByName("InvalidName")).thenThrow(new Exception("Error fetching characters"));

        ResponseEntity<?> response = characterController.getCharactersByName("InvalidName");

        assertEquals(500, response.getStatusCode().value());
        assertEquals("Error al obtener los datos: Error fetching characters", response.getBody());
    }
}
