package portal_xml.portal_xml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portal_xml.portal_xml.entity.jaxb.news.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
