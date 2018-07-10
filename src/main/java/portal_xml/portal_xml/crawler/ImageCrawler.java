package portal_xml.portal_xml.crawler;

import javafx.util.Pair;
import portal_xml.portal_xml.entity.jaxb.capital.Capital;
import portal_xml.portal_xml.entity.jaxb.image.Image;
import portal_xml.portal_xml.entity.jaxb.image.Images;
import portal_xml.portal_xml.service.DBService;
import portal_xml.portal_xml.utility.ErrorFileConfig;
import portal_xml.portal_xml.utility.XMLUtilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import static java.util.logging.Level.*;
import static portal_xml.portal_xml.enumpage.CrawlPage.*;
import static portal_xml.portal_xml.helperfunction.HelperFunction.getSubStringRegEx;

public class ImageCrawler extends AbstractCrawler {

    public ImageCrawler(DBService service, ErrorFileConfig errorFileConfig) {
        this.service = service;
        this.pageURL = IMAGE_PAGE;
        this.capitalBlockingQueue = new LinkedBlockingDeque<>();
        this.errorFileConfig = errorFileConfig;
    }

    @Override
    public void run() {
        XMLUtilities utilities = new XMLUtilities();
        String imagePage = IMAGE_PAGE.getUrl();

        String formattedString = "";
        int totalProgress;
        double counter = 1;
        while (true) {
            setupLogger();
            LOGGER.log(INFO, templateStarted);
            try {
                Capital capital = waitForQueueToContinue(this.capitalBlockingQueue);
                setupLogger();
                if (isTerminationFlag(capital)) {
                    counter = 1;
                    this.stop = true;
                    LOGGER.log(INFO, templateFinished);
                    closeLogger();
                }
                totalProgress = CrawlerManager.capitalSize;
                while (this.stop) {
                    waitForSignalToContinue();
                }
                if(isTerminationFlag(capital)){
                    resetProgress();
                    capital = waitForQueueToContinue(this.capitalBlockingQueue); // restart after finished
                    setupLogger();
                }
                formattedString = String.format(imagePage,
                        capital.getName().replaceAll("\\s+", "-"),
                        capital.getCountryName().replaceAll("\\s+", "-"));
                this.pageURL.setUrl(formattedString);
                LOGGER.log(INFO, "Crawling images for " + capital.getName() + " of " + capital.getCountryName());
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
                            .unmarshal(Images.class, "xsd/image.xsd");

                } catch (Exception e) {
                    LOGGER.log(WARNING, "Images not found for url: " + formattedString);
                }

                if (images != null && !images.getImage().isEmpty()) {
                    Stream<Image> stream = images.getImage().stream();

                    // check for image dimension big enough

                    Capital lambdaCapital = capital;
                    List<Image> filtered = stream.map((image -> {
                        String URL = image.getUrl();
                        Pair<Integer, Integer> dimension = getDimension(URL);
                        if (dimension != null) {
                            image.setWidth(dimension.getKey());
                            image.setHeight(dimension.getValue());

                        }
                        image.setIso2Code(lambdaCapital.getIso2Code());
                        return image;
                    })).collect(Collectors.toList());

                    filtered = filtered.stream()
                            .filter((image) -> (image.getWidth() >= 1000 && image.getHeight() <= 700))
                            .collect(Collectors.toList());

                    filtered.forEach(image -> {
                        service.saveImage(image);
                        LOGGER.log(INFO, "Image saved successful");
                    });
                }
                progress = ((counter++ / totalProgress) * 100);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
