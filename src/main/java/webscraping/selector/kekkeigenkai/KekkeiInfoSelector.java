package webscraping.selector.kekkeigenkai;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import webscraping.model.kekkeigenkai.KekkeiInfo;
import webscraping.util.Converters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class KekkeiInfoSelector {
    private KekkeiInfoSelector() {
    }

    public static KekkeiInfo getInfoKekkei(Document doc, Document docIcon, String kekkeiName) throws IOException {
        KekkeiInfo kekkeiInfo = new KekkeiInfo();

        //description
        Elements descElements = doc.select(".infobox ~ p");
        if (!descElements.isEmpty()) {
            if (!descElements.get(0).text().equals("")) {
                kekkeiInfo.setDescription(descElements.get(0).text().trim());
            } else {
                kekkeiInfo.setDescription(descElements.get(1).text().trim());
            }
            if (kekkeiInfo.getDescription().contains("[")) {
                kekkeiInfo.setDescription(kekkeiInfo.getDescription().replaceAll("[0-9]", "").replace("[", "").replace("]", "").trim());
            }
        }

        //image
        Elements imageElements = doc.select(".infobox .image");
        if (!imageElements.isEmpty()) {
            String urlImage = imageElements.attr("href");
            kekkeiInfo.setImage(Converters.getBase64Image(urlImage.substring(0, urlImage.indexOf("/revision")) +
                    "/revision" +
                    "/latest/scale-to-width-down/300"));
        }

        //image icon
        String selector = kekkeiName.substring(0, 4);
        Elements iconImgElements = docIcon.select("img[alt^=" + selector + "]");
        if (!iconImgElements.isEmpty()) {
            for (Element icoElement : iconImgElements) {
                String urlIcon = icoElement.select("img").attr("data-src");
                kekkeiInfo.setImageSymbol(Converters.getBase64Image(urlIcon.substring(0, urlIcon.indexOf("/revision")) +
                        "/revision" +
                        "/latest/scale-to-width-down/70"));
            }
        }

        //clan
        Elements clanElements = doc.select("th:containsOwn(Clan)");
        if (!clanElements.isEmpty()) {
            List<String> clans = new ArrayList<>();
            for (Element element : clanElements.first().parent().children().select("td ul li")) {
                clans.add(element.text().trim());
            }
            kekkeiInfo.setClan(clans);
        }

        //classification
        Elements classificationElements = doc.select("th:containsOwn(Classification)");
        if (!classificationElements.isEmpty()) {
            List<String> classifications = new ArrayList<>();
            for (Element element : classificationElements.first().parent().children().select("td a")) {
                classifications.add(element.text().trim());
            }
            kekkeiInfo.setClassification(classifications);
        }

        //basic natures
        Elements natureElements = doc.select("th:containsOwn(Basic Natures)");
        if (!natureElements.isEmpty()) {
            List<String> basicNatures = new ArrayList<>();
            for (Element element : natureElements.first().parent().children().select("td a:not(:has(img))")) {
                basicNatures.add(element.text().trim());
            }
            kekkeiInfo.setBasicNatures(basicNatures);
        }

        //known wielders
        Elements wielderElements = doc.select("th:containsOwn(Known Wielders)");
        if (!wielderElements.isEmpty()) {
            List<String> knownWielders = new ArrayList<>();
            for (Element element : wielderElements.parents().get(1).select("tr td ul li a")) {
                knownWielders.add(element.text().trim());
            }
            kekkeiInfo.setWielders(knownWielders);
        }

        //jutsus
        Elements jutsuElements = doc.select("th:containsOwn(Jutsu)");
        if (!jutsuElements.isEmpty()) {
            List<String> jutsus = new ArrayList<>();
            for (Element element : jutsuElements.parents().get(1).select("tr td ul li a")) {
                jutsus.add(element.text().trim());
            }
            kekkeiInfo.setJutsus(jutsus);
        }
        log.info("Kekkei genkai info getted.");
        return kekkeiInfo;
    }
}
