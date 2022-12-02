public class Array {
    public int array[];

    public Array(int array[]){
        this.array = array;
    }

    public void output(){
        System.out.print("{");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i != array.length-1) System.out.print(", ");
        }
        System.out.println("}");
    }
}
