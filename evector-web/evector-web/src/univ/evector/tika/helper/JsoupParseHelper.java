package univ.evector.tika.helper;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

public class JsoupParseHelper {

    public static List<String> parseByParagraph(String parsedRawText) {
        List<String> textStrings = new ArrayList<String>();
        List<String> wholeTextStrings = new ArrayList<String>();
        Document document = Jsoup.parse(parsedRawText);
        Elements elements = document.select("p");

        int elemSize = elements.size();
        for (int index = 0; index < elemSize; index++) {
            Element element = elements.get(index);
            List<TextNode> textDataList = element.textNodes();
            for (TextNode textNode : textDataList) {
                if (textNode != null) {
                    String tmpText = textNode.text();
                    textStrings.add(tmpText);
                    String wholeText = textNode.getWholeText();
                    wholeTextStrings.add(wholeText);
                }
            }
        }

        return textStrings;
    }

}
