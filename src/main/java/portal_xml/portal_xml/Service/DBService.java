package portal_xml.portal_xml.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import portal_xml.portal_xml.Entity.Jaxb.Capital.Capital;
import portal_xml.portal_xml.Entity.Jaxb.Capital.Capitals;
import portal_xml.portal_xml.Entity.Jaxb.Forecast.Forecast;
import portal_xml.portal_xml.Entity.Jaxb.Forecast.Forecasts;
import portal_xml.portal_xml.Entity.Jaxb.Image.Image;
import portal_xml.portal_xml.Entity.Jaxb.Image.Images;
import portal_xml.portal_xml.Entity.Jaxb.News.News;
import portal_xml.portal_xml.Entity.Jaxb.News.Stories;
import portal_xml.portal_xml.Repository.CapitalRepository;
import portal_xml.portal_xml.Repository.ForecastRepository;
import portal_xml.portal_xml.Repository.ImageRepository;
import portal_xml.portal_xml.Repository.NewsRepository;

import java.util.List;
import java.util.stream.Collectors;

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

    public Capitals getAllCapital() {
        Capitals capitals = new Capitals();
        capitals.setCapital(capitalRepository
                .findAll()
                .stream()
                .peek(capital -> capital.setName(capital.getName().replaceAll("'", "\\\\'")))
                .collect(Collectors.toList()));
        return capitals;
    }

    public Forecasts getForecastsByCode(String code) {
        Forecasts forecasts = new Forecasts();
        forecasts.setForecast(forecastRepository.findTop7ByCapitalIso2CodeOrderByForecastDateAsc(code));
        return forecasts;
    }

    public Forecasts getAllRecentForecasts() {
        Sort sort = new Sort("ForecastDate")
                .ascending();
        Forecasts forecasts = new Forecasts();
        forecasts.setForecast(forecastRepository.findAll(sort));
        return forecasts;
    }

    public Images getImagesByCode(String code){
        Images images = new Images();
        images.setImage(imageRepository.findByIso2Code(code));
        return images;
    }

    public Capital getCapitalByCode(String code){
        return capitalRepository.findById(code).orElse(null);
    }

    public Page<News> getNews(int pageNumber, int pageSize){
        PageRequest pageRequest = new PageRequest((pageNumber-1), pageSize, Sort.Direction.DESC, "date");
        return newsRepository.findAll(pageRequest);
    }

    public void saveCapital(Capital capital) {
        capitalRepository.save(capital);
    }

    public void saveImage(Image image) {
        imageRepository.save(image);
    }

    public void saveStories(List<News> list) {
        newsRepository.saveAll(list);
    }

    public void saveForecast(Forecast forecast) {
        Forecast existed = forecastRepository.findByCapitalIso2CodeAndAndForecastDate(forecast.getCapitalIso2Code(), forecast.getForecastDate());
        if (existed != null) {
            existed.merge(forecast);
            forecastRepository.save(existed);
        } else {
            forecastRepository.save(forecast);
        }
    }
}
