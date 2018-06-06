package lorica.main.interfaces;

import lorica.main.classes.Date;

public interface IDateParser {

    /**
     * Converts a string value to a Date object
     *
     * @param value the Date value in string format
     *
     */
    Date Parse(String value);
}
