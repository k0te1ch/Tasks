public class Triangle {
    int x1, x2, x3, y1, y2, y3;
    double a, a1, b, b1, c, c1;
    public Triangle(int[] list){
        x1 = list[0]; y1 = list[1];
        x2 = list[2]; y2 = list[3];
        x3 = list[4]; y3 = list[5];
        a1 = Math.abs(x1-x2)+Math.abs(y1-y2);
        b1 = Math.abs(x1-x3)+Math.abs(y1-y3);
        c1 = Math.abs(x2-x3)+Math.abs(y2-y3);
        a = Math.max(a1, b1);
        a = Math.max(a, c1);
        c = Math.min(a1, b1);
        c = Math.min(c, c1);
        b = a1 + b1 + c1 - a - c;
    }
    public int[] toArray(){
        int[] res = {x1, x2, x3, y1, y2, y3};
        return res;
    }
}
