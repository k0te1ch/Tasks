import java.util.*;

public class Main {
    public static int g;
    public static TreeNode f(String text, int n){
        boolean add = false, tree = false, root = true;
        StringBuilder temp_str = new StringBuilder();
        TreeNode node = new TreeNode("");
        if (n == 0) tree = true;
        for (int i = n; i < text.length(); i++){
            char e = text.charAt(i);
            if (e == '('){
                if (add){
                    node.addNode(f(text, i));
                    i = g;
                }
                add = true;
                temp_str = new StringBuilder();
            } else if (e == ')' || e == ','){
                if (!temp_str.isEmpty()){
                    if (root) {
                        node.root = temp_str.toString();
                        root = false;
                    } else {
                        node.addNode(new TreeNode(temp_str.toString()));
                    }
                    temp_str = new StringBuilder();
                } if (e == ')'){
                    g = i;
                    return node;
                }
            } else temp_str.append(e);
        }
        return node;
    }

    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return list;
    }

    public static void main(String[] args) {
        String text = "(a, second, (abc, y, (x, 7), uuu, (8, 9, (10, 1))), abcddcdba)";
        f(text.replaceAll(" ", ""), 0).printed();
    }
}
