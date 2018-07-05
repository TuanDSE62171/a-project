package portal_xml.portal_xml.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portal_xml.portal_xml.Entity.Jaxb.Image.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByIso2Code(String code);
}
