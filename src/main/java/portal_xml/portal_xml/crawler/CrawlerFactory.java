package portal_xml.portal_xml.crawler;

import portal_xml.portal_xml.service.DBService;
import portal_xml.portal_xml.utility.ErrorFileConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CrawlerFactory {

    public static <T extends AbstractCrawler> AbstractCrawler newInstance(DBService service, ErrorFileConfig errorFileConfig, Class<T> crawler) {
        try {
            Constructor<T> ctor = crawler.getDeclaredConstructor(DBService.class, ErrorFileConfig.class);
            return ctor.newInstance(service, errorFileConfig);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends AbstractCrawler> AbstractCrawler[] newInstance(DBService service, ErrorFileConfig errorFileConfig, Class<T>[] crawlers) {
        AbstractCrawler[] crawlersObjArr = new AbstractCrawler[crawlers.length];
        for (int i = 0; i < crawlers.length; i++) {
            crawlersObjArr[i] = newInstance(service, errorFileConfig, crawlers[i]);
        }
        return crawlersObjArr;
    }

}
