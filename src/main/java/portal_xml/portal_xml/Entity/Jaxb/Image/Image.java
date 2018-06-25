package portal_xml.portal_xml.Entity.Jaxb.Image;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Objects;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "image", propOrder = {
        "iso2Code",
        "width",
        "height",
        "url"
})
public class Image {

    @XmlTransient
    private long id;

    @XmlElement(required = true)
    private String iso2Code;
    @XmlElement(required = true)
    private int width;
    @XmlElement(required = true)
    private int height;
    @XmlElement(required = true)
    private String url;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ISO2Code")
    public String getIso2Code() {
        return iso2Code;
    }

    public void setIso2Code(String iso2Code) {
        this.iso2Code = iso2Code;
    }

    @Basic
    @Column(name = "Width")
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Basic
    @Column(name = "Height")
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Basic
    @Column(name = "Url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id &&
                Objects.equals(iso2Code, image.iso2Code) &&
                Objects.equals(width, image.width) &&
                Objects.equals(height, image.height) &&
                Objects.equals(url, image.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, iso2Code, width, height, url);
    }
}
