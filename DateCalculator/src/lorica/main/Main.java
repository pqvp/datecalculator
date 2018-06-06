package lorica.main;

import lorica.main.classes.Date;
import lorica.main.classes.DefaultDateParser;
import lorica.main.interfaces.IDateParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    private static boolean DEBUG_FLAG = false;

    private static void RunSimpleTestCases()
    {
        DisplayDaysDifference(new Date(7,11,1972), new Date(8,11,1972));
        DisplayDaysDifference(new Date(1,1,2000), new Date(3,1,2000));
        DisplayDaysDifference(new Date(2,6,1983), new Date(22,6,1983));
        DisplayDaysDifference(new Date(4,7,1984), new Date(25,12,1984));
        DisplayDaysDifference(new Date(3,1,1989), new Date(3,8,1983));
    }

    private static void DisplayDaysDifference(Date date1, Date date2)
    {
        Date startDate, endDate;

        if(date2.Compare(date1) >= 0) {
            startDate = date1;
            endDate = date2;
        } else
        {
            startDate = date2;
            endDate = date1;
        }

        long days = endDate.DifferenceInDays(startDate, true);
        System.out.println("Days difference: " + days);
    }

    public static void main(String[] args) {
        if(DEBUG_FLAG) {
            RunSimpleTestCases();
        }

        // This can be done via dependency injection to select different date format parsers.
        IDateParser parser = new DefaultDateParser();

        System.out.println("To exit program, simply enter letter 'q'");
        while(true) {
            try{
                System.out.print("Please enter date 1 in DD/MM/YYYY format: ");

                BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                String dateString1 = br.readLine();
                if(dateString1.compareToIgnoreCase("q") == 0)
                    break;

                Date date1 = parser.Parse(dateString1);

                System.out.print("Please enter date 2 in DD/MM/YYYY format: ");
                String dateString2 = br.readLine();
                if(dateString2.compareToIgnoreCase("q") == 0)
                    break;

                Date date2 = parser.Parse(dateString2);

                DisplayDaysDifference(date1, date2);
            }
            catch(Exception ex)
            {
                System.out.println("Exception: " + ex.getMessage());
                return;
            }
        }
    }
}
