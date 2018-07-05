package portal_xml.portal_xml.crawler;

import portal_xml.portal_xml.entity.jaxb.capital.Capital;
import portal_xml.portal_xml.entity.jaxb.capital.Capitals;
import portal_xml.portal_xml.service.DBService;
import portal_xml.portal_xml.utility.XMLUtilities;

import java.util.ArrayList;
import java.util.List;

import static java.util.logging.Level.*;
import static portal_xml.portal_xml.enumpage.CrawlPage.*;
import static portal_xml.portal_xml.helperfunction.HelperFunction.getSubStringRegEx;

public class CapitalCrawler extends AbstractCrawler {

    public CapitalCrawler(DBService service) {
        this.pageURL = CAPITAL_PAGE;
        this.service = service;
    }

    @Override
    public List<Capital> call() {
        LOGGER.log(INFO, templateInit);
        while(this.stop){
            LOGGER.log(INFO, templateStopped);
            waitForSignalToContinue();
            LOGGER.log(INFO, templateResumed);
        }
        XMLUtilities utilities = new XMLUtilities();
        String resultHTML = crawlHTML(null);
        utilities.setResult(resultHTML);

        Capitals capitals = utilities
                .welformHTML(getSubStringRegEx("<table", "</table>"), null, null)
                .transform("xsl/capital.xsl")
                .unmarshal(Capitals.class, "xsd/capital.xsd");

        int totalProgress = capitals.getCapital().size();

        if (capitals != null) {
            CrawlerManager.capitalSize = capitals.getCapital().size();
            double counter = 1;
            for(Capital capital : capitals.getCapital()){
                while(this.stop){
                    LOGGER.log(INFO, templateStopped);
                    waitForSignalToContinue();
                    LOGGER.log(INFO, templateResumed);
                }
                service.saveCapital(capital);
                for(AbstractCrawler crawler : CrawlerManager.crawlerList){
                    if(crawler.capitalBlockingQueue != null){
                        try {
                            crawler.capitalBlockingQueue.put(capital);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                LOGGER.log(INFO, "{0}: capital " + capital.getName() + " Code: " + capital.getIso2Code() + " saved successful", crawlerName);
                progress = ((counter++/totalProgress)*100);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for(AbstractCrawler crawler : CrawlerManager.crawlerList){
                if(crawler.capitalBlockingQueue != null){
                    try {
                        crawler.capitalBlockingQueue.put(AbstractCrawler.CAPITAL_QUEUE_TERMINATION_FLAG);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            LOGGER.log(INFO, templateDestroy);
            return capitals.getCapital();
        }
        LOGGER.log(INFO, templateDestroy);
        return new ArrayList<>();
    }
}
