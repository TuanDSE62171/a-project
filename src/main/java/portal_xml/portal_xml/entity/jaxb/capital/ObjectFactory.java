
package portal_xml.portal_xml.entity.jaxb.capital;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the CrawlApplication.CrawlApplication.jaxb.capital package.
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

    private final static QName _Capitals_QNAME = new QName("https://www.capitals.com", "capitals");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: CrawlApplication.CrawlApplication.jaxb.capital
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Capitals }
     * 
     */
    public Capitals createCapitals() {
        return new Capitals();
    }

    /**
     * Create an instance of {@link Capital }
     * 
     */
    public Capital createCapitalItem() {
        return new Capital();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Capitals }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Capitals }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.capitals.com", name = "capitals")
    public JAXBElement<Capitals> createCapitals(Capitals value) {
        return new JAXBElement<Capitals>(_Capitals_QNAME, Capitals.class, null, value);
    }

}
