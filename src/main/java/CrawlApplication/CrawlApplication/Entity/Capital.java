package CrawlApplication.CrawlApplication.Entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Capital {
    private String iso2Code;
    private String name;
    private String nameAlt;
    private String countryName;
    private String countryNameAlt;

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
    @Column(name = "NameAlt")
    public String getNameAlt() {
        return nameAlt;
    }

    public void setNameAlt(String nameAlt) {
        this.nameAlt = nameAlt;
    }

    @Basic
    @Column(name = "CountryName")
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    @Basic
    @Column(name = "CountryNameAlt")
    public String getCountryNameAlt() {
        return countryNameAlt;
    }

    public void setCountryNameAlt(String countryNameAlt) {
        this.countryNameAlt = countryNameAlt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Capital capital = (Capital) o;
        return Objects.equals(iso2Code, capital.iso2Code) &&
                Objects.equals(name, capital.name) &&
                Objects.equals(nameAlt, capital.nameAlt) &&
                Objects.equals(countryName, capital.countryName) &&
                Objects.equals(countryNameAlt, capital.countryNameAlt);
    }

    @Override
    public int hashCode() {

        return Objects.hash(iso2Code, name, nameAlt, countryName, countryNameAlt);
    }
}
