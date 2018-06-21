package CrawlApplication.CrawlApplication.JpaInterface;

import CrawlApplication.CrawlApplication.Entity.Capital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, Long> {
}
