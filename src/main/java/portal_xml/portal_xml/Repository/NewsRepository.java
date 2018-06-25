package portal_xml.portal_xml.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import portal_xml.portal_xml.Entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
