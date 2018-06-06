package lorica.tests;

import static org.junit.jupiter.api.Assertions.*;

import lorica.main.classes.Date;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

class DateTest {

    @org.junit.jupiter.api.Test
    void getDay() {
        int day = 12;
        Date sut = new Date(day,1,2000);
        assertEquals(day, sut.GetDay());
    }

    @org.junit.jupiter.api.Test
    void getMonth() {
        int month = 9;
        Date sut = new Date(1,month,2000);
        assertEquals(month, sut.GetMonth());
    }

    @org.junit.jupiter.api.Test
    void getYear() {
        int year = 2000;
        Date sut = new Date(1,9,year);
        assertEquals(year, sut.GetYear());
    }

    //@org.junit.jupiter.api.Test
    @ParameterizedTest
    @ValueSource(ints = {1990, 1991, 1996, 2000, 2006, 2010, 2018, 2020, 2043, 2088})
    void isLeapYear_compare_with_library_method_should_equal(int year) {
        assertEquals(Date.IsLeapYear(year), java.time.Year.isLeap(year));
    }

    @org.junit.jupiter.api.Test
    void differenceInDays_with_null_should_return_zero() {
        Date sut = new Date(31,12,2999);
        assertEquals(0, sut.DifferenceInDays(null, true));
        assertEquals(0, sut.DifferenceInDays(null, false));
    }

    @org.junit.jupiter.api.Test
    void differenceInDays_including_start_end_should_equal_library_function() throws ParseException  {
        // Arrange
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        String inputString1 = "23/01/1997";
        String inputString2 = "27/04/2020";
        java.util.Date date1 = myFormat.parse(inputString1);
        java.util.Date date2 = myFormat.parse(inputString2);
        long diffTicks = date2.getTime() - date1.getTime();
        long days = TimeUnit.DAYS.convert(diffTicks, TimeUnit.MILLISECONDS);

        // Act
        Date start = new Date(23,1,1997);
        Date end = new Date(27,4,2020);

        // Assert
        assertEquals(days, end.DifferenceInDays(start, false));
    }

    @org.junit.jupiter.api.Test
    void differenceInDays_excluding_start_end_should_equal_library_function() throws ParseException  {
        // Arrange
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
        String inputString1 = "23/01/1997";
        String inputString2 = "31/12/2040";
        java.util.Date date1 = myFormat.parse(inputString1);
        java.util.Date date2 = myFormat.parse(inputString2);
        long diffTicks = date2.getTime() - date1.getTime();
        long days = TimeUnit.DAYS.convert(diffTicks, TimeUnit.MILLISECONDS);

        // Act
        Date start = new Date(23,1,1997);
        Date end = new Date(31,12,2040);

        // Assert
        assertEquals(days, end.DifferenceInDays(start, true) + 1);
    }


    @org.junit.jupiter.api.Test
    void compare_dates_less_than_should_return_one() {
        Date sut = new Date(4,4,2018);
        assertEquals(1, sut.Compare(new Date(3,4,2018)));
        assertEquals(1, sut.Compare(new Date(5,3,2018)));
        assertEquals(1, sut.Compare(new Date(20,9,2010)));
    }

    @org.junit.jupiter.api.Test
    void compare_dates_greater_than_should_return_negative_one() {
        Date sut = new Date(4,4,2018);
        assertEquals(-1, sut.Compare(new Date(5,4,2018)));
        assertEquals(-1, sut.Compare(new Date(5,3,2019)));
        assertEquals(-1, sut.Compare(new Date(20,9,2020)));
    }

    @org.junit.jupiter.api.Test
    void compare_dates_equal_should_return_zero() {
        Date sut = new Date(4,4,2018);
        assertEquals(0, sut.Compare(new Date(4,4,2018)));
        assertNotEquals(0, sut.Compare(new Date(5,4,2018)));
        assertNotEquals(0, sut.Compare(new Date(3,4,2018)));
    }

    @org.junit.jupiter.api.Test
    void negative_date_components_should_throw_exception()
    {
        assertThrows(IllegalArgumentException.class,
                () -> new Date(-1,-1,-2018));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 32, 100})
    void invalid_day_components_should_throw_exception(int day)
    {
        assertThrows(IllegalArgumentException.class,
                () -> new Date(day,1,2018));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 13, 100})
    void invalid_month_components_should_throw_exception(int month)
    {
        assertThrows(IllegalArgumentException.class,
                () -> new Date(31,month,2018));
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 1900, 3000})
    void invalid_year_components_should_throw_exception(int year)
    {
        assertThrows(IllegalArgumentException.class,
                () -> new Date(31,1,year));
    }

    @org.junit.jupiter.api.Test
    void date_less_than_min_date_should_throw_exception()
    {
        assertThrows(IllegalArgumentException.class,
                () -> new Date(31,12,1900));
    }

    @org.junit.jupiter.api.Test
    void date_greater_than_max_date_should_throw_exception()
    {
        assertThrows(IllegalArgumentException.class,
                () -> new Date(1,1,3000));
    }
}