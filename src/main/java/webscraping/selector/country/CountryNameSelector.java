package webscraping.selector.country;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import webscraping.model.country.CountryName;

@Slf4j
public class CountryNameSelector {
    private CountryNameSelector(){}

    public static CountryName getCountryName(Document doc) {
        CountryName countryName = new CountryName();

        //english name
        Elements englishElmts = doc.select("#firstHeading");
        if(!englishElmts.isEmpty()) {
            countryName.setEnglish(englishElmts.text().trim());
        }

        //kanji name
        Elements kanjiElmts = doc.select("span[lang=\"ja\"]");
        if (!kanjiElmts.isEmpty()) {
            String kanjiName = kanjiElmts.get(0).text().trim();
            if (kanjiName.contains("[")) {
                countryName.setKanji(kanjiName.replaceAll("[0-9]", "").replace("[", "").replace("]", "").trim());
            } else {
                countryName.setKanji(kanjiName.trim());
            }
        }

        //romaji
        Elements romajiElements = doc.select(".mw-parser-output > p");
        if(!romajiElements.isEmpty()) {
            if (!romajiElements.get(0).text().equals("")) {
                countryName.setRomaji(romajiElements.get(0).select("span + i").text().trim());
            } else {
                countryName.setRomaji(romajiElements.get(1).select("span + i").text().trim());
            }
        }
        log.info("Country name getted.");
        return countryName;
    }
}
