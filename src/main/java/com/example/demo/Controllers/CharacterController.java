package com.example.demo.Controllers;
import com.example.demo.Config.DemoConfig;
import com.example.demo.Model.Entities.Character;
import com.example.demo.Services.CharacterService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;



import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;



@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:4200")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @Autowired
	RestTemplate restTemplate;

    @GetMapping("/salud")
	public String checkSalud() {
		return "Estoy trabajando correctamente";
	}

    @GetMapping("/characters")
    public ResponseEntity<?> getDetails() {
        try {
            List<Character> characters = characterService.getCharacters();
            return ResponseEntity.ok().body(characters);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los datos: " + e.getMessage());
        }
    }

    @GetMapping("/characters/{id}")
    public ResponseEntity<?> getCharacterById(@PathVariable int id) {
        try {
            Character character = characterService.getCharacterById(id);
            return ResponseEntity.ok().body(character);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los datos: " + e.getMessage());
        }
    }

    @GetMapping("/characters/search")
    public ResponseEntity<?> getCharactersByName(@RequestParam String name) {
        try {
            List<Character> characters = characterService.getCharactersByName(name);
            return ResponseEntity.ok().body(characters);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al obtener los datos: " + e.getMessage());
        }
    }
}
