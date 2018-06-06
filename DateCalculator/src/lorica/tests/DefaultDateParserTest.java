package lorica.tests;

import lorica.main.classes.Date;
import lorica.main.classes.DefaultDateParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class DefaultDateParserTest {

    @ParameterizedTest
    @ValueSource(strings = {"01-01-2000", "1-1-18", "02-02-2000", "2-2-19", "1/2/99"})
    void parse_unsupported_date_format_should_throw_exception(String dateString) {
        DefaultDateParser sut = new DefaultDateParser();
        assertThrows(IllegalArgumentException.class,
                () -> sut.Parse(dateString));
    }

    @ParameterizedTest
    @ValueSource(strings = {"01/01/2000", "02/02/2200", "31/12/1999"})
    void parse_supported_date_format_should_return_date_object(String dateString) throws ParseException
    {
        // Arrange
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date actualDate = formatter.parse(dateString);
        Calendar cal = Calendar.getInstance();
        cal.setTime(actualDate);
        int actualYear = cal.get(Calendar.YEAR);
        int actualMonth = cal.get(Calendar.MONTH) + 1;
        int actualDay = cal.get(Calendar.DAY_OF_MONTH);

        // Act
        DefaultDateParser sut = new DefaultDateParser();
        Date expectedDate = sut.Parse(dateString);

        // Assert
        assertEquals(expectedDate.GetDay(), actualDay);
        assertEquals(expectedDate.GetMonth(), actualMonth);
        assertEquals(expectedDate.GetYear(), actualYear);

    }
}