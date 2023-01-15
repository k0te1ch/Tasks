import java.util.Random;

public class Ball {
    private final Random rnd = new Random();
    private final int colorID = rnd.nextInt(Game.getColorCount());
    private int gain;
    public Ball(int gain){
        this.gain = gain;
    }

    public int getColorID() {
        return this.colorID;
    }
    public int getGain() {
        return this.gain;
    }
    public void grow() {
        if (this.gain < 1) this.gain += 1;
    }
}
