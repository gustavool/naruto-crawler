package webscraping.selector.country;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import webscraping.model.country.CountryInfo;
import webscraping.util.Converters;

import java.io.IOException;

@Slf4j
public class CountryInfoSelector {
    private CountryInfoSelector(){}

    public static CountryInfo getInfoCountry(Document doc) throws IOException {
        CountryInfo countryInfo = new CountryInfo();

        //description
        Elements descElements = doc.select(".mw-parser-output > p");
        if (!descElements.isEmpty()) {
            if (!descElements.get(0).text().equals("")) {
                countryInfo.setDescription(descElements.get(0).text().trim());
            } else if (!descElements.get(1).text().equals("")) {
                countryInfo.setDescription(descElements.get(1).text().trim());
            }
            else {
                countryInfo.setDescription(descElements.get(2).text().trim());
            }
            if (countryInfo.getDescription().contains("[")) {
                countryInfo.setDescription(countryInfo.getDescription().replaceAll("[0-9]", "").replace("[", "").replace("]", "").trim());
            }
        }

        //image
        Elements imageElements = doc.select(".mw-parser-output .image");
        if (!imageElements.isEmpty()) {
            String urlImage = imageElements.attr("href");
            countryInfo.setImage(Converters.getBase64Image(urlImage.substring(0, urlImage.indexOf("/revision")) +
                    "/revision" +
                    "/latest/scale-to-width-down/300"));
        }
        log.info("Country base info getted.");

        return countryInfo;
    }
}
