package lorica.main.classes;

public class Date {

    private static int[] DaysToMonthInNormalYear = {0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
    private static int[] DaysToMonthInLeapYear = {0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366};

    private static int[] DaysPerMonthInNormalYear = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    private static int[] DaysPerMonthInLeapYear = {31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    private long TicksPerMillisecond = 10000;
    private long TicksPerSecond = TicksPerMillisecond * 1000;
    private long TicksPerMinute = TicksPerSecond * 60;
    private long TicksPerHour = TicksPerMinute * 60;
    private long TicksPerDay = TicksPerHour * 24;

    private int _day;
    private int _month;
    private int _year;

    // Public methods

    public Date(int day, int month, int year)
    {
        if(!ValidateDate(day, month, year))
            throw new IllegalArgumentException("Date entered is invalid, valid dates are between 01/01/1901 to 31/12/2999");
        this._day = day;
        this._month = month;
        this._year = year;
    }

    public int GetDay() {
        return this._day;
    }

    public int GetMonth() {
        return this._month;
    }

    public int GetYear() {
        return this._year;
    }


    /**
     * Determine whether the supplied year value is a leap year
     *
     * @param year the year value
     *
     */
    public static boolean IsLeapYear(int year) {
        // sourced from https://en.wikipedia.org/wiki/Leap_year
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    /**
     * Calculates the number of days between the current date and the provided Date value
     *
     * @param value the Date value to be used to calculate the difference in days
     * @param excludeStartEndDate when set to true the start/end date will be excluded from the calculations
     * @return the number of days between the current date and the provided value
     *
     */
    public long DifferenceInDays(Date value, boolean excludeStartEndDate)
    {
        if(value == null)
            return 0;

        long currentDateToTicks = DateToTicks(this);
        long valueDateToTicks = DateToTicks(value);
        long tickDifference = (currentDateToTicks - valueDateToTicks);
        return TicksToDays(tickDifference) - (excludeStartEndDate ? 1 : 0);
    }

    /**
     * Calculates the number of days between the current date and the provided Date value
     *
     * @param value the Date value to be used to calculate the difference in days
     *
     */
    public int Compare(Date value)
    {
        long valueDateToTicks = DateToTicks(value);
        long currentDateToTicks = DateToTicks(this);
        if(currentDateToTicks > valueDateToTicks) return 1;
        if(currentDateToTicks < valueDateToTicks) return -1;
        return 0;
    }

    // Private methods
    /**
     * Calculates the number of ticks from the provided date components
     *
     * @param day the day component
     * @param month the month component
     * @param year the year component
     *
     */
    private long DateToTicks(int day, int month, int year)
    {
        if (year >= 1 && year <= 9999 && month >= 1 && month <= 12) {
            int[] days = IsLeapYear(year)? DaysToMonthInLeapYear: DaysToMonthInNormalYear;
            if (day >= 1 && day <= days[month] - days[month - 1]) {
                int y = year - 1;
                int n = y * 365 + y / 4 - y / 100 + y / 400 + days[month - 1] + day - 1;
                return n * TicksPerDay;
            }
        }
        throw new IllegalArgumentException("Year must be between 1 to 9999 and Month must be between 1 to 12.");
    }

    private long DateToTicks(Date value)
    {
        return DateToTicks(value.GetDay(), value.GetMonth(), value.GetYear());
    }

    /**
     * Convert the supplied number of ticks to number of days
     *
     * @param ticks the number of ticks
     *
     */
    private long TicksToDays(long ticks)
    {
        return ticks/TicksPerDay;
    }

    /**
     * Validation rule to check the date components are valid
     *
     * @param day the day component
     * @param month the month component
     * @param year the year component
     *
     */
    private boolean ValidateDate(int day, int month, int year)
    {
        if(day <= 0 || month <= 0 || year <= 0 || month > 12)
            return false;

        if( year < 1901 || year > 2999)
            return false;

        int maxDaysInMonth = IsLeapYear(year) ? DaysPerMonthInLeapYear[month-1] : DaysPerMonthInNormalYear[month-1];
        if(day > maxDaysInMonth)
            return false;

        return true;
    }
}
