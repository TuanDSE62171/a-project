package portal_xml.portal_xml.Entity.Jaxb.Capital;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "capital")
public class Capital {

    @XmlAttribute(name = "iso2Code")
    private String iso2Code;
    @XmlAttribute(name = "name")
    private String name;
    @XmlAttribute(name = "countryName")
    private String countryName;

    @Id
    @Column(name = "ISO2Code")
    public String getIso2Code() {
        return iso2Code;
    }

    public void setIso2Code(String iso2Code) {
        this.iso2Code = iso2Code;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "CountryName")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capital capital = (Capital) o;
        return Objects.equals(iso2Code, capital.iso2Code) &&
                Objects.equals(name, capital.name) &&
                Objects.equals(countryName, capital.countryName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(iso2Code, name, countryName);
    }
}
