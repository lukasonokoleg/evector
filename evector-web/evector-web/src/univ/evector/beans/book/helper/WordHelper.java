package univ.evector.beans.book.helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lt.jmsys.spark.gwt.application.shared.helper.ConversionHelper;
import univ.evector.beans.book.GramWord;
import univ.evector.beans.book.Sentence;
import univ.evector.beans.book.Word;

/**
 *
 * @author Andrius Misiukas Misi�nas
 * @author Oleg Lukašonok
 */
public class WordHelper {

    private final boolean isDialog;
    private final boolean isExclamation;
    private final boolean isQuestion;
    private final boolean isSuspension;
    private final ArrayList<String> zodziai;
    private static final List<String> dialogoSkyryba = Arrays.asList("�", "-", "�");
    private int marker;

    /**
     * Sakinio parseris, kuris issiaiskina pagrindinius duomenis apie sakini.
     * Po to gauna sakinio zodzius tolimesnei analizei. Is esmes atlieka 1.1
     * punkta is schemos.
     * @param sentence sakinys
     */
    public WordHelper(String sentence) {
        String tmp = sentence.trim();
        this.isDialog = dialogoSkyryba.contains(String.valueOf(tmp.charAt(0)));
        this.isExclamation = tmp.substring(tmp.length() - 3).contains("!");
        this.isQuestion = tmp.substring(tmp.length() - 3).contains("?");
        this.isSuspension = tmp.substring(tmp.length() - 3).matches(".*([.?!])\\1$");
        this.zodziai = getWords(sentence);
        this.marker = 0;
    }

    /**
     * Gauna sakinio zodzius
     * @return sakinio zodziu arraylist
     */
    public ArrayList<String> getWords() {
        return this.zodziai;
    }

    /**
     * atsako i klausima ar sakinys yra dialogas.
     * @return 
     */
    public boolean dialog() {
        return this.isDialog;
    }

    /**
     * atsako i klausima ar sakinys yra saukiamasis
     * @return 
     */
    public boolean exclamation() {
        return this.isExclamation;
    }

    /**
     * atsako i klausima ar sakinys yra klausiamasis
     * @return 
     */
    public boolean question() {
        return this.isQuestion;
    }

    /**
     * atsako i klausima ar sakinys baigiasi daugtaskiu
     * @return 
     */
    public boolean suspension() {
        return this.isSuspension;
    }

    /**
     * pasako kiek zodziu yra sakinyje
     * @return zodziu skaicius
     */
    public int getWordCount() {
        return this.zodziai.size();
    }

    /**
     * Gauna kita zodi is sakinio
     * @return 
     */
    public String next() {
        if (this.marker < this.zodziai.size()) {
            return this.zodziai.get(this.marker++);
        } else {
            return null;
        }
    }

    /**
     * Gauna praeita zodi is sakinio
     * @return 
     */
    public String previous() {
        if (this.marker > 0) {
            return this.zodziai.get(--marker);
        } else {
            return null;
        }
    }

    private static final List<String> SkZ = Arrays.asList(".", "!", "?", ",", "�", "�", ":", ";");

    /**
     * Gauna sakinio zodziu arraylist
     * @param sentence sakinys
     * @param charsToRemove skyrybos zenklai
     * @return zodziu ArrayList
     */
    public static ArrayList<String> getWords(String sentence, List<String> charsToRemove) {
        StringBuilder p = new StringBuilder();
        p.append("[");
        for (String i : charsToRemove) {
            p.append(i);
        }
        p.append("]");
        String sakinys = sentence.replaceAll(p.toString(), "");
        String[] ats = sakinys.split(" ");
        ArrayList ATS = new ArrayList();
        for (String i : ats) {
            ATS.add(i.toLowerCase());
        }
        return ATS;
    }

    /**
     * Gauna sakinio zodziu arraylist. panaudojamas numatytasis skyrybos zenklu
     * masyvas.
     * @param sentence sakinys
     * @return zodziu ArrayList
     */
    public static ArrayList<String> getWords(String sentence) {
        return getWords(sentence, SkZ);
    }

    public static String filterWord(Object object) {
        if (object != null) {
            String retVal = object.toString();
            retVal = retVal.replaceAll("\\(", "");
            retVal = retVal.replaceAll("\\)", "");
            retVal = retVal.replaceAll("\\„", "");
            retVal = retVal.replaceAll("\\“", "");
            retVal = retVal.replaceAll("\\d", "");
            retVal = retVal.replaceAll("\\–", "");
            retVal = retVal.replaceAll("\\—", "");

            if (retVal.length() > 0) {
                return retVal;
            }
        }
        return null;
    }

    public static boolean hasWrdId(Word word) {
        boolean retVal = word != null && word.getWrd_id() != null;
        return retVal;
    }

    public static boolean hasGwrdId(Word word) {
        boolean retVal = word != null && word.getWrd_gwrd_id() != null;
        return retVal;
    }

    public static void cleanGwrdIds(List<Word> words) {
        if (words != null) {
            for (Word word : words) {
                cleanGwrdId(word);
            }
        }
    }

    public static void cleanGwrdId(Word word) {
        if (word != null) {
            word.setWrd_gwrd_id(null);
        }
    }

    public static void setGwrdIdToWord(Word word, GramWord gramWord) {
        if (word != null && gramWord != null) {
            word.setWrd_gwrd_id(gramWord.getGwrd_id());
        }
    }

    public static List<Word> getAllWords(List<Sentence> sentences) {
        List<Word> retVal = new ArrayList<Word>();
        if (sentences != null) {
            for (Sentence sentence : sentences) {
                if (sentence != null && !ConversionHelper.isEmpty(sentence.getWords())) {
                    retVal.addAll(sentence.getWords());
                }
            }
        }
        return retVal;
    }
}
