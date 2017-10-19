package pl.rebigo.libs.common.Services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * IDE Editor: IntelliJ IDEA
 * <p>
 * Date: 16.09.2017
 * Time: 11:55
 * Project name: alle
 *
 * @author Karol Golec <karol.rebigo@gmail.com>
 */
public class DateTimeService {

    /**
     * parse string date time to calendar
     * @param dateTime date time
     * @param pattern pattern
     * @param locale locale
     * @return null or calendar
     */
    public static Calendar calendar(String dateTime, String pattern, Locale locale) {
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
            cal.setTime(sdf.parse(dateTime));
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Calendar to string
     * @param dateTime date time
     * @param pattern pattern
     * @return string date time
     */
    public static String string(Calendar dateTime, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(dateTime.getTime());
    }
}
