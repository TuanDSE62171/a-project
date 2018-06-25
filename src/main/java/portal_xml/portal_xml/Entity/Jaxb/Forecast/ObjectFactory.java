
package portal_xml.portal_xml.Entity.Jaxb.Forecast;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the CrawlApplication.CrawlApplication.Jaxb.Forecast package. 
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

    private final static QName _Weathers_QNAME = new QName("https://www.forecast.com", "forecasts");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: CrawlApplication.CrawlApplication.Jaxb.Forecast
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Forecasts }
     * 
     */
    public Forecasts createForecasts() {
        return new Forecasts();
    }

    /**
     * Create an instance of {@link Forecast }
     * 
     */
    public Forecast createForecast() {
        return new Forecast();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Forecasts }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link Forecasts }{@code >}
     */
    @XmlElementDecl(namespace = "https://www.forecast.com", name = "forecasts")
    public JAXBElement<Forecasts> createWeathers(Forecasts value) {
        return new JAXBElement<Forecasts>(_Weathers_QNAME, Forecasts.class, null, value);
    }

}
