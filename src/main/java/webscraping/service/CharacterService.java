package webscraping.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import webscraping.document.CharacterDoc;
import webscraping.dto.CharacterDTO;
import webscraping.model.character.*;
import webscraping.repository.CharacterRepository;
import webscraping.util.JsoupConnection;

import java.io.IOException;
import java.util.List;

import static webscraping.selector.character.CharDatabookSelector.getDatabookInfo;
import static webscraping.selector.character.CharDebutSelector.getInfoDebut;
import static webscraping.selector.character.CharInfoSelector.getInfoBase;
import static webscraping.selector.character.CharNameSelector.getInfoName;
import static webscraping.selector.character.CharPersonalSelector.getInfoPersonal;
import static webscraping.selector.character.CharRankSelector.getInfoRank;
import static webscraping.selector.character.CharVoiceSelector.getInfoVoices;
import static webscraping.util.CharacterInfoCheckNull.*;

@Service
@Slf4j
public class CharacterService {

    @Autowired
    CharacterRepository characterRepository;

    public CharacterDoc getCharacterInfo(String charName) {
        Document doc = JsoupConnection.connectionInfo(charName);
        log.info("Character url jsoup connected");
        CharacterDoc characterDoc = new CharacterDoc();

        if (doc != null) {
            try {
                CharName name = getInfoName(doc);
                CharInfo charInfo = getInfoBase(doc);
                CharDebut charDebut = getInfoDebut(doc);
                CharPersonal charPersonal = getInfoPersonal(doc);
                CharRank charRank = getInfoRank(doc);
                CharVoice charVoice = getInfoVoices(doc);
                List<CharDatabook> charDatabooks = getDatabookInfo(doc);

                characterDoc.setName(name);
                characterDoc.setDescription(charInfo.getDescription() == null ? null : charInfo.getDescription());
                characterDoc.setImages(charInfo.getImages() == null ? null : charInfo.getImages());
                characterDoc.setDebut(checkNullInfoDebut(charDebut) ? null : charDebut);
                characterDoc.setVoices(checkNullInfoVoice(charVoice) ? null : charVoice);
                characterDoc.setPersonal(checkNullInfoPersonal(charPersonal) ? null : charPersonal);
                characterDoc.setCharRank(checkNullInfoRank(charRank) ? null : charRank);
                characterDoc.setFamily(charInfo.getFamily() == null ? null : charInfo.getFamily());
                characterDoc.setNatureTypes(charInfo.getNatureTypes() == null ? null : charInfo.getNatureTypes());
                characterDoc.setUniqueTraits(charInfo.getUniqueTraits() == null ? null : charInfo.getUniqueTraits());
                characterDoc.setJutsus(charInfo.getJutsus() == null ? null : charInfo.getJutsus());
                characterDoc.setTools(charInfo.getTools() == null ? null : charInfo.getTools());
                characterDoc.setDatabooks(checkNullDatabook(charDatabooks) ? null : charDatabooks);
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
        } else {
            log.warn("Document is empty.");
        }
        return characterDoc;
    }

    public void insert(CharacterDoc characterDoc) {
        if (getCheckCharacter(characterDoc.getName().getEnglish()) == null) { //check if character already
            // exists
            characterRepository.insert(characterDoc);
        } else {
            log.warn("Character already exists.");
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Character already exists.");
        }
    }

    public CharacterDTO getCheckCharacter(String name) {
        return characterRepository.findByNameEnglish(name);
    }

    public CharacterDTO getCharacter(String name) {
        if (characterRepository.findByNameEnglish(name) == null) {
            log.warn("Character not found.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Character not found.");
        }
        return characterRepository.findByNameEnglish(name);
    }
}
