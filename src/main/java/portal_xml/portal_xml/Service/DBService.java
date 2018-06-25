package portal_xml.portal_xml.Service;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import portal_xml.portal_xml.Entity.Jaxb.Capital.Capital;
import portal_xml.portal_xml.Entity.Jaxb.Forecast.Forecast;
import portal_xml.portal_xml.Repository.CapitalRepository;
import portal_xml.portal_xml.Repository.ForecastRepository;
import portal_xml.portal_xml.Repository.ImageRepository;
import portal_xml.portal_xml.Repository.NewsRepository;

import java.util.List;

@Service
public class DBService {

    CapitalRepository capitalRepository;
    ForecastRepository forecastRepository;
    NewsRepository newsRepository;
    ImageRepository imageRepository;

    public DBService(CapitalRepository capitalRepository, ForecastRepository forecastRepository, NewsRepository newsRepository, ImageRepository imageRepository) {
        this.capitalRepository = capitalRepository;
        this.forecastRepository = forecastRepository;
        this.newsRepository = newsRepository;
        this.imageRepository = imageRepository;
    }

    public List<Capital> getAllCapital() {
        return capitalRepository.findAll();
    }

    public List<Forecast> getForecastsByCode(String code) {
        return forecastRepository.findTop7ByCapitalIso2CodeOrderByForecastDateAsc(code);
    }

    public List<Forecast> getAllRecentForecasts() {
        Sort sort = new Sort("ForecastDate")
                .ascending();
        return forecastRepository.findAll(sort);
    }
}
