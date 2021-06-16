package webscraping.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import webscraping.document.ClanDoc;
import webscraping.dto.ClanDTO;
import webscraping.model.clan.ClanInfo;
import webscraping.model.clan.ClanName;
import webscraping.repository.ClanRepository;
import webscraping.util.JsoupConnection;

import java.io.IOException;

import static webscraping.selector.clan.ClanInfoSelector.getInfoData;
import static webscraping.selector.clan.ClanNameSelector.getNameClan;

@Slf4j
@Service
public class ClanService {

    @Autowired
    ClanRepository clanRepository;

    public ClanDoc getClanInfo(String name) {
        Document doc = JsoupConnection.connectionInfo(name);
        log.info("Clan url jsoup connected");
        ClanDoc clanDoc = new ClanDoc();
        if (doc != null) {
            try {
                ClanName clanName = getNameClan(doc);
                ClanInfo clanInfo = getInfoData(doc);

                clanDoc.setName(clanName);
                clanDoc.setDescription(clanInfo.getDescription() == null ? null : clanInfo.getDescription());
                clanDoc.setImage(clanInfo.getImage() == null ? null : clanInfo.getImage());
                clanDoc.setAppears(clanInfo.getAppears() == null ? null : clanInfo.getAppears());
                clanDoc.setAffiliation(clanInfo.getAffiliation() == null ? null : clanInfo.getAffiliation());
                clanDoc.setKekkeiGenkai(clanInfo.getKekkeiGenkai() == null ? null : clanInfo.getKekkeiGenkai());
                clanDoc.setMembers(clanInfo.getMembers() == null ? null : clanInfo.getMembers());
                clanDoc.setJutsus(clanInfo.getJutsus() == null ? null : clanInfo.getJutsus());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            log.warn("Document is empty.");
        }
        return clanDoc;
    }

    public void insert(ClanDoc clanDoc) {
        if (getCheckClan(clanDoc.getName().getEnglish()) == null) { //check if clan already exists
            clanRepository.insert(clanDoc);
        } else {
            log.warn("Clan already exists.");
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Clan already exists.");
        }
    }

    public ClanDTO getCheckClan(String name) {
        return clanRepository.findByNameEnglish(name);
    }

    public ClanDTO getClan(String name) {
        if(clanRepository.findByNameEnglish(name) == null) {
            log.warn("Clan not found.");
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Clan not found.");
        }
        return clanRepository.findByNameEnglish(name);
    }
}
