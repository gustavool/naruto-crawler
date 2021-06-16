package webscraping.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webscraping.document.CountryDoc;
import webscraping.dto.CountryDTO;
import webscraping.service.CountryService;

@Slf4j
@RestController
@RequestMapping(value = "country")
public class CountryController {
    @Autowired
    CountryService countryService;

    @PostMapping(value = "/{name}")
    public ResponseEntity<Void> saveCountry(@PathVariable String name) {
        log.info("Starting get infos for country: {}", name);
        CountryDoc countryDoc = countryService.getCountryInfo(name);
        countryService.insert(countryDoc);
        log.info("Country {} infos saved.", name);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{name}")
    public ResponseEntity<CountryDTO> getCountry(@PathVariable String name) {
        log.info("Searching infos for country: {}", name);
        CountryDTO country = countryService.getCountry(name);
        log.info("Infos getted for country: {}", name);
        return ResponseEntity.ok().body(country);
    }
}
