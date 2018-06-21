package CrawlApplication.CrawlApplication.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class News {
    private long id;
    private String postImgUrl;
    private String postOriginUrl;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id &&
                Objects.equals(postImgUrl, news.postImgUrl) &&
                Objects.equals(postOriginUrl, news.postOriginUrl);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, postImgUrl, postOriginUrl);
    }
}
