package portal_xml.portal_xml.controller;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import portal_xml.portal_xml.entity.jaxb.capital.Capital;
import portal_xml.portal_xml.entity.jaxb.capital.Capitals;
import portal_xml.portal_xml.entity.jaxb.forecast.Forecasts;
import portal_xml.portal_xml.entity.jaxb.image.Images;
import portal_xml.portal_xml.entity.jaxb.news.News;
import portal_xml.portal_xml.entity.jaxb.news.Stories;
import portal_xml.portal_xml.service.DBService;
import portal_xml.portal_xml.utility.XMLUtilities;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.*;

@RestController
@CrossOrigin
public class HomeController {

    DBService service;
    XMLUtilities xmlUtilities;

    public HomeController(DBService service, XMLUtilities xmlUtilities) {
        this.service = service;
        this.xmlUtilities = xmlUtilities;
    }

    @RequestMapping(value = "/")
    public ModelAndView home(HttpServletRequest request) {
        String country = request.getLocale().getCountry();
        country = country.replaceAll("\\s*", "");
        String xmlStringForecast = xmlUtilities.marshal(Forecasts.class, getForecastByCode(country));
        String xmlStringCapitals = xmlUtilities.marshal(Capitals.class, getAllCapitals());

        String xmlStringCurrentCapital = xmlUtilities.marshal(Capitals.class, getCapitalByCode(country));


        String xmlStringNews = xmlUtilities.marshal(Stories.class, getNews("1", "10"));



        String xmlStringImage = xmlUtilities.marshal(Images.class, getImagesByCode(country));
        request.setAttribute("forecasts", xmlStringForecast);
        request.setAttribute("capitals", xmlStringCapitals);
        request.setAttribute("images", xmlStringImage);
        request.setAttribute("news", xmlStringNews);
        request.setAttribute("currentCapital", xmlStringCurrentCapital);
        request.setAttribute("totalNewsPages", getTotalPages("1", "10"));
        return new ModelAndView("home");
    }

    @RequestMapping(value = "/{code}/images", produces = APPLICATION_XML_VALUE)
    public Images getImagesByCode(@PathVariable String code) {
        Images images = service.getImagesByCode(code);
        return images;
    }

    @RequestMapping(value = "/{code}/forecasts", produces = APPLICATION_XML_VALUE)
    public Forecasts getForecastByCode(@PathVariable String code) {
        Forecasts forecasts = service.getForecastsByCode(code);
        return forecasts;
    }

    @RequestMapping(value = "/totalpages")
    public Integer getTotalPages(@RequestParam(defaultValue = "1", name = "page") String page,
                                 @RequestParam(defaultValue = "10", name = "size") String size) {
        Page<News> stories = service.getNews(Integer.parseInt(page), Integer.parseInt(size));
        return stories.getTotalPages();
    }

    @RequestMapping(value = "/news", produces = APPLICATION_XML_VALUE)
    public Stories getNews(@RequestParam(defaultValue = "1", name = "page") String page,
                              @RequestParam(defaultValue = "10", name = "size") String size) {
        Stories stories = new Stories();
        stories.setNews(service.getNews(Integer.parseInt(page), Integer.parseInt(size)).getContent());
        return stories;
    }

    //    @RequestMapping(value = "/capitals", produces = MediaType.APPLICATION_XML_VALUE)
    public Capitals getAllCapitals() {
        Capitals capitals = service.getAllCapital();
        return capitals;
    }

    //    @RequestMapping(value = "/capitals/{code}", produces = MediaType.APPLICATION_XML_VALUE)
    public Capitals getCapitalByCode(@PathVariable String code) {
        Capital capital = service.getCapitalByCode(code.toUpperCase());
        List<Capital> capitalList = new ArrayList<>();
        capitalList.add(capital);
        Capitals capitals = new Capitals();
        capitals.setCapital(capitalList);
        return capitals;
    }
}
