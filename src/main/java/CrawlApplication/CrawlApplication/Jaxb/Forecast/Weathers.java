
package CrawlApplication.CrawlApplication.Jaxb.Forecast;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for weathers complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="weathers"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="weather" type="{https://www.forecast.com}weatherItem" maxOccurs="7"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "weathers", propOrder = {
    "weather"
})
@XmlRootElement(name = "weathers")
public class Weathers {

    @XmlElement(required = true)
    protected List<WeatherItem> weather;

    /**
     * Gets the value of the weather property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the weather property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWeather().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WeatherItem }
     * 
     * 
     */
    public List<WeatherItem> getWeather() {
        if (weather == null) {
            weather = new ArrayList<WeatherItem>();
        }
        return this.weather;
    }

}
