package CrawlApplication.CrawlApplication.Crawler;

import CrawlApplication.CrawlApplication.EnumPage.CrawlPage;

public class Crawler extends AbstractCrawler {

    public Crawler(CrawlPage pageURL){
        this.pageURL = pageURL;
    }
}
