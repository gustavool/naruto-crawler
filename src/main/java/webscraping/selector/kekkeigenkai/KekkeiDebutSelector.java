package webscraping.selector.kekkeigenkai;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import webscraping.model.kekkeigenkai.KekkeiAnime;
import webscraping.model.kekkeigenkai.KekkeiDebut;
import webscraping.model.kekkeigenkai.KekkeiManga;

import java.util.ArrayList;
import java.util.List;

import static webscraping.util.KekkeiInfoCheckNull.checkNullInfoAnime;
import static webscraping.util.KekkeiInfoCheckNull.checkNullManga;

@Slf4j
public class KekkeiDebutSelector {
    private KekkeiDebutSelector() {
    }

    public static KekkeiDebut getKekkeiDebut(Document doc) {
        KekkeiDebut kekkeiDebut = new KekkeiDebut();
        KekkeiManga kekkeiManga = new KekkeiManga();
        KekkeiAnime kekkeiAnime = new KekkeiAnime();

        //manga
        Elements mangaElements = doc.select("th:contains(Manga)");
        if (!mangaElements.isEmpty()) {
            if (mangaElements.toString().contains("Viz manga")) {
                kekkeiManga.setName(mangaElements.get(1).parents().first().select("td a i").text().trim());
                String volume = mangaElements.get(1).parents().first().select("td a").get(0).text().trim();
                kekkeiManga.setVolume(Double.parseDouble(volume.substring(volume.indexOf("#") + 1)));
                String chapter = mangaElements.get(1).parents().first().select("td a").get(1).text().trim();
                kekkeiManga.setChapter(Double.parseDouble(chapter.substring(chapter.indexOf("#") + 1)));
            } else {
                kekkeiManga.setName(mangaElements.get(0).parents().first().select("td a i").text().trim());
                String volume = mangaElements.get(0).parents().first().select("td a").get(0).text().trim();
                kekkeiManga.setVolume(Double.parseDouble(volume.substring(volume.indexOf("#") + 1)));
                String chapter = mangaElements.get(0).parents().first().select("td a").get(1).text().trim();
                kekkeiManga.setChapter(Double.parseDouble(chapter.substring(chapter.indexOf("#") + 1)));
            }

        }
        kekkeiDebut.setManga(checkNullManga(kekkeiManga) ? null : kekkeiManga);

        //anime
        Elements animeElements = doc.select("th:containsOwn(Anime)");
        if (!animeElements.isEmpty()) {
            String anime = animeElements.first().parent().children().get(1).text();
            kekkeiAnime.setName(animeElements.first().parent().select("tr td i").text().trim());
            kekkeiAnime.setEpisode(Double.parseDouble(anime.substring(anime.indexOf("#") + 1)));
        }
        kekkeiDebut.setAnime(checkNullInfoAnime(kekkeiAnime) ? null : kekkeiAnime);

        //novel
        Elements novelElements = doc.select("th:containsOwn(Novel)");
        if (!novelElements.isEmpty()) {
            String novel = novelElements.first().parent().children().get(1).text();
            if (novel.contains("(")) {
                kekkeiDebut.setNovel(novel.substring(0, novel.indexOf(" (")).trim());
            } else {
                kekkeiDebut.setNovel(novel);
            }
        }

        //movie
        Elements movieElements = doc.select("th:containsOwn(Movie)");
        if (!movieElements.isEmpty()) {
            String movie = movieElements.first().parent().children().get(1).text();
            if (movie.contains("(")) {
                kekkeiDebut.setMovie(movie.substring(0, movie.indexOf(" (")).trim());
            } else {
                kekkeiDebut.setMovie(movie);
            }
        }

        //game
        Elements gameElements = doc.select("th:containsOwn(Game)");
        if (!gameElements.isEmpty()) {
            String game = gameElements.first().parent().children().get(1).text();
            if (game.contains("(")) {
                kekkeiDebut.setGame(game.substring(0, game.indexOf(" (")).trim());
            } else {
                kekkeiDebut.setGame(game);
            }
        }

        //ova
        Elements ovaElements = doc.select("th:containsOwn(OVA)");
        if (!ovaElements.isEmpty()) {
            String ova = ovaElements.first().parent().children().get(1).text();
            if (ova.contains("(")) {
                kekkeiDebut.setOva(ova.substring(0, ova.indexOf(" (")).trim());
            } else {
                kekkeiDebut.setOva(ova);
            }
        }

        //appears
        Elements appearsElements = doc.select("th:containsOwn(Appears in)");
        List<String> appears = new ArrayList<>();
        if (!appearsElements.isEmpty()) {
            String[] appearsElemSeparated = appearsElements.first().parent().children().get(1).text().split(", ");
            for (String appear : appearsElemSeparated) {
                appears.add(appear.trim().trim());
            }
            kekkeiDebut.setAppears(appears);
        }
        log.info("Debut info getted.");
        return kekkeiDebut;
    }
}
