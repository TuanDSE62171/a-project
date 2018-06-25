package portal_xml.portal_xml.Utility;

import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.*;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class XMLUtilities {

    private StringBuilder result;

    public XMLUtilities(){
        result = new StringBuilder("");
    }

    public String getResult(){
        return result.toString();
    }

    public void reset(){this.result = new StringBuilder("");}

    public void setResult(String html){this.result = new StringBuilder(html);}

    public XMLUtilities welformHTML(UnaryOperator<String> subStringFunc,
                                    UnaryOperator<String> replaceAllFunc, String[] selfClosingTags){
        String result;
        Pattern pattern = null;
        Matcher matcher = null;
        StringBuilder temp = null;
        String head, tail, tag;
        if (selfClosingTags == null) {
            selfClosingTags = new String[]{};
        }
        result = this.result.toString();
        for (int i = 0; i < selfClosingTags.length; i++) {
            tag = selfClosingTags[i];
            pattern = Pattern.compile("<(" + tag.toLowerCase() + "|" + tag.toUpperCase() + ")\\s*[^>]*>");
            matcher = pattern.matcher(result);
            String group = "";
            while (matcher.find()) {
                group = matcher.group();
                String replacement = matcher.group().replaceAll("\\/*>", "/>");
                result = result.replace(group, replacement);
            }
        }
        if (subStringFunc != null) {
            result = subStringFunc.apply(result);
        }
        if (replaceAllFunc != null) {
            result = replaceAllFunc.apply(result);
        }
        this.result = new StringBuilder(result);
        System.out.println("Welform done");
        return this;
    }

    public XMLUtilities transform(String templateFilePath){
        try {
            String result = "";
            StringWriter writer = new StringWriter();
            TransformerFactory factory = TransformerFactory.newInstance();
            StreamSource xsltFile = new StreamSource(templateFilePath);
            StreamSource input = new StreamSource(new StringReader(this.result.toString()));
            StreamResult output = new StreamResult(writer);

            Templates template = factory.newTemplates(xsltFile);
            Transformer transformer = template.newTransformer();

            transformer.transform(input, output);
            result = writer.toString();
            this.result = new StringBuilder(result);
            System.out.println("Transformation done");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    public <T> T unmarshal(Class<T> jaxbClass, String schemaFilePath){
        if(validateXML(schemaFilePath)){
            try {
                JAXBContext jc = JAXBContext.newInstance(jaxbClass);
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                T result = (T) unmarshaller.unmarshal(new StringReader(this.result.toString()));
                System.out.println("Unmarshaling done");
                return result;
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public <T> String marshal(Class<T> jaxbClass, T object){
        try{
            StringWriter sw = new StringWriter();
            JAXBContext jc = JAXBContext.newInstance(jaxbClass);
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(object, sw);
            return sw.toString();
        }catch(JAXBException e){
            e.printStackTrace();
        }
        return null;
    }

    private boolean validateXML(String schemaFilePath){
        try {
            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = sf.newSchema(new File(schemaFilePath));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(this.result.toString())));
            return true;
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
