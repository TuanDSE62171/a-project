package portal_xml.portal_xml.Entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
public class News {
    private long id;
    private String title;
    private String postImgUrl;
    private String postOriginUrl;
    private boolean isHotNews;
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
