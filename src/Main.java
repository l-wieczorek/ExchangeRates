import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        String input;
        String[] splittedInput;
        Scanner scan = new Scanner(System.in);
        System.out.print("Enter the currency code and date (yyyy-MM-dd) range {code} {from} {to}: ");
        input = scan.nextLine();
        splittedInput = input.split("\\s+");
        String code = splittedInput[0].toLowerCase();
        String fromDate = splittedInput[1];
        String toDate = splittedInput[2];
        if(ExceptionsHandler.checkDateRange(fromDate, toDate)) {
            double averageRate = CurrencyInfo.averageRate(CurrencyInfo.getExchangeRate(code, fromDate, toDate));
            double deviation = CurrencyInfo.standardDeviation(CurrencyInfo.getExchangeRate(code, fromDate, toDate), averageRate);
            if(ExceptionsHandler.ret) {
                System.out.println(averageRate);
                System.out.println(deviation);
            }
            else
                ExceptionsHandler.retException();
        }
    }
}
