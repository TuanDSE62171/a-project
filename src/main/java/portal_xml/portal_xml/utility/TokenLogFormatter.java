package portal_xml.portal_xml.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class TokenLogFormatter extends Formatter {

    private Date date;
    private final SimpleDateFormat sdf = new SimpleDateFormat("[dd-MM-yyy HH:mm:ss]");

    @Override
    public String format(LogRecord record) {
        date = new Date(record.getMillis());
        return sdf.format(date)+" "+record.getLevel()+" --- "+record.getLoggerName()+": "+record.getMessage() + System.getProperty("line.separator");
    }

}
