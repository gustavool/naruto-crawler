package webscraping.selector.kekkeigenkai;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import webscraping.model.kekkeigenkai.KekkeiName;

@Slf4j
public class KekkeiNameSelector {
    private KekkeiNameSelector() {
    }

    public static KekkeiName getNameKekkei(Document doc) {
        KekkeiName kekkeiName = new KekkeiName();

        //english name
        Elements englishElements = doc.getElementsByClass("mainheader");
        if (!englishElements.isEmpty()) {
            String englishName = englishElements.get(0).text().trim();
            if (englishName.contains("[")) {
                kekkeiName.setEnglish(englishName.replaceAll("[0-9]", "").replace("[", "").replace("]", "").trim());
            } else {
                kekkeiName.setEnglish(englishName.trim());
            }
        }

        //kanji name
        Elements kanjiElmts = doc.select("span[lang=\"ja\"]");
        if (!kanjiElmts.isEmpty()) {
            String kanjiName = kanjiElmts.get(0).text().trim();
            if (kanjiName.contains("[")) {
                kekkeiName.setKanji(kanjiName.replaceAll("[0-9]", "").replace("[", "").replace("]", "").trim());
            } else {
                kekkeiName.setKanji(kanjiName.trim());
            }
        }

        //romaji name
        Elements romajiElmts = doc.select("th:containsOwn(Rōmaji)");
        if(!romajiElmts.isEmpty()) {
            String romajiName = romajiElmts.first().parent().children().select("td").text().trim();
            if (romajiName.contains("[")) {
                kekkeiName.setRomaji(romajiName.replaceAll("[0-9]", "").replace("[", "").replace("]", "").trim());
            } else {
                kekkeiName.setRomaji(romajiName.trim());
            }
        }

        //other name
        Elements otherElmts = doc.select("th:containsOwn(Other)");
        if(!otherElmts.isEmpty()) {
            String otherName = otherElmts.first().parent().select("td").text();
            if (otherName.contains("[")) {
                kekkeiName.setOther(otherName.replaceAll("[0-9]", "").replace("[", "").replace("]", "").trim());
            } else {
                kekkeiName.setOther(otherName.trim());
            }
        }
        log.info("Name info getted.");
        return kekkeiName;
    }
}
