import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TreeNode {
    public String root;
    public List<TreeNode> children = new ArrayList<>();
    public List<Object> nodes = new ArrayList<>();
    public HashMap<Integer, Integer> levels = new HashMap<>();
    public TreeNode(String root){
        this.root = root;
    }
    public void addNode(TreeNode obj){
        this.children.add(obj);
    }
    public void getAllNodes(){
        this.nodes.add(Arrays.asList(this.root, 0));
        for (TreeNode child: children){
            this.nodes.add(Arrays.asList(child.root, 1));
            if (child.children != null){
                child.getChildNodes(this.nodes, 2);
            }
        }
        for (Object i: this.nodes){
            List<Object> d = (List<Object>) Main.convertObjectToList(i);
            if (d.size() > 1){
                int level = (int) d.get(1);
                if (!this.levels.containsKey(level)) this.levels.put(level, 1);
                else this.levels.put(level, this.levels.get(level)+1);
            }
        }
    }
    public void getChildNodes(List<Object> tree, int level){
        for (TreeNode child: this.children){
            tree.add(Arrays.asList(child.root, level));
            if (child.children != null){
                child.getChildNodes(tree, level+1);
            }
        }
    }
    public void printed(){
        this.nodes = new ArrayList<>();
        this.getAllNodes();
        StringBuilder text = new StringBuilder();
        for (Object i: this.nodes){
            List<Object> f = (List<Object>) Main.convertObjectToList(i);
            if ((int) f.get(1) == 0) text.append(f.get(0)).append("\n");
            else {
                for (int d = 1; d < (int) f.get(1); d++){
                    if (this.levels.get(d) > 0) text.append("| ");
                    else text.append("  ");
                }
                text.append("+-").append(f.get(0)).append("\n");
                this.levels.put((int) f.get(1), this.levels.get((int) f.get(1))-1);
            }
        }
        System.out.println(text.deleteCharAt(text.length()-1));
    }
}

