package CrawlApplication.CrawlApplication.Crawler;

import CrawlApplication.CrawlApplication.EnumPage.CrawlPage;
import javafx.util.Pair;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CrawlerFactory {

    public static <T extends AbstractCrawler> AbstractCrawler newInstance(Pair<Class<T>, CrawlPage> crawlerInitInfo){
        try {
            Class<? extends AbstractCrawler> clazz = crawlerInitInfo.getKey();
            CrawlPage pageURL = crawlerInitInfo.getValue();
            Constructor<? extends AbstractCrawler> ctor = clazz.getDeclaredConstructor(CrawlPage.class);
            AbstractCrawler crawler = ctor.newInstance(pageURL);
            return crawler;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T extends AbstractCrawler> List<AbstractCrawler> newInstance(Pair<Class<T>, CrawlPage>[] crawlerInitInfoList){
        List<AbstractCrawler> list = new ArrayList<>();
        for(int i = 0; i < crawlerInitInfoList.length; i++){
            list.add(newInstance(crawlerInitInfoList[i]));
        }
        return list;
    }

}
