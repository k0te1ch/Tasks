import org.apache.commons.cli.*;
import util.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConsoleMain {

    public static class CmdParams {
        public String inputFile;
        public String outputFile;
        public boolean test;
        public boolean window;
    }

    public static ConsoleMain.CmdParams parseArgs(String[] args) throws ParseException {
        ConsoleMain.CmdParams params = new ConsoleMain.CmdParams();
        Options options = new Options();

        Option input = new Option("i", "input", true, "input file path");
        options.addOption(input);

        Option output = new Option("o", "output", true, "output file");
        options.addOption(output);

        Option window = new Option("w", "window", false, "windowed");
        options.addOption(window);

        Option test = new Option("t", "test", false, "tests");
        options.addOption(test);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("ConsoleMain", options);

            System.exit(70);
        }
        params.inputFile = ((!cmd.hasOption("input")) ? System.getProperty("user.dir") + "\\task_9_6\\src\\input.txt" : cmd.getOptionValue("input"));
        params.outputFile = ((!cmd.hasOption("output")) ? System.getProperty("user.dir") + "\\task_9_6\\src\\output.txt" : cmd.getOptionValue("output"));
        params.window = cmd.hasOption("window");
        params.test = cmd.hasOption("test");
        return params;
    }

    public static List<Integer> solution(List<Integer> List){ // Алгоритм Кадане с дополнительным циклом копирования. T: O(n); M: O(1);
        int minPos = 0;
        int maxPos = 0;
        int minPosTemp = 0;
        int maxPosTemp = 0;

        int best_sum = Integer.MIN_VALUE;
        int current_sum = 0;
        int cnt = 0;
        for (Integer i: List){
            if (current_sum + i < i) {
                current_sum = i;
                minPosTemp = cnt;
            } else {
                current_sum += i;
                maxPosTemp = cnt;
            }
            if (best_sum <= current_sum){
                if (best_sum == current_sum && maxPos-minPos <= maxPosTemp-minPosTemp) continue;
                best_sum = current_sum;
                minPos = minPosTemp;
                maxPos = maxPosTemp;
            }
            cnt++;
        }
        maxPos++;
        if (maxPos <= minPos) maxPos++;
        List<Integer> result = new ArrayList<>();
        for (int i = minPos; i < maxPos; i++) result.add(List.get(i));
        return result;
    }

    public static void main(String[] args) throws Exception {
        ConsoleMain.CmdParams params = parseArgs(args);
        if (params.test){
            for (int i = 1; i <= 10; i++) {
                params.inputFile = String.format("%s\\task_9_6\\src\\tests\\input%s.txt", System.getProperty("user.dir"), i);
                params.outputFile = String.format("%s\\task_9_6\\src\\tests\\output%s.txt", System.getProperty("user.dir"), i);
                int[] listArr = ArrayUtils.readIntArrayFromFile(params.inputFile);
                if (listArr.length == 0 || listArr == null) {
                    System.err.printf("Can't read array from \"%s\"%n", params.inputFile);
                    System.exit(70);
                }
                List<Integer> list = solution(Arrays.asList(ArrayUtils.toObject(listArr)));
                ArrayUtils.writeArrayToFile(params.outputFile, ArrayUtils.toPrimitive(list.toArray(new Integer[list.size()])));
            }
        }
        if (params.window) {
            GuiMain.winMain();
        } else {
            int[] listArr = ArrayUtils.readIntArrayFromFile(params.inputFile);
            if (listArr.length == 0 || listArr == null) {
                System.err.printf("Can't read array from \"%s\"%n", params.inputFile);
                System.exit(70);
            }
            List<Integer> array = Arrays.asList(ArrayUtils.toObject(listArr));
            List<Integer> list = solution(array);
            ArrayUtils.writeArrayToFile(params.outputFile, ArrayUtils.toPrimitive(list.toArray(new Integer[list.size()])));
        }
    }
}