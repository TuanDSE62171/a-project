package portal_xml.portal_xml.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import portal_xml.portal_xml.entity.jaxb.capital.Capital;
import portal_xml.portal_xml.entity.jaxb.capital.Capitals;
import portal_xml.portal_xml.entity.jaxb.forecast.Forecast;
import portal_xml.portal_xml.entity.jaxb.forecast.Forecasts;
import portal_xml.portal_xml.entity.jaxb.image.Image;
import portal_xml.portal_xml.entity.jaxb.image.Images;
import portal_xml.portal_xml.entity.jaxb.news.News;
import portal_xml.portal_xml.repository.CapitalRepository;
import portal_xml.portal_xml.repository.ForecastRepository;
import portal_xml.portal_xml.repository.ImageRepository;
import portal_xml.portal_xml.repository.NewsRepository;

import java.sql.Date;
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

    public Forecasts getForecastsByCodeAndBetweenDate(String code, Date startDate, Date endDate){
        Forecasts forecasts = new Forecasts();
        forecasts.setForecast(forecastRepository.findTop7ByCapitalIso2CodeAndForecastDateBetween(code, startDate, endDate));
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
        Image existed = imageRepository.findByUrl(image.getUrl());
        if(existed != null){
            existed.merge(image);
            imageRepository.save(existed);
        } else {
            imageRepository.save(image);
        }
    }

    public void saveNews(News news){
        News existed = newsRepository.findByPostOriginUrl(news.getPostOriginUrl());
        if(existed != null){
            existed.merge(news);
            newsRepository.save(existed);
        } else {
            newsRepository.save(news);
        }
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
