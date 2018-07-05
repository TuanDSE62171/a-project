package portal_xml.portal_xml.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import portal_xml.portal_xml.Crawler.*;

import static org.springframework.http.MediaType.*;

@RestController
@CrossOrigin
public class CrawlerController {

    public CrawlerManager cm;

    public CrawlerController(CrawlerManager cm) {
        this.cm = cm;
    }

    @RequestMapping(value = "/crawlers")
    public ModelAndView crawlerHome() {
        return new ModelAndView("crawler");
    }

    @RequestMapping(value = "/crawlers/signin", method = RequestMethod.POST)
    public String signin(String code) {
        return "false";
    }

//    @RequestMapping(value = "/crawlers/{op}", produces = TEXT_PLAIN_VALUE)
//    public String operateCrawlers(@PathVariable String op) {
//        if (op.equalsIgnoreCase("start")) {
//            if(cm.started){
//                cm.resumeCrawlers();
//                return "resumed";
//            }
//            cm.startCrawlers();
//            return "running";
//        } else if (op.equalsIgnoreCase("pause")) {
//            cm.stopCrawlers();
//            return "paused";
//        }
//        return "invalid";
//    }

    @RequestMapping(value = "/crawler/progress/{crawler}", produces = TEXT_PLAIN_VALUE)
    public String progress(@PathVariable String crawler) {
        switch (crawler.toLowerCase()) {
            case "capital":
                return String.format("%.2f", cm.getProgress(CapitalCrawler.class));
            case "news":
                return String.format("%.2f", cm.getProgress(NewsCrawler.class));
            case "image":
                return String.format("%.2f", cm.getProgress(ImageCrawler.class));
            case "forecast":
                return String.format("%.2f", cm.getProgress(ForecastCrawler.class));
            default:
                return String.format("%.2f", 0.0);
        }
    }

    @RequestMapping(value = "/crawler/{name}/{op}")
    public String operateCrawler(@PathVariable String name, @PathVariable String op){
        boolean stop = op.equalsIgnoreCase("pause");
        switch(name.toLowerCase()){
            case "capital":
                cm.stopCrawler(CapitalCrawler.class, stop);
                break;
            case "news":
                cm.stopCrawler(NewsCrawler.class, stop);
                break;
            case "image":
                cm.stopCrawler(ImageCrawler.class, stop);
                break;
            case "forecast":
                cm.stopCrawler(ForecastCrawler.class, stop);
                break;
        }
        return "";
    }

}
