public class DateForma {
    public static int day;
    public static int year;
    public static int month;
    public DateForma(int day, int month, int year){
        DateForma.day = day; DateForma.year = year; DateForma.month = month;
    }

    public static int getMonth() {
        return month;
    }

    public static int getYear() {
        return year;
    }

    public static int getDay() {
        return day;
    }
}
