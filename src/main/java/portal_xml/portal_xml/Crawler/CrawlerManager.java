package portal_xml.portal_xml.Crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import portal_xml.portal_xml.Entity.Jaxb.Capital.Capital;
import portal_xml.portal_xml.EnumPage.CrawlPage;
import portal_xml.portal_xml.Service.DBService;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Component
public class CrawlerManager {

    public boolean started = false;

    public DBService service;

    private ExecutorService es;

    private List<AbstractCrawler> crawlerList;

    public static Future<List<Capital>> capitalFuture;

    @Autowired
    CrawlerManager(DBService service) {
        this.service = service;
        init();
    }

    public void init() {
        if (!started) {

            AbstractCrawler[] crawlers = CrawlerFactory.newInstance(service, new Class[]{
                    CapitalCrawler.class,
                    ForecastCrawler.class,
                    NewsCrawler.class,
                    ImageCrawler.class
            });

            es = Executors.newFixedThreadPool(crawlers.length);

            crawlerList = Arrays.asList(crawlers);

            started = true;
            startCrawlers();
        }
    }

    public void startCrawlers() {
        for (AbstractCrawler crawler : crawlerList) {
            if (crawler.getClass().getSimpleName().equalsIgnoreCase("CapitalCrawler")) {
                capitalFuture = es.submit(crawler);
            } else {
                es.submit(crawler);
            }
        }
    }

    public <T extends AbstractCrawler> double getProgress(Class<T> crawlerType) {
        for (AbstractCrawler crawler : crawlerList) {
            if (crawlerType.isInstance(crawler)) {
                return crawler.progress;
            }
        }
        return 0.0;
    }

    public <T extends AbstractCrawler> void stopCrawler(Class<T> crawlerType, boolean stop) {
        for (AbstractCrawler crawler : crawlerList) {
            if (crawlerType.isInstance(crawler)) {
                if (crawler.stop != stop) {
                    crawler.stop = stop;
                    if (!stop) {
                        synchronized (crawler) {
                            crawler.notify();
                        }
                    }
                }

            }
        }
    }
}
