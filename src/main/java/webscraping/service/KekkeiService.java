package webscraping.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import webscraping.document.KekkeiDoc;
import webscraping.dto.KekkeiDTO;
import webscraping.model.kekkeigenkai.KekkeiDebut;
import webscraping.model.kekkeigenkai.KekkeiInfo;
import webscraping.model.kekkeigenkai.KekkeiName;
import webscraping.repository.KekkeiRepository;
import webscraping.util.JsoupConnection;

import java.io.IOException;

import static webscraping.selector.kekkeigenkai.KekkeiDebutSelector.getKekkeiDebut;
import static webscraping.selector.kekkeigenkai.KekkeiInfoSelector.getInfoKekkei;
import static webscraping.selector.kekkeigenkai.KekkeiNameSelector.getNameKekkei;
import static webscraping.util.KekkeiInfoCheckNull.checkNullDebut;
import static webscraping.util.KekkeiInfoCheckNull.checkNullInfoName;

@Slf4j
@Service
public class KekkeiService {

    @Autowired
    KekkeiRepository kekkeiRepository;

    public KekkeiDoc getKekkeiInfo(String kekkeiName) {
        Document doc = JsoupConnection.connectionInfo(kekkeiName);
        log.info("Kekkei Genkai url jsoup connected");
        Document docIcon = JsoupConnection.connectionKekkeiIcon();

        KekkeiDoc kekkeiDoc = new KekkeiDoc();
        if (doc != null) {
            try {
                KekkeiName kekkeiNameGet = getNameKekkei(doc);
                String englishName = kekkeiNameGet.getEnglish();
                KekkeiInfo kekkeiInfo = getInfoKekkei(doc, docIcon, englishName);
                KekkeiDebut kekkeiDebut = getKekkeiDebut(doc);

                kekkeiDoc.setName(checkNullInfoName(kekkeiNameGet) ? null : kekkeiNameGet);

                kekkeiDoc.setDescription(kekkeiInfo.getDescription() == null ? null : kekkeiInfo.getDescription());
                kekkeiDoc.setImage(kekkeiInfo.getImage() == null ? null : kekkeiInfo.getImage());
                kekkeiDoc.setImageSymbol(kekkeiInfo.getImageSymbol() == null ? null : kekkeiInfo.getImageSymbol());
                kekkeiDoc.setDebut(checkNullDebut(kekkeiDebut) ? null : kekkeiDebut);
                kekkeiDoc.setClan(kekkeiInfo.getClan() == null ? null : kekkeiInfo.getClan());
                kekkeiDoc.setClassification(kekkeiInfo.getClassification() == null ? null : kekkeiInfo.getClassification());
                kekkeiDoc.setBasicNatures(kekkeiInfo.getBasicNatures() == null ? null : kekkeiInfo.getBasicNatures());
                kekkeiDoc.setWielders(kekkeiInfo.getWielders() == null ? null : kekkeiInfo.getWielders());

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.warn("Document is empty.");
        }
        return kekkeiDoc;
    }

    public void insert(KekkeiDoc kekkeiDoc) {
        if (getCheckKekkeiGenkai(kekkeiDoc.getName().getEnglish()) == null) { //check if kekkei genkai already exists
            kekkeiRepository.insert(kekkeiDoc);
        } else {
            log.warn("Kekkei genkai already exists.");
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Kekkei genkai already exists.");
        }
    }

    public KekkeiDTO getCheckKekkeiGenkai(String name) {
        return kekkeiRepository.findByNameEnglish(name);
    }

    public KekkeiDTO getKekkeiGenkai(String name) {
        if(kekkeiRepository.findByNameEnglish(name) == null) {
            log.warn("Kekkei genkai not found.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Kekkei genkai not found.");
        }
        return kekkeiRepository.findByNameEnglish(name);
    }

}
