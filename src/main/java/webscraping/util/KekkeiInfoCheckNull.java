package webscraping.util;

import webscraping.model.kekkeigenkai.KekkeiAnime;
import webscraping.model.kekkeigenkai.KekkeiDebut;
import webscraping.model.kekkeigenkai.KekkeiManga;
import webscraping.model.kekkeigenkai.KekkeiName;

public class KekkeiInfoCheckNull {
    //checks to don't create empty object in json
    public static boolean checkNullInfoName(KekkeiName kekkeiName) {
        return kekkeiName.getEnglish() == null && kekkeiName.getKanji() == null
                && kekkeiName.getRomaji() == null && kekkeiName.getOther() == null;
    }

    public static boolean checkNullInfoAnime(KekkeiAnime kekkeiAnime) {
        return kekkeiAnime.getName() == null && kekkeiAnime.getEpisode() == null;
    }

    public static boolean checkNullManga(KekkeiManga kekkeiManga) {
        return kekkeiManga.getName() == null && kekkeiManga.getVolume() == null
                && kekkeiManga.getChapter() == null;
    }

    public static boolean checkNullDebut(KekkeiDebut kekkeiDebut) {
        return kekkeiDebut.getManga() == null && kekkeiDebut.getAnime() == null
                && kekkeiDebut.getNovel() == null && kekkeiDebut.getMovie() == null
                && kekkeiDebut.getGame() == null && kekkeiDebut.getOva() == null;
    }
}
