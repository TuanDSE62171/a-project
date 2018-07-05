package portal_xml.portal_xml.crawler;

import javafx.util.Pair;
import portal_xml.portal_xml.entity.jaxb.capital.Capital;
import portal_xml.portal_xml.entity.jaxb.forecast.Forecast;
import portal_xml.portal_xml.entity.jaxb.forecast.Forecasts;
import portal_xml.portal_xml.service.DBService;
import portal_xml.portal_xml.utility.XMLUtilities;

import java.sql.Date;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.LinkedBlockingDeque;


import static java.util.logging.Level.*;
import static portal_xml.portal_xml.enumpage.CrawlPage.*;
import static portal_xml.portal_xml.helperfunction.HelperFunction.getReplaceAllInvalidTokens;
import static portal_xml.portal_xml.helperfunction.HelperFunction.getSubStringRegEx;

public class ForecastCrawler extends AbstractCrawler {

    public ForecastCrawler(DBService service) {
        this.pageURL = WEATHER_PAGE;
        this.service = service;
        this.capitalBlockingQueue = new LinkedBlockingDeque<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object call() {
        LOGGER.log(INFO, templateInit);
        XMLUtilities utilities = new XMLUtilities();
        Forecasts forecasts = null;
        LocalDate date = null;
        Locale VnLocale = new Locale("vi", "VN");
        StringBuilder sb = new StringBuilder("");
        String timeAndDatePage = WEATHER_PAGE.getUrl();
        double counter = 1;
        String resultHTML = "";
        String formatedString = "";
        String capitalName = "";
        String countryName = "";

        List<Forecast> forecastList = new ArrayList<>();

        int totalProgress;
        try {
            while(true){
                Capital capital = this.capitalBlockingQueue.take();
                if(isTerminationFlag(capital)) break;
                totalProgress = (CrawlerManager.capitalSize * 7);
                while(this.stop){
                    LOGGER.log(INFO, templateStopped);
                    waitForSignalToContinue();
                    LOGGER.log(INFO, templateResumed);
                }
                LOGGER.log(INFO, "{0}: "+capital.getCountryName() + " " + capital.getName(), crawlerName);

                capitalName = Normalizer.normalize(capital.getName(), Normalizer.Form.NFD);
                countryName = Normalizer.normalize(capital.getCountryName(), Normalizer.Form.NFD);

                capitalName = capitalName
                        .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                        .replaceAll("\\s+", "-");

                countryName = countryName
                        .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "")
                        .replaceAll("\\s+", "-");

                formatedString = String.format(timeAndDatePage, countryName, capitalName);
                this.pageURL.setUrl(formatedString);
                LOGGER.log(INFO, "{0}: Attempt: " + formatedString, crawlerName);
                resultHTML = crawlHTML(null);
                utilities.setResult(resultHTML);

                try {
                    utilities
                            .welformHTML(getSubStringRegEx("<div class=main-content-div>", "<\\/script><\\/div>"),
                                    getReplaceAllInvalidTokens(new Pair[]{
                                            new Pair("main-content-div", "\"main-content-div\""),
                                            new Pair("(?s)<script[^>]*>(.*?)<\\/script>", ""),
                                            new Pair("[a-zA-Z]+=[a-zA-Z0-9-_]+", ""),
                                            new Pair("&nbsp;", ""),
                                            new Pair("&copy;", ""),
                                            new Pair("°C", "")
                                    })
                                    , new String[]{"img", "input", "br"});
                } catch (Exception e1) {
                    LOGGER.log(WARNING, "{0}: Data for " + capital.getName() + " of " + capital.getCountryName() + " not found", crawlerName);
                }
                sb = sb.append(utilities.getResult());
                utilities.setResult(sb.toString());
                if (!utilities.getResult().isEmpty() && utilities.getResult() != null) {
                    forecasts = utilities
                            .transform("xsl/forecast.xsl")
                            .unmarshal(Forecasts.class, "xsd/forecast.xsd");
                    if (forecasts != null) {
                        date = LocalDate.now();
                        for (Forecast forecast : forecasts.getForecast()) {
                            Forecast f = new Forecast();
                            forecast.setCapitalIso2Code(capital.getIso2Code());
                            forecast.setForecastDate(Date.valueOf(date));
                            forecast.setForecastDay(date.getDayOfMonth());
                            forecast.setForecastMonth(date.getMonthValue());
                            forecast.setForecastYear(date.getYear());
                            forecast.setForecastDayOfWeek(date.format(DateTimeFormatter.ofPattern("EEEE", VnLocale)));
                            forecastList.add(forecast);
                            service.saveForecast(forecast);
                            counter++;
                            LOGGER.log(INFO,"{0}: forecast for " + capital.getName() + " date: " + Date.valueOf(date) + " saved successful", crawlerName);
                            date = date.plusDays(1);
                        }
                    }
                    LOGGER.log(INFO, "{0}: " + forecasts.getForecast().size() + " record found for capital " + capital.getName() + " of " + capital.getCountryName(), crawlerName);
                }
                progress = ((counter / totalProgress) * 100);
                utilities.reset();
                sb.setLength(0);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LOGGER.log(INFO, templateDestroy);
        return null;
    }
}
