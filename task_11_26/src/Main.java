import java.util.*;

import static java.util.Map.entry;

public class Main {
    public static String solution(DateForma date, String format){
        String s;
        List<String> templates = Arrays.asList("year", "yyyy", "yyy", "yy", "q", "month", "mon", "mm", "rm", "ww", "w", "day", "ddd", "dd", "dy", "d", "y");
        for (String i: templates){
            s = "";
            switch (i) {
                case "month":
                case "mon":
                    s = Arrays.asList("январь", "февраль", "март", "апрель", "май", "июнь", "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь").get(DateForma.getMonth() - 1);
                    if (i.equals("mon")) {
                        s = s.substring(0, 3);
                    }
                    break;
                case "mm":
                    s = Integer.toString(DateForma.getMonth());
                    if (DateForma.getMonth() < 10) {
                        s = "0" + s;
                    }
                    break;
                case "year":
                case "yyyy":
                case "yyy":
                case "yy":
                case "y":
                    if (i.length() < 4) {
                        if (Integer.toString(DateForma.getYear()).length() > 4 - i.length()) s = Integer.toString(DateForma.getYear()).substring(4 - i.length());
                    } else {
                        s = Integer.toString(DateForma.getYear());
                    }
                    break;
                case "q":
                    s = Integer.toString((DateForma.getMonth() - 1) / 3 + 1);
                    break;
                case "rm":
                    s = Arrays.asList("I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII").get(DateForma.getMonth() - 1);
                    break;
                case "ww": {
                    int days = DateForma.getDay();
                    for (int g = 0; g < DateForma.getMonth() - 1; g++) {
                        days += 28;
                        if (Arrays.asList(0, 2, 4, 6, 7, 9, 11).contains(g)) days += 3;
                        else if (g == 1 && DateForma.getYear() % 4 == 0) days += 1;
                        else if (g != 1) days += 2;
                    }
                    s = Integer.toString(days / 7);
                    if (days % 7 != 0) s = Integer.toString(days / 7 + 1);
                    break;
                }
                case "w":
                    s = Integer.toString(DateForma.getMonth() / 7);
                    if (DateForma.getMonth() % 7 == 0) s = Integer.toString(DateForma.getMonth() / 7 + 1);
                    break;
                case "d": {
                    Map<Integer, Integer> code_month = Map.ofEntries(entry(1, 1), entry(2, 4), entry(3, 4), entry(4, 0), entry(5, 2), entry(6, 5),
                            entry(7, 0), entry(8, 3), entry(9, 6), entry(10, 1), entry(11, 4), entry(12, 6));
                    int code_year = (Arrays.asList(6, 4, 2, 0).get(Integer.parseInt(Integer.toString(DateForma.getYear()).substring(0, 2)) % 4) + Integer.parseInt(Integer.toString(DateForma.getYear()).substring(2))
                            + Integer.parseInt(Integer.toString(DateForma.getYear()).substring(2)) / 4) % 7;
                    int d = (DateForma.getDay() + code_month.get(DateForma.getMonth()) + code_year) % 7;
                    if (DateForma.getYear() % 4 == 0 && DateForma.getMonth() < 2 || (DateForma.getMonth() == 2 && DateForma.getDay() <= 29)) d--;
                    s = Map.of(0, "6", 1, "7", 2, "1", 3, "2", 4, "3", 5, "4", 6, "5").get(d);
                    break;
                }
                case "day": {
                    Map<Integer, Integer> code_month = Map.ofEntries(entry(1, 1), entry(2, 4), entry(3, 4), entry(4, 0), entry(5, 2), entry(6, 5),
                            entry(7, 0), entry(8, 3), entry(9, 6), entry(10, 1), entry(11, 4), entry(12, 6));
                    int code_year = (Arrays.asList(6, 4, 2, 0).get(Integer.parseInt(Integer.toString(DateForma.getYear()).substring(0, 2)) % 4) + Integer.parseInt(Integer.toString(DateForma.getYear()).substring(2))
                            + Integer.parseInt(Integer.toString(DateForma.getYear()).substring(2)) / 4) % 7;
                    int d = (DateForma.getDay() + code_month.get(DateForma.getMonth()) + code_year) % 7;
                    if (DateForma.getYear() % 4 == 0 && DateForma.getMonth() < 2 || (DateForma.getMonth() == 2 && DateForma.getDay() <= 29)) d--;
                    s = Map.of(0, "суббота", 1, "воскресенье", 2, "понедельник", 3, "вторник", 4, "среда", 5, "четверг", 6, "пятница").get(d);
                    break;
                }
                case "dd":
                    s = Integer.toString(DateForma.getDay());
                    if (DateForma.getDay() < 10) s = "0" + DateForma.getDay();
                    break;
                case "ddd": {
                    int days = DateForma.getDay();
                    for (int g = 0; g < DateForma.getMonth() - 1; g++) {
                        days += 28;
                        if (Arrays.asList(0, 2, 4, 6, 7, 9, 11).contains(g)) days += 3;
                        else if (g == 1 && DateForma.getYear() % 4 == 0) days += 1;
                        else if (g != 1) days += 2;
                    }
                    s = Integer.toString(days);
                    break;
                }
                case "dy": {
                    Map<Integer, Integer> code_month = Map.ofEntries(entry(1, 1), entry(2, 4), entry(3, 4), entry(4, 0), entry(5, 2), entry(6, 5),
                            entry(7, 0), entry(8, 3), entry(9, 6), entry(10, 1), entry(11, 4), entry(12, 6));
                    int code_year = (Arrays.asList(6, 4, 2, 0).get(Integer.parseInt(Integer.toString(DateForma.getYear()).substring(0, 2)) % 4) + Integer.parseInt(Integer.toString(DateForma.getYear()).substring(2))
                            + Integer.parseInt(Integer.toString(DateForma.getYear()).substring(2)) / 4) % 7;
                    int d = (DateForma.getDay() + code_month.get(DateForma.getMonth()) + code_year) % 7;
                    if (DateForma.getYear() % 4 == 0 && DateForma.getMonth() < 2 || (DateForma.getMonth() == 2 && DateForma.getDay() <= 29)) d--;
                    s = Map.of(0, "сб", 1, "вс", 2, "пн", 3, "вт", 4, "ср", 5, "чт", 6, "пт").get(d);
                    break;
                }
            }
            StringBuilder i1 = new StringBuilder();
            StringBuilder i2 = new StringBuilder();
            for (int d = 0; d < i.length(); d++){
                i2.append(String.valueOf(i.charAt(d)).toUpperCase());
                if (d == 0){
                    i1.append(String.valueOf(i.charAt(0)).toUpperCase());
                } else {
                    i1.append(i.charAt(d));
                }
            }
            StringBuilder s1 = new StringBuilder();
            StringBuilder s2 = new StringBuilder();
            for (int d = 0; d < s.length(); d++){
                s2.append(String.valueOf(s.charAt(d)).toUpperCase());
                if (d == 0){
                    s1.append(String.valueOf(s.charAt(0)).toUpperCase());
                } else {
                    s1.append(s.charAt(d));
                }
            }
            format = format.replaceAll(i, s).replaceAll(i1.toString(), s1.toString()).replaceAll(i2.toString(), s2.toString());
        }
        return format;
    }

