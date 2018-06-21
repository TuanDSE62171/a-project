package CrawlApplication.CrawlApplication.JpaInterface;

import CrawlApplication.CrawlApplication.Entity.Forecast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ForecastRepository extends JpaRepository<Forecast, Long> {
}
