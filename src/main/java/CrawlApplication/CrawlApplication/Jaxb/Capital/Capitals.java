
package CrawlApplication.CrawlApplication.Jaxb.Capital;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.*;


/**
 * <p>Java class for capitals complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="capitals"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="capital" type="{https://www.capitals.com}capitalItem" maxOccurs="unbounded"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "capitals", propOrder = {
    "capital"
})
@XmlRootElement(name = "capitals")
public class Capitals {

    @XmlElement(required = true)
    protected List<CapitalItem> capital;

    /**
     * Gets the value of the capital property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the capital property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCapital().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CapitalItem }
     * 
     * 
     */
    public List<CapitalItem> getCapital() {
        if (capital == null) {
            capital = new ArrayList<CapitalItem>();
        }
        return this.capital;
    }

}
