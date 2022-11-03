package org.cannotsay.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date/Time util class
 */
public class DateTime {

    static final private String regex = "(Starttijd: )(.*)(&nbsp;)(.*)(Eindtijd: )(.*)(&nbsp;)";
    static final private Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
    static final private Logger LOGGER = Logger.getLogger(DateTime.class.getName());

    public static String getStartDate(String string) {
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return matcher.group(2);
        }
        return "";
    }

    public static String getEndDate(String string) {
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return matcher.group(6);
        }
        return "";
    }

    public static long getMiliseconds(String dateString) {
        long result = 0L;
        try {
            SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = f.parse(dateString);
            result = date.getTime();
        } catch(Exception e) {
            LOGGER.severe(e.getMessage());
        }
        return result;
    }
}