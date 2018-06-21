package CrawlApplication.CrawlApplication.Utility;

import CrawlApplication.CrawlApplication.EnumPage.CrawlPage;
import com.sun.codemodel.JCodeModel;
import com.sun.tools.xjc.api.ErrorListener;
import com.sun.tools.xjc.api.S2JJAXBModel;
import com.sun.tools.xjc.api.SchemaCompiler;
import com.sun.tools.xjc.api.XJC;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
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
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;
import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void parseSchemaToJAXBClass(String output, String schemaFilePath, String packageName) {
        try {
            SchemaCompiler sc = XJC.createSchemaCompiler();
            sc.setErrorListener(new ErrorListener() {
                @Override
                public void error(SAXParseException e) {
                    System.out.println("error "+e.toString());
                }

                @Override
                public void fatalError(SAXParseException e) {
                    System.out.println("fatal "+e.toString());
                }

                @Override
                public void warning(SAXParseException e) {
                    System.out.println("warning "+e.toString());
                }

                @Override
                public void info(SAXParseException e) {
                    System.out.println("info "+e.toString());
                }
            });
            sc.forcePackageName("CrawlApplication.CrawlApplication.Jaxb."+packageName);
            File schema = new File(schemaFilePath);
            InputSource is = new InputSource(schema.toURI().toString());
            sc.parseSchema(is);
            S2JJAXBModel model = sc.bind();
            JCodeModel code = model.generateCode(null, null);
            code.build(new File(output));
            System.out.println("Parse done");
        } catch (IOException ioe) {
            ioe.printStackTrace();;
        }
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

    public boolean validateXML(String schemaFilePath){
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
