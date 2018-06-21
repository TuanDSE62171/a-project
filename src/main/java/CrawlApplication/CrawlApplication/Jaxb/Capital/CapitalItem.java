
package CrawlApplication.CrawlApplication.Jaxb.Capital;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for capitalItem complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="capitalItem"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;attribute name="capitalName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="countryName" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="alphaTwoCode" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "capitalItem")
public class CapitalItem {

    @XmlAttribute(name = "capitalName")
    protected String capitalName;
    @XmlAttribute(name = "countryName")
    protected String countryName;
    @XmlAttribute(name = "alphaTwoCode")
    protected String alphaTwoCode;

    /**
     * Gets the value of the capitalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapitalName() {
        return capitalName;
    }

    /**
     * Sets the value of the capitalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapitalName(String value) {
        this.capitalName = value;
    }

    /**
     * Gets the value of the countryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the value of the countryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryName(String value) {
        this.countryName = value;
    }

    /**
     * Gets the value of the alphaTwoCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAlphaTwoCode() {
        return alphaTwoCode;
    }

    /**
     * Sets the value of the alphaTwoCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAlphaTwoCode(String value) {
        this.alphaTwoCode = value;
    }

}
