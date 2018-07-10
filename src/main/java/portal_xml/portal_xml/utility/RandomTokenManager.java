package portal_xml.portal_xml.utility;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.logging.*;

import static java.util.logging.Level.*;

@Component("contextBean")
@EnableScheduling
public class RandomTokenManager {

    private final Logger LOGGER = Logger.getLogger(this.getClass().getSimpleName());

    private FileHandler fh;

    private ServletContext context;

    private TokenFileConfig fileConfig;

    public RandomTokenManager(ServletContext context, TokenFileConfig tokenFileConfig){
        this.context = context;
        this.fileConfig = tokenFileConfig;
        generateToken();
    }

    // every 30 seconds 0/30 * * ? * *
    // every sunday 0 0 * ? * SUN
    // every hour * * * ? * *
    @Scheduled(cron = "0 0 * ? * SUN")
    public void generateToken(){
        try {
            fh = new FileHandler("logs/"+this.fileConfig.getTokenFilePath(), true);
            fh.setFormatter(new TokenLogFormatter());
            fh.setEncoding("UTF-8");
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String randomToken = RandomStringUtils.randomAlphanumeric(15);
        this.context.setAttribute("TOKEN", randomToken);
        logToFile(randomToken);
    }

    private void logToFile(String token){
        String message = String.format("token set to \"%s\"", token);
        LOGGER.log(INFO, message);
        this.fh.close();
    }

}
