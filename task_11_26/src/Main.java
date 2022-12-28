import java.util.HashMap;
import java.util.Map;

public class Main {
    public static String solution(Map<String, Integer> date, String format){
        String res = format;
        for (char i: format.toCharArray()){

        }
        return res;
    }

    public static void main(String[] args) {
        Map<String, Integer> date = new HashMap<>();
        date.put("Year", 2016); date.put("Month", 4); date.put("Day", 30);
        System.out.println(solution(date, "Month DD, YYYY"));

    }
}
