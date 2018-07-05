package portal_xml.portal_xml.Crawler;

import javafx.util.Pair;
import portal_xml.portal_xml.Entity.Jaxb.Capital.Capital;
import portal_xml.portal_xml.Entity.Jaxb.Image.Image;
import portal_xml.portal_xml.Entity.Jaxb.Image.Images;
import portal_xml.portal_xml.Service.DBService;
import portal_xml.portal_xml.Utility.XMLUtilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static java.util.logging.Level.*;
import static portal_xml.portal_xml.EnumPage.CrawlPage.*;
import static portal_xml.portal_xml.HelperFunction.HelperFunction.getSubStringRegEx;

public class ImageCrawler extends AbstractCrawler {

    public ImageCrawler(DBService service) {
        this.service = service;
        this.pageURL = IMAGE_PAGE;
    }

    @Override
    public Object call() {
        LOGGER.log(INFO, templateInit);
        List<Capital> capitals = null;
        try {
            capitals = CrawlerManager.capitalFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        XMLUtilities utilities = new XMLUtilities();
        String imagePage = IMAGE_PAGE.getUrl();

        String formattedString = "";

        int totalProgress = capitals.size();
        double counter = 1;
        for (Capital capital : capitals) {
            while (this.stop){
                LOGGER.log(INFO, templateStopped);
                waitForSignalToContinue();
                LOGGER.log(INFO, templateResumed);
            }
            formattedString = String.format(imagePage,
                    capital.getName().replaceAll("\\s+", "-"),
                    capital.getCountryName().replaceAll("\\s+", "-"));
            this.pageURL.setUrl(formattedString);
            LOGGER.log(INFO, "{0}: Crawling images for " + capital.getName() + " of " + capital.getCountryName(), crawlerName);
            String resultHTML = crawlHTML(null);

            utilities.setResult(resultHTML);
            Images images = null;
            try {
                images = utilities.
                        welformHTML(
                                getSubStringRegEx("<div id=\"gridSingle\"", "<\\/figure>\\t*\\n*\\s*<\\/div>\\t*\\n*\\s*<\\/div>\\t*\\n*\\s*<\\/div>"),
                                null,
                                null)
                        .transform("xsl/image.xsl")
                        .unmarshal(Images.class, "xsd/Image.xsd");

            } catch (Exception e) {
                LOGGER.log(WARNING, "{0}: Images not found for url: " + formattedString, crawlerName);
            }

            if (images != null && !images.getImage().isEmpty()) {
                Stream<Image> stream = images.getImage().stream();

                // check for image dimension big enough

                List<Image> filtered = stream.map((image -> {
                    String URL = image.getUrl();
                    Pair<Integer, Integer> dimension = getDimension(URL);
                    if (dimension != null) {
                        image.setWidth(dimension.getKey());
                        image.setHeight(dimension.getValue());

                    }
                    image.setIso2Code(capital.getIso2Code());
                    return image;
                })).collect(Collectors.toList());

                filtered = filtered.stream()
                        .filter((image) -> (image.getWidth() >= 1000 && image.getHeight() <= 700))
                        .collect(Collectors.toList());

                filtered.forEach(image -> {
                    service.saveImage(image);
                    LOGGER.log(INFO, "{0}: image saved successful", crawlerName);
                });
            }
            progress = ((counter++ / totalProgress) * 100);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        LOGGER.log(INFO, templateDestroy);
        return null;
    }

    public Pair<Integer, Integer> getDimension(String url) {
        try {
            URL imgURL = new URL(url);
            BufferedImage image = ImageIO.read(imgURL);
            return new Pair<>(image.getWidth(), image.getHeight());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
