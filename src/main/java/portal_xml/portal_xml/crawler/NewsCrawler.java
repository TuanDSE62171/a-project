package portal_xml.portal_xml.crawler;

import javafx.util.Pair;
import portal_xml.portal_xml.entity.jaxb.news.News;
import portal_xml.portal_xml.entity.jaxb.news.Stories;
import portal_xml.portal_xml.service.DBService;
import portal_xml.portal_xml.utility.XMLUtilities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import static java.util.logging.Level.*;
import static portal_xml.portal_xml.enumpage.CrawlPage.*;
import static portal_xml.portal_xml.helperfunction.HelperFunction.getReplaceAllInvalidTokens;
import static portal_xml.portal_xml.helperfunction.HelperFunction.getSubStringRegEx;

public class NewsCrawler extends AbstractCrawler {

    public NewsCrawler(DBService service) {
        this.pageURL = NEWS_PAGE;
        this.service = service;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        LOGGER.log(INFO, templateInit);
        XMLUtilities utilities = new XMLUtilities();
        HashMap<String, String> queryParameters = new HashMap<>();
        int totalProgress = (10 * 18);
        while (true) {
            double counter = 1;
            for (int i = 1; i <= 10; i++) {
                while (this.stop) {
                    LOGGER.log(INFO, templateStopped);
                    waitForSignalToContinue();
                    LOGGER.log(INFO, templateResumed);
                }
                queryParameters.put("page", String.valueOf(i));
                LOGGER.log(INFO, "{0}: crawling page {1}", new Object[]{crawlerName, i});
                String newsHTML = crawlHTML(queryParameters);
                utilities.setResult(newsHTML);
                Stories stories = utilities.welformHTML(getSubStringRegEx("<div class=\"w-body list\">\n", "<\\/ul>\\t*\\n*\\s*<\\/div>\\t*\\n*\\s*<\\/div>"),
                        getReplaceAllInvalidTokens(new Pair[]{new Pair("async", ""),
                                new Pair("&raquo;", ""),
                                new Pair("&laquo;", "")
                        }), null)
                        .transform("xsl/news.xsl")
                        .unmarshal(Stories.class, "xsd/news.xsd");

                for (News n : stories.getNews()) {
                    News news = new News();
                    news.setTitle(n.getTitle());
                    news.setPostImgUrl(n.getPostImgUrl());
                    news.setPostOriginUrl(n.getPostOriginUrl());
                    news.setHotNews(n.isHotNews());
                    news.setDate(Date.valueOf(n.getDate().toString()));
                    service.saveNews(n);
                    progress = ((counter++ / totalProgress) * 100);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.stop = true;
            LOGGER.log(INFO, templateFinished);
        }
    }
}
