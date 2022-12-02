import org.apache.commons.cli.*;
import util.ArrayUtils;

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
        params.inputFile = ((!cmd.hasOption("input")) ? System.getProperty("user.dir") + "\\task_8_3\\src\\input.txt" : cmd.getOptionValue("input"));
        params.outputFile = ((!cmd.hasOption("output")) ? System.getProperty("user.dir") + "\\task_8_3\\src\\output.txt" : cmd.getOptionValue("output"));
        params.window = cmd.hasOption("window");
        params.test = cmd.hasOption("test");
        return params;
    }

    public static int[][] solution(int[][] array){
        int m = -1;
        for (int[] ints : array) {
            m = Math.max(ints.length, m);
        }
        int[][] array1 = new int[m][array.length];
        for (int i = 0; i < array1.length; i++){
            for (int d = 0; d < array1[i].length; d++){
                array1[i][d] = array[d][i];
            }
        }
        return array1;
    }

    public static void main(String[] args) throws Exception {
        ConsoleMain.CmdParams params = parseArgs(args);
        if (params.test){
            for (int i = 1; i <= 5; i++) {
                params.inputFile = String.format("%s\\task_8_3\\src\\tests\\input0%s.txt", System.getProperty("user.dir"), i);
                params.outputFile = String.format("%s\\task_8_3\\src\\tests\\output0%s.txt", System.getProperty("user.dir"), i);
                int[][] array = ArrayUtils.readIntArray2FromFile(params.inputFile);
                if (array == null) {
                    System.err.printf("Can't read array from \"%s\"%n", params.inputFile);
                    System.exit(70);
                }
                ArrayUtils.writeArrayToFile(params.outputFile, solution(array));
            }
        }
        if (params.window) {
            GuiMain.winMain();
        } else {
            int[][] array = ArrayUtils.readIntArray2FromFile(params.inputFile);
            if (array.length == 0 || array == null) {
                System.err.printf("Can't read array from \"%s\"%n", params.inputFile);
                System.exit(70);
            }
            ArrayUtils.writeArrayToFile(params.outputFile, solution(array));
        }
    }
}