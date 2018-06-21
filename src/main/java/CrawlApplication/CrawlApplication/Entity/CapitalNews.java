package CrawlApplication.CrawlApplication.Entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class CapitalNews {
    private long id;
    private long newsId;
    private String capitalIso2Code;

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
    @Column(name = "NewsId")
    public long getNewsId() {
        return newsId;
    }

    public void setNewsId(long newsId) {
        this.newsId = newsId;
    }

    @Basic
    @Column(name = "CapitalISO2Code")
    public String getCapitalIso2Code() {
        return capitalIso2Code;
    }

    public void setCapitalIso2Code(String capitalIso2Code) {
        this.capitalIso2Code = capitalIso2Code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapitalNews that = (CapitalNews) o;
        return id == that.id &&
                newsId == that.newsId &&
                Objects.equals(capitalIso2Code, that.capitalIso2Code);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, newsId, capitalIso2Code);
    }
}
