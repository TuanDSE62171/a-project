package CrawlApplication.CrawlApplication;

import CrawlApplication.CrawlApplication.Crawler.AbstractCrawler;
import CrawlApplication.CrawlApplication.Crawler.Crawler;
import CrawlApplication.CrawlApplication.Crawler.CrawlerFactory;
import CrawlApplication.CrawlApplication.Entity.Capital;
import CrawlApplication.CrawlApplication.Entity.Forecast;
import CrawlApplication.CrawlApplication.EnumPage.CrawlPage;
import CrawlApplication.CrawlApplication.Jaxb.Capital.Capitals;
import CrawlApplication.CrawlApplication.Jaxb.Forecast.WeatherItem;
import CrawlApplication.CrawlApplication.Jaxb.Forecast.Weathers;
import CrawlApplication.CrawlApplication.Service.DBService;
import CrawlApplication.CrawlApplication.Utility.XMLUtilities;
import javafx.util.Pair;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Date;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static CrawlApplication.CrawlApplication.HelperFunction.HelperFunction.getReplaceAllInvalidTokens;
import static CrawlApplication.CrawlApplication.HelperFunction.HelperFunction.getSubStringRegEx;

@SpringBootApplication
public class CrawlApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlApplication.class);

    }

    @Bean
    public CommandLineRunner init(DBService service) {
        return args -> {
            XMLUtilities utilities = new XMLUtilities();
//            utilities.parseSchemaToJAXBClass("src/main/java/", "xsd/Capital.xsd", "Capital");
            AbstractCrawler capitalCrawler = CrawlerFactory.newInstance(new Pair<>(Crawler.class, CrawlPage.CAPITAL_PAGE));
            AbstractCrawler weatherCrawler = CrawlerFactory.newInstance(new Pair<>(Crawler.class, CrawlPage.WEATHER_PAGE));
            String capitalHTML = capitalCrawler.crawlHTML(null);
            utilities.setResult(capitalHTML);
            Capitals capitals = utilities
                    .welformHTML(getSubStringRegEx("<table", "</table>"), null, null)
                    .transform("xsl/capital.xsl")
                    .unmarshal(Capitals.class, "xsd/Capital.xsd");

//            capitals.getCapital().forEach(capitalItem -> {
//                Capital capital = new Capital();
//                capital.setName(capitalItem.getCapitalName());
//                capital.setIso2Code(capitalItem.getAlphaTwoCode());
//                capital.setCountryName(capitalItem.getCountryName());
//                service.saveCapital(capital);
//            });
//            utilities.reset();

            List<Capital> capitalList = service.getAllCapitals();
            List<Forecast> forecastList = new ArrayList<>();
            Weathers weathers = null;
            LocalDate date = null;
            Locale VnLocale = new Locale("vi", "VN");
            StringBuilder sb = new StringBuilder("");
            String timeAndDatePage = CrawlPage.WEATHER_PAGE.getUrl();
            int counter = 1;
            String resultHTML = "";
            String formatedString = "";
            String capitalName = "";
            String countryName = "";

            for (Capital capital : capitalList) {
                System.out.println(capital.getCountryName() + " " + capital.getName());

                capitalName = capital.getName().replaceAll("\\s+", "-");
                capitalName = Normalizer.normalize(capitalName, Normalizer.Form.NFD);
                capitalName = capitalName.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

                countryName = capital.getCountryName().replaceAll("\\s+", "-");
                countryName = Normalizer.normalize(countryName, Normalizer.Form.NFD);
                countryName = countryName.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

                formatedString = String.format(timeAndDatePage, countryName, capitalName);

                Thread.sleep(10000);
                weatherCrawler.pageURL.setUrl(formatedString);
                System.out.println("First Attempt: " + formatedString);
                resultHTML = weatherCrawler.crawlHTML(null);
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
                    System.out.println("First Attempt Failed");
                    capitalName = Optional.ofNullable(capital.getNameAlt()).orElse(capital.getName());
                    capitalName = Normalizer.normalize(capitalName, Normalizer.Form.NFD);
                    capitalName = capitalName.replaceAll("\\s+", "-").replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

                    countryName = Optional.ofNullable(capital.getCountryNameAlt()).orElse(capital.getCountryName());
                    countryName = Normalizer.normalize(countryName, Normalizer.Form.NFD);
                    countryName = countryName.replaceAll("\\s+", "-").replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

                    formatedString = String.format(timeAndDatePage, countryName, capitalName);

                    Thread.sleep(10000);
                    weatherCrawler.pageURL.setUrl(formatedString);
                    System.out.println("Second Attempt: " + formatedString);
                    resultHTML = weatherCrawler.crawlHTML(null);
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


                    } catch (Exception e2) {
                        System.out.println("Data for " + capital.getCountryName() + ", " + capital.getName() + " not found");
                    }
                }
                sb = sb.append(utilities.getResult());
                utilities.setResult(sb.toString());
                weathers = utilities
                        .transform("xsl/forecast.xsl")
                        .unmarshal(Weathers.class, "xsd/Forecast.xsd");

                if (weathers != null) {
                    date = LocalDate.now();
                    for (WeatherItem weatherItem : weathers.getWeather()) {
                        date = date.plusDays(1);
                        Forecast forecast = new Forecast();
                        forecast.setCapitalIso2Code(capital.getIso2Code());
                        forecast.setForecastDescription(weatherItem.getDescription());
                        forecast.setForecastWind(weatherItem.getWindVelocity());
                        forecast.setForecastTemp(weatherItem.getTemp());
                        forecast.setForecastIcon(weatherItem.getIcon());
                        forecast.setForecastDate(Date.valueOf(date));
                        forecast.setForecastDay(date.getDayOfMonth());
                        forecast.setForecastMonth(date.getMonthValue());
                        forecast.setForecastYear(date.getYear());
                        forecast.setForecastDayOfWeek(date.format(DateTimeFormatter.ofPattern("EEEE 'ngày' d", VnLocale)));
                        forecastList.add(forecast);
                    }
                }
                utilities.reset();
                System.out.println("******* Counter: " + (counter++) + " *******");
                sb.setLength(0);
                service.saveForecasts(forecastList);
            }

            System.out.println("a");
//            utilities.parseSchemaToJAXBClass("src/main/java/", "xsd/Forecast.xsd", "Forecast");

        };
    }

}
