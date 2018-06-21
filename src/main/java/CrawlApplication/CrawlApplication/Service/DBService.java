package CrawlApplication.CrawlApplication.Service;

import CrawlApplication.CrawlApplication.Entity.Capital;
import CrawlApplication.CrawlApplication.Entity.Forecast;
import CrawlApplication.CrawlApplication.JpaInterface.CapitalRepository;
import CrawlApplication.CrawlApplication.JpaInterface.ForecastRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBService {

    CapitalRepository capitalRepository;
    ForecastRepository forecastRepository;

    public DBService(CapitalRepository capitalRepository, ForecastRepository forecastRepository) {
        this.capitalRepository = capitalRepository;
        this.forecastRepository = forecastRepository;
    }

    public List<Capital> getAllCapitals(){
        return capitalRepository.findAll();
    }

    public void saveCapital(Capital capital) {
        capitalRepository.save(capital);
    }

    public void saveForecasts(List<Forecast> list) {
        forecastRepository.saveAll(list);
    }
}
