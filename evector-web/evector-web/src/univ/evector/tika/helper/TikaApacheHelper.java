package univ.evector.tika.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.epub.EpubContentParser;
import org.apache.tika.parser.epub.EpubParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.TaggedContentHandler;
import org.apache.tika.sax.ToHTMLContentHandler;
import org.xml.sax.SAXException;

import univ.evector.beans.book.Book;
import univ.evector.beans.book.Paragraph;
import univ.evector.beans.book.Sentence;
import univ.evector.beans.book.Word;
import univ.evector.beans.book.helper.BookMetaDataHelper;
import univ.evector.beans.book.helper.SentenceHelper;
import univ.evector.beans.book.helper.WordHelper;

public class TikaApacheHelper {

    private final static Logger logger = Logger.getLogger(TikaApacheHelper.class);

    private static EpubParser epubParser;
    private static EpubContentParser epubContentParser;

    private static StringWriter stringWriter;
    private static Metadata metadata;
    private static ParseContext context;

    private static ParseContext getParseContext() {
        context = new ParseContext();
        return context;
    }

    private static Metadata getMetaData() {
        metadata = new Metadata();
        return metadata;
    }

    private static StringWriter getStringWriter() {
        stringWriter = new StringWriter();
        return stringWriter;
    }

    private static EpubContentParser getEpubContentParser() {
        epubContentParser = new EpubContentParser();
        return epubContentParser;
    }

    private static EpubParser getEpubParser() {

        epubParser = new EpubParser();
        return epubParser;
    }

    public static List<Book> parseBooks(List<File> files) throws TransformerConfigurationException, IOException, TikaException, SAXException {
        logger.info("Parsing books.");
        if (files == null) {
            throw new IllegalArgumentException("Parameter fileDescriptors can not be NULL!");
        }
        List<Book> retBooks = new ArrayList<Book>();
        for (File file : files) {
            Book book = parseBook(file);
            retBooks.add(book);
        }
        return retBooks;
    }

    public static Book parseBook(File file) throws IOException, TikaException, TransformerConfigurationException, SAXException {
        if (file == null) {
            throw new IllegalArgumentException("Parameter file can not be NULL!");
        }
        InputStream inputStream = new FileInputStream(file);
        Book retBook = parseBook(inputStream);
        return retBook;
    }

    public static Book parseBook(InputStream inputStream) throws TransformerConfigurationException, IOException {
        if (inputStream == null) {
            throw new IllegalArgumentException("Parameter inputStream can not be NULL!");
        }
        try {
            Book retBook = parseEpubByTaggedContentHandler(inputStream);
            inputStream.close();
            return retBook;
        } catch (TikaException | SAXException e) {
            throw new RuntimeException("SYSTEM error occured.");
        }
    }

    private static BodyContentHandler createContenHandler(StringWriter stringWriter) throws TransformerConfigurationException {
        SAXTransformerFactory factory = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
        TransformerHandler handler = factory.newTransformerHandler();
        handler.getTransformer().setOutputProperty(OutputKeys.METHOD, "xml");
        handler.getTransformer().setOutputProperty(OutputKeys.INDENT, "no");
        handler.setResult(new StreamResult(stringWriter));
        BodyContentHandler bodyContentHandler = new BodyContentHandler(handler);
        return bodyContentHandler;
    }

    public static String parseEpubHtmlContentHandler(InputStream stream) throws IOException, SAXException, TikaException {
        EpubParser epubParser = getEpubParser();
        Metadata metadata = getMetaData();
        ParseContext context = getParseContext();

        ToHTMLContentHandler handler = new ToHTMLContentHandler();

        epubParser.parse(stream, handler, metadata, context);

        String parsedText = handler.toString();

        return parsedText;
    }

    public static Book parseEpubByTaggedContentHandler(InputStream stream) throws TransformerConfigurationException, IOException, SAXException, TikaException {
        EpubParser epubParser = getEpubParser();
        Metadata metadata = getMetaData();
        ParseContext context = getParseContext();
        StringWriter stringWriter = getStringWriter();
        BodyContentHandler bodyContentHandler = createContenHandler(stringWriter);
        TaggedContentHandler handler = new TaggedContentHandler(bodyContentHandler);
        epubParser.parse(stream, handler, metadata, context);
        String rawText = stringWriter.toString();

        stringWriter.close();
        List<Paragraph> paragraphs = parseParagraphs(rawText);

        Book retBook = new Book();

        retBook.setParagraphs(paragraphs);

        BookMetaDataHelper.setBookMetaData(retBook, metadata);

        return retBook;
    }

    private static List<Paragraph> parseParagraphs(String text) {
        List<Paragraph> retList = new ArrayList<>();
        List<String> parsedList = JsoupParseHelper.parseByParagraph(text);
        long seq = 0;
        for (String paragraphText : parsedList) {
            List<Sentence> sentences = parseSentences(paragraphText);
            Paragraph paragraph = new Paragraph();
            paragraph.setPrg_seq(seq);
            paragraph.setPrg_value(paragraphText);
            paragraph.setSentences(sentences);
            retList.add(paragraph);
            seq++;
        }
        return retList;
    }

    private static List<Sentence> parseSentences(String text) {
        List<Sentence> retVal = new ArrayList<>();

        ArrayList<String> sentencesAsStrings = SentenceHelper.getSentences(text);
        if (sentencesAsStrings != null) {
            long seq = 0;
            for (String sentenceAsString : sentencesAsStrings) {

                List<Word> words = parseWords(sentenceAsString);
                Sentence sentence = new Sentence();
                sentence.setSnt_seq(seq);
                sentence.setSnt_value(sentenceAsString);
                sentence.setWords(words);

                retVal.add(sentence);
                seq++;
            }
        }

        return retVal;
    }

    private static List<Word> parseWords(String text) {
        List<Word> retVal = new ArrayList<>();
        ArrayList<String> words = WordHelper.getWords(text);
        if (words != null) {
            long seq = 0;
            for (String wordAsString : words) {
                Word word = new Word();
                word.setWrd_seq(seq);
                word.setWrd_value(wordAsString);
                retVal.add(word);
                seq++;
            }
        }
        return retVal;
    }

    public static String parseEpubBodyContentHandler(InputStream stream) throws IOException, SAXException, TikaException, TransformerConfigurationException {
        EpubParser epubParser = getEpubParser();
        Metadata metadata = getMetaData();
        ParseContext context = getParseContext();

        StringWriter stringWriter = getStringWriter();

        BodyContentHandler handler = createContenHandler(stringWriter);

        epubParser.parse(stream, handler, metadata, context);

        String parsedText = handler.toString();

        return parsedText;
    }
}
