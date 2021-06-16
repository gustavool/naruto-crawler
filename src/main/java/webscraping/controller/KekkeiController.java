package webscraping.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webscraping.document.KekkeiDoc;
import webscraping.dto.KekkeiDTO;
import webscraping.service.KekkeiService;

@Slf4j
@RestController
@RequestMapping(value = "kekkeigenkai")
public class KekkeiController {
    @Autowired
    KekkeiService kekkeiService;

    @PostMapping(value = "/{name}")
    public ResponseEntity<Void> saveKekkeiGenkai(@PathVariable String name) {
        log.info("Starting get infos for kekkei genkai: {}", name);
        KekkeiDoc kekkeiDoc = kekkeiService.getKekkeiInfo(name);
        kekkeiService.insert(kekkeiDoc);
        log.info("Kekkei genkai {} infos saved.", name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<KekkeiDTO> getKekkei(@PathVariable String name) {
        log.info("Searching infos for kekkei: {}", name);
        KekkeiDTO kekkei = kekkeiService.getKekkeiGenkai(name);
        log.info("Infos getted for kekkei: {}", name);
        return ResponseEntity.ok().body(kekkei);
    }
}
