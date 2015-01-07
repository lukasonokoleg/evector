package univ.evector.beans.book.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;

import org.apache.log4j.Logger;

import univ.evector.beans.book.Paragraph;
import univ.evector.beans.book.Sentence;

/**
 * Sakiniu isgavimo utility klase
 * @author Andrius Misiukas Misi�nas
 * @version 1.0
 */
public class SentenceHelper {

    private final static Logger logger = Logger.getLogger(SentenceHelper.class);

    public static void cleanGwrdIds(List<Sentence> sentences) {
        if (sentences != null) {
            for (Sentence sentence : sentences) {
                cleanGwrdIds(sentence);
            }
        }
    }

    public static void cleanGwrdIds(Sentence sentence) {
        if (sentence != null) {
            WordHelper.cleanGwrdIds(sentence.getWords());
        }
    }

    public static boolean hasSntId(Sentence sentece) {
        boolean retVal = sentece != null && sentece.getSnt_id() != null;
        return retVal;
    }

    /**
     * Numatytieji (default) sakinio pabaigos zenklai.
     */
    private static final List<String> EoS = Arrays.asList(".", "!", "?");

    private SentenceHelper() {
    }

    /**
     * Pastraipos padalinimo i sakinius metodas. Naudojami numatytieji
     * sakinio pabaigos zenklai.
     * @param text - pastraipos tekstas
     * @return Pastraipos sakiniu ArrayList
     */
    public static ArrayList<String> getSentences(String text) {
        ArrayList<String> retVal = new ArrayList<>();
        if (hasAnyOfPart(text, EoS)) {
            retVal.addAll(SentenceHelper.getSentences(text, EoS));
        } else {
            retVal.add(text);
        }
        return retVal;
    }

    private static boolean hasAnyOfPart(String value, List<String> parts) {
        boolean retVal = false;
        if (value != null && parts != null && parts.size() > 0) {
            for (String part : parts) {
                if (part != null && value.contains(part)) {
                    retVal = true;
                    break;
                }
            }
        }
        return retVal;
    }

    /**
     * Pastraipos padalinimo i sakinius metodas.
     * @param text - pastraipos tekstas
     * @param sentenceEndCodes - sakinio pabaigos zenklu List
     * @return Pastraipos sakiniu ArrayList
     */
    public static ArrayList<String> getSentences(String text, List<String> sentenceEndCodes) {
        // pasidarom patterna sakinio pabaigos simboliu radimui
        StringBuilder p = new StringBuilder();
        p.append("([^");
        for (String i : sentenceEndCodes) {
            p.append(i);
        }
        p.append("]*)[");
        for (String i : sentenceEndCodes) {
            p.append("\\").append(i);
        }
        p.append("]");
        // Taip turetu atrodyti numatytasis patternas
        // Pattern pattern = Pattern.compile("([^.?!]*)[\\?|\\.|\\!]");
        Pattern pattern = Pattern.compile(p.toString());
        Matcher matcher = pattern.matcher(text);
        // ieskom sakiniu su duotais pabaigos zenklais
        ArrayList<String> ATS = new ArrayList();
        while (matcher.find()) {
            String tmp = matcher.group();
            // Jei sakinys baigiasi ..., kad nesigautu triju sakiniu po "."
            if (!sentenceEndCodes.contains(tmp)) {
                // trim pasalina leading ir trailing tarpus
                ATS.add(tmp.trim());
            } else {
                StringBuilder sakinys = new StringBuilder();
                int atsSize = ATS.size();
                if (atsSize > 0) {
                    String stringToAppend = ATS.get(atsSize - 1);
                    sakinys.append(stringToAppend).append(tmp);
                    ATS.remove(ATS.size() - 1);
                    ATS.add(sakinys.toString());
                }
            }
        }
        return ATS;
    }

    /**
     * Metodas, isdalinantis pastraipa i sakinius ir pasalinantis skyrybos
     * zenklus.
     * @param text pastraipos tekstas
     * @param sentenceEndCodes sakinio pabaigos zenklai
     * @return pastraipos sakiniu arraylist
     */
    public static ArrayList<String> getSentencesStrip(String text, List<String> sentenceEndCodes) {
        ArrayList<String> TMP = SentenceHelper.getSentences(text, sentenceEndCodes);
        ArrayList<String> ATS = new ArrayList();
        for (String i : TMP) {
            // panaikinam viska, kas nera raide
            ATS.add(i.replaceAll("[^a-zA-Z�-��-� ]", ""));
        }
        return ATS;
    }

    /**
     * Metodas, isdalinantis pastraipa i sakinius ir pasalinantis skyrybos
     * zenklus. Imami numatytieji sakinio pabaigos zenklai.
     * @param text pastraipos tekstas
     * @return pastraipos sakiniu arraylist
     */
    public static ArrayList<String> getSentencesStrip(String text) {
        return getSentencesStrip(text, EoS);
    }

    public static List<Sentence> getAllSentences(List<Paragraph> paragraphs) {
        List<Sentence> retVal = new ArrayList<>();
        if (paragraphs != null) {
            for (Paragraph paragraph : paragraphs) {
                if (paragraph != null && paragraph.getSentences() != null) {
                    retVal.addAll(paragraph.getSentences());
                }
            }
        }
        return retVal;
    }
}
