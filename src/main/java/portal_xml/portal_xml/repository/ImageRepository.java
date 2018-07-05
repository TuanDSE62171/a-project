package portal_xml.portal_xml.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portal_xml.portal_xml.entity.jaxb.image.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    List<Image> findByIso2Code(String code);
}
