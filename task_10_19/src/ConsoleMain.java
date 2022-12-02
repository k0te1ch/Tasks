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

    public static CmdParams parseArgs(String[] args) throws ParseException {
        CmdParams params = new CmdParams();
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
        params.inputFile = ((!cmd.hasOption("input")) ? System.getProperty("user.dir") + "\\task_10_19\\src\\input.txt" : cmd.getOptionValue("input"));
        params.outputFile = ((!cmd.hasOption("output")) ? System.getProperty("user.dir") + "\\task_10_19\\src\\output.txt" : cmd.getOptionValue("output"));
        params.window = cmd.hasOption("window");
        params.test = cmd.hasOption("test");
        return params;
    }

    public static List<List<Triangle>> solution(List<Triangle> List){
        List<List<Triangle>> res = new ArrayList<>();
        List<Triangle> block;
        for (int i = 0, f = 1; i < List.size(); i += f){
            block = new ArrayList<>();
            block.add(List.get(i));
            for (int s = 0; s < List.size(); s++){
                if (s == i) continue;
                if (Math.max(List.get(i).a, List.get(s).a)/Math.min(List.get(i).a, List.get(s).a) == Math.max(List.get(i).b, List.get(s).b)/Math.min(List.get(i).b, List.get(s).b) && (Math.max(List.get(i).a, List.get(s).a)/Math.min(List.get(i).a, List.get(s).a) == Math.max(List.get(i).c, List.get(s).c)/Math.min(List.get(i).c, List.get(s).c) || Math.max(List.get(i).b, List.get(s).b)/Math.min(List.get(i).b, List.get(s).b) == Math.max(List.get(i).c, List.get(s).c)/Math.min(List.get(i).c, List.get(s).c))) {
                    block.add(List.get(s));
                    f++;
                } else if (Math.max(List.get(i).a, List.get(s).a)/Math.min(List.get(i).a, List.get(s).a) == Math.max(List.get(i).c, List.get(s).c)/Math.min(List.get(i).c, List.get(s).c) && (Math.max(List.get(i).a, List.get(s).a)/Math.min(List.get(i).a, List.get(s).a) == Math.max(List.get(i).b, List.get(s).b)/Math.min(List.get(i).b, List.get(s).b) || Math.max(List.get(i).b, List.get(s).b)/Math.min(List.get(i).b, List.get(s).b) == Math.max(List.get(i).c, List.get(s).c)/Math.min(List.get(i).c, List.get(s).c))) {
                    block.add(List.get(s));
                    f++;
                }

            }
            res.add(block);
        }
        return res;
    }

    public static List<Triangle> toTriangles(List<int[]> list){
        List<Triangle> res = new ArrayList<>();
        Triangle t;
        for (int i = 0; i < list.size(); i++){
            t = new Triangle(list.get(i));
            res.add(t);
        }
        return res;
    }
    public static int[][][] trianglesToArr(List<List<Triangle>> list){
        int[][][] res = new int[list.size()][][];
        for (int i = 0; i < list.size(); i++){
            List<Triangle> row = list.get(i);
            int[][] copy = new int[row.size()][];
            for (int j = 0; j < row.size(); j++){
                copy[j] = row.get(j).toArray();
            }
            res[i] = copy;
        }
        return res;
    }

    public static int[][] trianglesToArr2(List<List<Triangle>> list){
        int[][] res = new int[1][];
        int k = 0;
        for (int i = 0; i < list.size(); i++){
            for (int d = 0; d < list.get(i).size(); d++){
                res[k++] = list.get(i).get(d).toArray();
                res = Arrays.copyOf(res, res.length+1);
            }
            res[res.length-1] = new int[]{0, 0, 0, 0, 0, 0};
            res = Arrays.copyOf(res, res.length+1);
            k++;
        }

        int[][] res1 = new int[res.length - 2][];
        for (int i = 0; i < res.length-2; i++) {
            res1[i] = res[i];
        }
        return res1;
    }

    public static void main(String[] args) throws Exception {
        CmdParams params = parseArgs(args);
        if (params.test){
            for (int i = 1; i <= 5; i++) {
                params.inputFile = String.format("%s\\task_10_19\\src\\tests\\input0%s.txt", System.getProperty("user.dir"), i);
                params.outputFile = String.format("%s\\task_10_19\\src\\tests\\output0%s.txt", System.getProperty("user.dir"), i);
                int[][] listArr = ArrayUtils.readIntArray2FromFile(params.inputFile);
                if (listArr.length == 0 || listArr == null) {
                    System.err.printf("Can't read array from \"%s\"%n", params.inputFile);
                    System.exit(70);
                }
                List<List<Triangle>> list = solution(toTriangles(Arrays.asList(listArr)));
                ArrayUtils.writeArrayToFile(params.outputFile, trianglesToArr2(list));
            }
        }
        if (params.window) {
            GuiMain.winMain();
        } else {
            int[][] listArr = ArrayUtils.readIntArray2FromFile(params.inputFile);
            if (listArr.length == 0 || listArr == null) {
                System.err.printf("Can't read array from \"%s\"%n", params.inputFile);
                System.exit(70);
            }
            List<List<Triangle>> list = solution(toTriangles(Arrays.asList(listArr)));
            ArrayUtils.writeArrayToFile(params.outputFile, trianglesToArr2(list));
        }
    }
}