package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Performs date formatting .
 */
public class DateUtils {
    /**
     * Specifies date format used in all project .
     */
    private static final SimpleDateFormat dateFormatter = new SimpleDateFormat ( "dd.MM.yyyy" );
    /**
     * Returns string value of given date .
     * @param date date you give to get string from
     * @return string value of given date
     */
    public static String getString ( Date date ) {
        return dateFormatter.format ( date );
    }
    /**
     * Returns date parsed from given string .
     * @param string whic you want parse date from
     * @return Date value of given string
     */
    public  static Date getDate(String string) throws ParseException {
        return dateFormatter.parse ( string );
    }
}
