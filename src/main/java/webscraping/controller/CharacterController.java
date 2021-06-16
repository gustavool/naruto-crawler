package webscraping.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webscraping.document.CharacterDoc;
import webscraping.dto.CharacterDTO;
import webscraping.service.CharacterService;

@Slf4j
@RestController
@RequestMapping(value = "character")
public class CharacterController {

    @Autowired
    CharacterService characterService;

    @PostMapping(value = "/{name}")
    public ResponseEntity<String> saveCharacter(@PathVariable String name) {
            log.info("Starting get infos for character: {}", name);
            CharacterDoc characterDoc = characterService.getCharacterInfo(name);

            characterService.insert(characterDoc);
            log.info("Character {} infos saved.", name);

            return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<CharacterDTO> getCharacter(@PathVariable String name) {
        log.info("Searching infos for character: {}", name);
        CharacterDTO character = characterService.getCharacter(name);
        log.info("Infos getted for character: {}", name);
        return ResponseEntity.ok().body(character);
    }

}
