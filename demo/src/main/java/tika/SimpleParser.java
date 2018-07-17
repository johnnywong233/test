package tika;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.tika.sax.ContentHandlerDecorator;
import org.apache.tika.sax.ToXMLContentHandler;

import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wajian on 2016/10/4.
 * simple parse through tika
 */
public class SimpleParser {

    //https://tika.apache.org/1.13/examples.html#Parsing_using_the_Auto-Detect_Parser
    public static void main(String[] args) throws Exception {
        SimpleParser simpleParser = new SimpleParser();
        System.out.println(simpleParser.parseToString());
    }


    //TODO: GET NOTHING
    private String parseToString() throws IOException, SAXException, TikaException {
        Tika tika = new Tika();
//        try (InputStream stream = SimpleParser.class.getResourceAsStream("201702.pdf")) {
//            return tika.parseToString(stream);
//        }
        SimpleParser simpleParser = new SimpleParser();
        return simpleParser.parseFile(new File("C:\\work\\test_git\\test\\demo\\src\\main\\resources\\201702.pdf"));
    }

    //parse file to string
    public String parseFile(File f) {
        //create parser
        Parser parser = new AutoDetectParser();
        InputStream is = null;
        try {
            Metadata metadata = new Metadata();
            metadata.set(Metadata.RESOURCE_NAME_KEY, f.getName());
            is = new FileInputStream(f);
            ContentHandler handler = new BodyContentHandler();
            ParseContext context = new ParseContext();
            context.set(Parser.class, parser);
            //do parse
            parser.parse(is, handler, metadata, context);
            for (String name : metadata.names()) {
                System.out.println(name + ":" + metadata.get(name));
            }
            return handler.toString();
        } catch (SAXException | TikaException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    //By using the BodyContentHandler, you can request that Tika return
    //only the content of the document's body as a plain-text string.
    public String parseToPlainText() throws IOException, SAXException, TikaException {
        BodyContentHandler handler = new BodyContentHandler();

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = SimpleParser.class.getResourceAsStream("test.doc")) {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
    }

    //use ToXMLContentHandler to get the XHTML content of the whole document as a string.
    public String parseToHTML() throws IOException, SAXException, TikaException {
        ContentHandler handler = new ToXMLContentHandler();

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = SimpleParser.class.getResourceAsStream("test.doc")) {
            parser.parse(stream, handler, metadata);
            return handler.toString();
        }
    }

    //chunk the resulting text up: minimising memory use, HDFS files,With a small custom content handler.
    public List<String> parseToPlainTextChunks() throws IOException, SAXException, TikaException {
        final List<String> chunks = new ArrayList<>();
        chunks.add("");
        ContentHandlerDecorator handler = new ContentHandlerDecorator() {
            static final int MAXIMUM_TEXT_CHUNK_SIZE = 1024;

            @Override
            public void characters(char[] ch, int start, int length) {
                String lastChunk = chunks.get(chunks.size() - 1);
                String thisStr = new String(ch, start, length);

                if (lastChunk.length() + length > MAXIMUM_TEXT_CHUNK_SIZE) {
                    chunks.add(thisStr);
                } else {
                    chunks.set(chunks.size() - 1, lastChunk + thisStr);
                }
            }
        };

        AutoDetectParser parser = new AutoDetectParser();
        Metadata metadata = new Metadata();
        try (InputStream stream = SimpleParser.class.getResourceAsStream("test2.doc")) {
            parser.parse(stream, handler, metadata);
            return chunks;
        }
    }

    //TODO: what's the MicrosoftTranslator, where to find it?
//    public String microsoftTranslateToFrench(String text) {
//        MicrosoftTranslator translator = new MicrosoftTranslator();
//        // Change the id and secret! See http://msdn.microsoft.com/en-us/library/hh454950.aspx.
//        translator.setId("dummy-id");
//        translator.setSecret("dummy-secret");
//        try {
//            return translator.translate(text, "fr");
//        } catch (Exception e) {
//            return "Error while translating.";
//        }
//    }

    //Tika provides support for identifying the language of text, through the LanguageIdentifier class.
    public String identifyLanguage(String text) {
        LanguageIdentifier identifier = new LanguageIdentifier(text);
        return identifier.getLanguage();
    }

}
