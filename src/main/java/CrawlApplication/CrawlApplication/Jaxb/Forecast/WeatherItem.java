
package CrawlApplication.CrawlApplication.Jaxb.Forecast;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for weatherItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="weatherItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="windVelocity" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="temp" type="{http://www.w3.org/2001/XMLSchema}int"/&gt;
 *         &lt;element name="icon" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "weatherItem", propOrder = {
    "description",
    "windVelocity",
    "temp",
    "icon"
})
public class WeatherItem {

    @XmlElement(required = true)
    protected String description;
    @XmlElement(required = true)
    protected String windVelocity;
    protected int temp;
    @XmlElement(required = true)
    protected String icon;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the windVelocity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWindVelocity() {
        return windVelocity;
    }

    /**
     * Sets the value of the windVelocity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWindVelocity(String value) {
        this.windVelocity = value;
    }

    /**
     * Gets the value of the temp property.
     * 
     */
    public int getTemp() {
        return temp;
    }

    /**
     * Sets the value of the temp property.
     * 
     */
    public void setTemp(int value) {
        this.temp = value;
    }

    /**
     * Gets the value of the icon property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIcon() {
        return icon;
    }

    /**
     * Sets the value of the icon property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIcon(String value) {
        this.icon = value;
    }

}
