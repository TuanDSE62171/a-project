package portal_xml.portal_xml.Entity.Jaxb.News;


import portal_xml.portal_xml.Utility.GregorianCalendarDateAdapter;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.sql.Date;
import java.util.Objects;

@Entity
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "news", propOrder = {
        "title",
        "postImgUrl",
        "postOriginUrl",
        "isHotNews",
        "date"
})
public class News {

    @XmlTransient
    private long id;
    @XmlElement(required = true)
    private String title;
    @XmlElement(required = true)
    private String postImgUrl;
    @XmlElement(required = true)
    private String postOriginUrl;
    @XmlElement(required = true)
    private boolean isHotNews;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(GregorianCalendarDateAdapter.class)
    private Date date;


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
    @Column(name = "Title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "PostImgUrl")
    public String getPostImgUrl() {
        return postImgUrl;
    }

    public void setPostImgUrl(String postImgUrl) {
        this.postImgUrl = postImgUrl;
    }

    @Basic
    @Column(name = "PostOriginUrl")
    public String getPostOriginUrl() {
        return postOriginUrl;
    }

    public void setPostOriginUrl(String postOriginUrl) {
        this.postOriginUrl = postOriginUrl;
    }

    @Basic
    @Column(name = "IsHotNews")
    public boolean isHotNews() {
        return isHotNews;
    }

    public void setHotNews(boolean hotNews) {
        isHotNews = hotNews;
    }

    @Basic
    @Column(name = "Date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id &&
                isHotNews == news.isHotNews &&
                Objects.equals(title, news.title) &&
                Objects.equals(postImgUrl, news.postImgUrl) &&
                Objects.equals(postOriginUrl, news.postOriginUrl) &&
                Objects.equals(date, news.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, title, postImgUrl, postOriginUrl, isHotNews, date);
    }
}
