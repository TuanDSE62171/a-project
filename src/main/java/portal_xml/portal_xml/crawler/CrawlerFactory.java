package portal_xml.portal_xml.crawler;

import portal_xml.portal_xml.service.DBService;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CrawlerFactory {

    public static <T extends AbstractCrawler> AbstractCrawler newInstance(DBService service, Class<T> crawler) {
        try {
            Constructor<T> ctor = crawler.getDeclaredConstructor(DBService.class);
            return ctor.newInstance(service);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends AbstractCrawler> AbstractCrawler[] newInstance(DBService service, Class<T>[] crawlers) {
        AbstractCrawler[] crawlersObjArr = new AbstractCrawler[crawlers.length];
        for (int i = 0; i < crawlers.length; i++) {
            crawlersObjArr[i] = newInstance(service, crawlers[i]);
        }
        return crawlersObjArr;
    }

}