    public static void main(String[] args) {
        System.out.println(solution(new DateForma(22, 11, 2004), "YEAR|YYYY|YYY|YY|Y|Q|MM|MON|MONTH|RM|WW|W|D|DAY|DD|DDD|DY"));
        System.out.println(solution(new DateForma(29, 2, 2004), "YEAR|YYYY|YYY|YY|Y|Q|MM|MON|MONTH|RM|WW|W|D|DAY|DD|DDD|DY"));
        System.out.println(solution(new DateForma(22, 11, 1605), "YEAR|YYYY|YYY|YY|Y|Q|MM|MON|MONTH|RM|WW|W|D|DAY|DD|DDD|DY"));
        System.out.println(solution(new DateForma(1, 12, 2004), "YEAR|YYYY|YYY|YY|Y|Q|MM|MON|MONTH|RM|WW|W|D|DAY|DD|DDD|DY"));
        System.out.println(solution(new DateForma(1, 9, 2017), "Month DD, YYYY. months month mOnth|YEAR|YYYY|YYY|YY|Y|Q|MM|MON|MONTH|RM|WW|W|D|DAY|DD|DDD|DY"));
        //Month DD, YYYY. months month mOnth|YEAR|YYYY|YYY|YY|Y|Q|MM|MON|MONTH|RM|WW|W|D|DAY|DD|DDD|DY
        //Сентябрь 01, 2017. сентябрьs сентябрь mOnth|2017|2017|017|17|7|3|09|СЕН|СЕНТЯБРЬ|IX|35|1|5|ПЯТНИЦА|01|244|ПТ
    }
}
