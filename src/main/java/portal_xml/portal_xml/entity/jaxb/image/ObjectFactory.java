
package portal_xml.portal_xml.entity.jaxb.image;


import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the CrawlApplication.CrawlApplication.jaxb.forecast package.
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

    private final static QName _Images_QNAME = new QName("https://www.images.com", "images");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: CrawlApplication.CrawlApplication.jaxb.forecast
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Images }
     * 
     */
    public Images createImages() {
        return new Images();
    }

    /**
     * Create an instance of {@link Image }
     * 
     */
    public Image createImage() {
        return new Image();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Images }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Images }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.images.com", name = "images")
    public JAXBElement<Images> createImages(Images value) {
        return new JAXBElement<Images>(_Images_QNAME, Images.class, null, value);
    }

}
