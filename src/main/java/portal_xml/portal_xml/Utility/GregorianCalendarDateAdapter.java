package portal_xml.portal_xml.Utility;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.util.Calendar.*;
import static javax.xml.datatype.DatatypeConstants.*;

public class GregorianCalendarDateAdapter extends XmlAdapter<XMLGregorianCalendar, Date> {

    DateTimeFormatter dft = DateTimeFormatter.ofPattern("YYY-MM-dd");

    @Override
    public Date unmarshal(XMLGregorianCalendar v) throws Exception {
        return new Date((v.toGregorianCalendar().getTime()).getTime());
    }

    @Override
    public XMLGregorianCalendar marshal(Date v) throws Exception {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(new java.util.Date(v.getTime()));
        XMLGregorianCalendar c = DatatypeFactory.newInstance().newXMLGregorianCalendar(
                gc.get(YEAR),
                gc.get(MONTH),
                gc.get(DAY_OF_MONTH),
                FIELD_UNDEFINED,
                FIELD_UNDEFINED,
                FIELD_UNDEFINED,
                FIELD_UNDEFINED,
                FIELD_UNDEFINED
        );
        return c;
    }
}
