package portal_xml.portal_xml.Crawler;

import portal_xml.portal_xml.Entity.Jaxb.Capital.Capital;
import portal_xml.portal_xml.Entity.Jaxb.Capital.Capitals;
import portal_xml.portal_xml.Service.DBService;
import portal_xml.portal_xml.Utility.XMLUtilities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Level;

import static java.util.logging.Level.*;
import static portal_xml.portal_xml.EnumPage.CrawlPage.*;
import static portal_xml.portal_xml.HelperFunction.HelperFunction.getSubStringRegEx;

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
                .unmarshal(Capitals.class, "xsd/Capital.xsd");

        int totalProgress = capitals.getCapital().size();

        if (capitals != null) {

            double counter = 1;
            for(Capital capital : capitals.getCapital()){
                while(this.stop){
                    LOGGER.log(INFO, templateStopped);
                    waitForSignalToContinue();
                    LOGGER.log(INFO, templateResumed);
                }
                service.saveCapital(capital);
                LOGGER.log(INFO, "{0}: Capital " + capital.getName() + " Code: " + capital.getIso2Code() + " saved successful", crawlerName);
                progress = ((counter++/totalProgress)*100);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LOGGER.log(INFO, templateDestroy);
            return capitals.getCapital();
        }
        LOGGER.log(INFO, templateDestroy);
        return new ArrayList<>();
    }
}
