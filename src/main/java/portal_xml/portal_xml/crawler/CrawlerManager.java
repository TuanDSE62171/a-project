package portal_xml.portal_xml.crawler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import portal_xml.portal_xml.service.DBService;
import portal_xml.portal_xml.utility.ErrorFileConfig;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class CrawlerManager {

    public boolean started = false;

    public DBService service;

    public ErrorFileConfig errorFileConfig;

    private ExecutorService es;

    public static List<AbstractCrawler> crawlerList;

    public static int capitalSize;

    @Autowired
    CrawlerManager(DBService service, ErrorFileConfig errorFileConfig) {
        this.service = service;
        this.errorFileConfig = errorFileConfig;
        init();
    }

    @SuppressWarnings("unchecked") // crawlers will always extend AbstractCrawler
    public void init() {
        if (!started) {

            AbstractCrawler[] crawlers = CrawlerFactory.newInstance(service, errorFileConfig, new Class[]{
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
            es.submit(crawler);
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

    public void stopCrawlers(boolean stop){
        for(AbstractCrawler crawler : crawlerList){
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
