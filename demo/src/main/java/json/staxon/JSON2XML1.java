package json.staxon;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLStreamWriter;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stax.StAXResult;
import javax.xml.transform.stax.StAXSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Author: Johnny
 * Date: 2016/12/9
 * Time: 20:23
 */
public class JSON2XML1 {
    /**
     * Copy/format JSON as XML using {@Transformer #transform( Source, Result)}.
     *
     * @param args ignored
     */
	//as it, JSON2XML2 will use XMLEventReader, XMLEventWriter
    public static void main(String[] args) throws TransformerException, XMLStreamException, IOException {
        /*
         * If the <code>multiplePI</code> property is
         * set to <code>true</code>, the StAXON reader will generate
         * <code>&lt;xml-multiple&gt;</code> processing instructions
         * which would be copied to the XML output.
         * These can be used by StAXON when converting back to JSON
         * to trigger array starts.
         * Set to <code>false</code> if you don't need to go back to JSON.
         */
        JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).build();
        try (InputStream input = JSON2XML1.class.getResourceAsStream("input.json"); OutputStream output = System.out) {
            //Create source (JSON).
            XMLStreamReader reader = new JsonXMLInputFactory(config).createXMLStreamReader(input);
            Source source = new StAXSource(reader);

            //Create result (XML).
            XMLStreamWriter writer = XMLOutputFactory.newInstance().createXMLStreamWriter(output);
            Result result = new StAXResult(new PrettyXMLStreamWriter(writer)); // format output

            //Copy source to result via "identity transform".
            TransformerFactory.newInstance().newTransformer().transform(source, result);
        }
        //As per StAX specification, XMLStreamReader/Writer.close() doesn't close the underlying stream.
    }
}
