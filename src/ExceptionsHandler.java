import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExceptionsHandler {
    public static boolean ret = true;
    public static int exceptionCode = 0;

    public static boolean checkDateRange(String dateFromAsString, String dateToAsString){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date dateFrom = format.parse(dateFromAsString);
            Date dateTo = format.parse(dateToAsString);
            if(dateFrom.after(dateTo)){
                System.out.println("The specified range does not exist!");
                ret = false;
            }
        } catch (ParseException e) {
            System.out.println("Bad format of date!");
            ret = false;
        }
        return ret;
    }

    public static void retException(){
        if(exceptionCode == 1)
            System.out.println("Limit of 367 days has been exceeded");
        else if(exceptionCode == 2)
            System.out.println("Entered currency code does not exist or date range is wrong");
    }
}
