package portal_xml.portal_xml.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import portal_xml.portal_xml.Entity.Jaxb.Forecast.Forecast;
import portal_xml.portal_xml.Entity.Jaxb.Forecast.Forecasts;
import portal_xml.portal_xml.Service.DBService;
import portal_xml.portal_xml.Utility.XMLUtilities;

import java.util.List;

@Controller
public class HomeController {

    DBService service;
    XMLUtilities xmlUtilities;

    public HomeController(DBService service, XMLUtilities xmlUtilities){
        this.service = service;
        this.xmlUtilities = xmlUtilities;
    }

    @RequestMapping(value = "/")
    public ModelAndView test(){
        List<Forecast> result = service.getAllRecentForecasts();
        Forecasts forecasts = new Forecasts();
        forecasts.setForecast(result);
        String xmlString = xmlUtilities.marshal(Forecasts.class, forecasts);
        return new ModelAndView("home", "forecasts", xmlString);
    }
}
