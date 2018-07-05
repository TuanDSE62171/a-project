package portal_xml.portal_xml.utility;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        try {
            fh = new FileHandler(this.fileConfig.getTokenFilePath());
            fh.setFormatter(new CustomFormatter());
            LOGGER.addHandler(fh);
        } catch (IOException e) {
            e.printStackTrace();
        }
        generateToken();
    }

    // every 30 seconds 0/30 * * ? * *
    // every sunday 0 0 * ? * SUN
    // every hour * * * ? * *
    @Scheduled(cron = "0/30 * * ? * *")
    public void generateToken(){
        Date date = new Date();
        String randomToken = RandomStringUtils.randomAlphanumeric(15);
        this.context.setAttribute("TOKEN", randomToken);
        logToFile(randomToken, date);
    }

    private void logToFile(String token, Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("[dd-MM-yyy HH:mm:ss]");
        String message = String.format("%s: token set to \"%s\"", sdf.format(date), token);
        LOGGER.log(INFO, message);
    }

    private class CustomFormatter extends Formatter{

        @Override
        public String format(LogRecord record) {
            return record.getLoggerName()+"-"+record.getMessage();
        }
    }

}
