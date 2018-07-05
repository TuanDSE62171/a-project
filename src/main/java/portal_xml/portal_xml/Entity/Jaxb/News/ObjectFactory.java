
package portal_xml.portal_xml.Entity.Jaxb.News;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the CrawlApplication.CrawlApplication.Jaxb.News package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Stories_QNAME = new QName("https://www.news.com", "stories");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: CrawlApplication.CrawlApplication.Jaxb.News
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Stories }
     * 
     */
    public Stories createStories() {
        return new Stories();
    }

    /**
     * Create an instance of {@link News }
     * 
     */
    public News createNewsItem() {
        return new News();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Stories }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Stories }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.news.com", name = "stories")
    public JAXBElement<Stories> createStories(Stories value) {
        return new JAXBElement<Stories>(_Stories_QNAME, Stories.class, null, value);
    }

}
