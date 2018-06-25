
package portal_xml.portal_xml.Entity.Jaxb.Forecast;


import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for forecasts complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="forecasts"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="forecast" type="{https://www.forecast.com}forecast" maxOccurs="7"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "forecasts", propOrder = {
    "forecast"
})
@XmlRootElement(name = "forecasts")
public class Forecasts {

    @XmlElement(required = true)
    protected List<Forecast> forecast;

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
     * {@link Forecast }
     * 
     * 
     */
    public List<Forecast> getForecast() {
        if (forecast == null) {
            forecast = new ArrayList<Forecast>();
        }
        return this.forecast;
    }

    public void setForecast(List<Forecast> list){
        this.forecast = list;
    }

}
