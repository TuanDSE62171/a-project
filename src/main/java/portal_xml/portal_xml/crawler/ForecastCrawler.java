package portal_xml.portal_xml.crawler;

import javafx.util.Pair;
import portal_xml.portal_xml.entity.jaxb.capital.Capital;
import portal_xml.portal_xml.entity.jaxb.forecast.Forecast;
import portal_xml.portal_xml.entity.jaxb.forecast.Forecasts;
import portal_xml.portal_xml.service.DBService;
import portal_xml.portal_xml.utility.ErrorFileConfig;
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

    public ForecastCrawler(DBService service, ErrorFileConfig errorFileConfig) {
        this.pageURL = WEATHER_PAGE;
        this.service = service;
        this.capitalBlockingQueue = new LinkedBlockingDeque<>();
        this.errorFileConfig = errorFileConfig;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
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

        while (true) {
            setupLogger();
            LOGGER.log(INFO, templateStarted);
            int totalProgress;
            try {
                Capital capital = waitForQueueToContinue(this.capitalBlockingQueue);
                setupLogger();
                if(isTerminationFlag(capital)){
                    counter = 1;
                    LOGGER.log(INFO, templateFinished);
                    this.stop = true;
                    closeLogger();
                }
                totalProgress = (CrawlerManager.capitalSize);
                while (this.stop) {
                    waitForSignalToContinue();
                }
                if(isTerminationFlag(capital)){
                    resetProgress();
                    capital = waitForQueueToContinue(this.capitalBlockingQueue); // restart after finished
                    setupLogger();
                }
                LOGGER.log(INFO, capital.getCountryName() + " " + capital.getName());

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
                LOGGER.log(INFO, "Attempt: " + formatedString);
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
                    LOGGER.log(WARNING, "Data for " + capital.getName() + " of " + capital.getCountryName() + " not found");
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
                            LOGGER.log(INFO, "Forecast for " + capital.getName() + " date: " + Date.valueOf(date) + " saved successful");
                            date = date.plusDays(1);
                        }
                    }
                    LOGGER.log(INFO, forecasts.getForecast().size() + " record found for capital " + capital.getName() + " of " + capital.getCountryName());
                }
                progress = ((counter++ / totalProgress) * 100);
                utilities.reset();
                sb.setLength(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
