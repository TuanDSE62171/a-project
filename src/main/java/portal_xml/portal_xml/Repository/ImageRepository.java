package portal_xml.portal_xml.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portal_xml.portal_xml.Entity.Jaxb.Image.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}
