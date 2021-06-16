package webscraping.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import webscraping.document.CountryDoc;
import webscraping.dto.CountryDTO;
import webscraping.model.country.CountryInfo;
import webscraping.model.country.CountryName;
import webscraping.repository.CountryRepository;
import webscraping.selector.country.CountryInfoSelector;
import webscraping.selector.country.CountryNameSelector;
import webscraping.util.JsoupConnection;

import java.io.IOException;

@Slf4j
@Service
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public CountryDoc getCountryInfo(String name) {
        Document doc = JsoupConnection.connectionInfo(name);
        log.info("Country url jsoup connected");
        CountryDoc countryDoc = new CountryDoc();

        if (doc != null) {
            try {
                CountryName countryName = CountryNameSelector.getCountryName(doc);
                CountryInfo countryInfo = CountryInfoSelector.getInfoCountry(doc);

                countryDoc.setName(countryName);
                countryDoc.setDescription(countryInfo.getDescription() == null ? null : countryInfo.getDescription());
                countryDoc.setImage(countryInfo.getImage() == null ? null : countryInfo.getImage());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.warn("Document is empty.");
        }
        return countryDoc;
    }

    public void insert(CountryDoc countryDoc) {
        if (getCheckCountry(countryDoc.getName().getEnglish()) == null) { //check if country already exists
            countryRepository.insert(countryDoc);
        } else {
            log.warn("Country already exists.");
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Country already exists.");
        }
    }

    public CountryDTO getCheckCountry(String name) {
        return countryRepository.findByNameEnglish(name);
    }

    public CountryDTO getCountry(String name) {
        if (countryRepository.findByNameEnglish(name) == null) {
            log.warn("Country not found.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Country not found.");
        }
        return countryRepository.findByNameEnglish(name);
    }
}
