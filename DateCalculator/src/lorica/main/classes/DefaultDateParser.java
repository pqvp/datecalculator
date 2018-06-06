package lorica.main.classes;

import lorica.main.interfaces.IDateParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultDateParser implements IDateParser {
    private  String DATE_FORMAT_REGEX = "(\\d{2})/(\\d{2})/(\\d{4})";

    /**
     * Converts a string value to a Date object
     *
     * @param value the Date value in DD/MM/YYYY format
     * @return the Date object
     *
     */
    public Date Parse(String value)
    {
        Pattern pattern = Pattern.compile(DATE_FORMAT_REGEX);
        Matcher matcher = pattern.matcher(value);
        if(matcher.matches() == false || matcher.groupCount() != 3)
        {
            throw new IllegalArgumentException("Date argument needs to be in DD/MM/YYYY format.");
        }
        int day = Integer.parseInt(matcher.group(1));
        int month = Integer.parseInt(matcher.group(2));
        int year = Integer.parseInt(matcher.group(3));

        return new Date(day, month, year);
    }
}
