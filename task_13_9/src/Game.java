import util.ArrayUtils;

import java.io.FileNotFoundException;
import java.util.*;


public class Game {

    private static final int HEIGHT = 9;
    private static final int COLOR_COUNT = 7;
    private static final Random rnd = new Random();
    private final String FILENAME = System.getProperty("user.dir") + "\\task_13_9\\src\\record.txt";

    private int score = 0;
    private int record = 0;
    public Ball deletedBall = null;

    private List<List<Ball>> field = null;
    private List<Ball> nextBall = new ArrayList<>();
    private List<Ball> nextBalls = new ArrayList<>();
    private List<Integer> select = new ArrayList<>();
    private final Set<List<Integer>> fullLines = new HashSet<>();
    private final Set<List<Integer>> deletedLines = new HashSet<>();
    private MainForm mainForm;

    public Game() {
    }

    public void newGame(MainForm mainForm) {
        this.mainForm = mainForm;
        if (record == 0) this.readRecord();
        score = 0;
        field = new ArrayList<>();
        for (int i = 0; i < HEIGHT; i++) {
            List<Ball> temp = new ArrayList<>();
            for (int d = 0; d < HEIGHT; d++) temp.add(null);
            field.add(temp);
        }
        for (int i = 0; i < 8;){
            int x = rnd.nextInt(HEIGHT);
            int y = rnd.nextInt(HEIGHT);
            if (getCell(x, y) == null){
                Ball ball = new Ball(i < 5 ? 1 : 0);
                this.setCell(x, y, ball);
                if (i >= 5) this.nextBalls.add(ball);
                i++;
            }
        }
        this.getCountBalls();
    }

    public void restartGame() {
        newGame(mainForm);
        mainForm.time = 0;
        mainForm.timer.start();
    }
    
    public void gameOver() throws FileNotFoundException {
        if (score > record){
            record = score;
            writeRecord();
        }
        EndGame endGame = new EndGame(this);
        mainForm.timer.stop();
        endGame.setVisible(true);
    }

    private int checkGame(int n) throws FileNotFoundException {
        int canGenerate = Math.min(81 - this.getCountBalls(), n);
        if (canGenerate == 0){
            getFullLines();
            if (fullLines.size() > 0){
                deleteFullLines();
            } else gameOver();
        }
        return canGenerate;
    }

    private void generate() throws FileNotFoundException {
        for (List<Ball> i: this.field) for (Ball d: i) if (d != null && d.getGain() == 0) d.grow();
        this.getFullLines(); if (fullLines.size() > 0) deleteFullLines();
        checkGame(1);
        for (Ball i: this.nextBall){
            while (true){
                int x = rnd.nextInt(HEIGHT);
                int y = rnd.nextInt(HEIGHT);
                if (getCell(x, y) == null) {
                    setCell(x, y, i);
                    break;
                }
            }
            i.grow();
        }
        this.getFullLines(); if (fullLines.size() > 0) deleteFullLines();
        this.nextBall = new ArrayList<>();
        this.nextBalls = new ArrayList<>();
        int canGenerate = checkGame(3);
        for (int i = 0; i < canGenerate;){
            int x = rnd.nextInt(HEIGHT);
            int y = rnd.nextInt(HEIGHT);
            if (this.getCell(x, y) == null){
                Ball ball = new Ball(0);
                this.setCell(x, y, ball);
                this.nextBalls.add(ball);
                i++;
            }
        }
        this.getFullLines(); if (fullLines.size() > 0) deleteFullLines();
    }

    public int getScore(){
        return this.score;
    }

    public int getCountBalls(){
        int countBalls = 0;
        for (List<Ball> i: this.field) for (Ball d: i) if (d != null && d.getGain() == 1) countBalls++;
        return countBalls;
    }

    private void move(int x_old, int y_old, int x_new, int y_new){
        Ball ball = this.getCell(x_old, y_old);
        Ball ballOnNew = this.getCell(x_new, y_new);
        this.setCell(x_new, y_new, ball);
        if (ballOnNew != null && ballOnNew.getGain() == 0) this.nextBall.add(ballOnNew);
        this.setCell(x_old, y_old, null);
    }

    private boolean canMove(int start_x, int start_y, int end_x, int end_y){
        Ball start_ball = this.getCell(start_x, start_y);
        Ball ball = this.getCell(end_x, end_y);
        if ((ball != null && ball.getGain() == 1) || (start_ball != null && start_ball.getGain() == 0)) return false;
        List<List<Integer>> queue = new ArrayList<>();
        List<List<Integer>> visitedCells = new ArrayList<>();
        queue.add(Arrays.asList(start_x, start_y));
        while (queue.size() != 0) {
            List<Integer> cord = queue.remove(0);
            if (cord.get(0) < 0 || cord.get(0) >= HEIGHT || cord.get(1) < 0 || cord.get(1) >= HEIGHT) continue;
            ball = this.getCell(cord.get(0), cord.get(1));
            if ((!cord.equals(Arrays.asList(start_x, start_y)) && ball != null && ball.getGain() == 1) || visitedCells.contains(Arrays.asList(cord.get(0), cord.get(1))))
                continue;
            if (cord.get(0) == end_x && cord.get(1) == end_y) return true;
            visitedCells.add(Arrays.asList(cord.get(0), cord.get(1)));
            for (int y = -1; y < 2; y++) {
                for (int x = -1; x < 2; x++) {
                    if (x != 0 && y != 0) continue;
                    queue.add(Arrays.asList(cord.get(0) + x, cord.get(1) + y));
                }
            }
        }
        return false;
    }

    private void getFullLines(){
        for (int y = 0; y < HEIGHT; y++){
            for (int x = 0; x < HEIGHT; x++){
                this.getFullLine(x, y);
                for (List<Integer> i: fullLines){
                    int x1 = i.get(0);
                    int y1 = i.get(1);
                    if (x1 != x && y1 != y){
                        this.getFullLine(x1, y1);
                    }
                }
            }
        }
    }

    private void getFullLine(int x, int y){
        Ball ball = this.getCell(x, y);
        if (ball == null || ball.getGain() == 0) return;
        int current_color = ball.getColorID();
        List<List<Integer>> forDelete;

        // Горизонталь
        forDelete = new ArrayList<>();
        for (int plus_x = x; plus_x < HEIGHT && this.getCell(plus_x, y) != null && this.getCell(plus_x, y).getGain() == 1 && this.getCell(plus_x, y).getColorID() == current_color; plus_x++){
            forDelete.add(Arrays.asList(plus_x, y));
        }
        for (int minus_x = x-1; minus_x >= 0 && this.getCell(minus_x, y) != null && this.getCell(minus_x, y).getGain() == 1 && this.getCell(minus_x, y).getColorID() == current_color; minus_x--){
            forDelete.add(Arrays.asList(minus_x, y));
        }
        if (new HashSet<>(forDelete).size() >= 5) fullLines.addAll(forDelete);

        // Вертикаль
        forDelete.clear();
        for (int plus_y = y; plus_y < HEIGHT && this.getCell(x, plus_y) != null && this.getCell(x, plus_y).getGain() == 1 && this.getCell(x, plus_y).getColorID() == current_color; plus_y++){
            forDelete.add(Arrays.asList(x, plus_y));
        }
        for (int minus_y = y-1; minus_y >= 0 && this.getCell(x, minus_y) != null && this.getCell(x, minus_y).getGain() == 1 && this.getCell(x, minus_y).getColorID() == current_color; minus_y--){
            forDelete.add(Arrays.asList(x, minus_y));
        }
        if (new HashSet<>(forDelete).size() >= 5) fullLines.addAll(forDelete);

        // Диагональ 1
        forDelete.clear();
        for (int plus_y = y, plus_x = x; plus_y < HEIGHT && plus_x < HEIGHT && this.getCell(plus_x, plus_y) != null && this.getCell(plus_x, plus_y).getGain() == 1 && this.getCell(plus_x, plus_y).getColorID() == current_color; plus_y++, plus_x++){
            forDelete.add(Arrays.asList(plus_x, plus_y));
        }
        for (int minus_y = y, minus_x = x; minus_y >= 0 && minus_x >= 0 && this.getCell(minus_x, minus_y) != null && this.getCell(minus_x, minus_y).getGain() == 1 && this.getCell(minus_x, minus_y).getColorID() == current_color; minus_y--, minus_x--){
            forDelete.add(Arrays.asList(minus_x, minus_y));
        }
        if (new HashSet<>(forDelete).size() >= 5) fullLines.addAll(forDelete);

        // Диагональ 2
        forDelete.clear();
        for (int plus_y = y, minus_x = x; plus_y < HEIGHT && minus_x >= 0 && this.getCell(minus_x, plus_y) != null && this.getCell(minus_x, plus_y).getGain() == 1 && this.getCell(minus_x, plus_y).getColorID() == current_color; plus_y++, minus_x--){
            forDelete.add(Arrays.asList(minus_x, plus_y));
        }
        for (int minus_y = y, plus_x = x; plus_x < HEIGHT && minus_y >= 0 && this.getCell(plus_x, minus_y) != null && this.getCell(plus_x, minus_y).getGain() == 1 && this.getCell(plus_x, minus_y).getColorID() == current_color; plus_x++, minus_y--){
            forDelete.add(Arrays.asList(plus_x, minus_y));
        }
        if (new HashSet<>(forDelete).size() >= 5) fullLines.addAll(forDelete);
    }

    private void scoring(int length){
        int past_score = score;
        this.score += 5;
        length -= 5;
        for (int i = 2; length > 0; i++, length--) this.score += i;
        MainForm.msg = String.format("+%s очков!", score-past_score);
    }

    public void deleteFullLines(){
        this.scoring(fullLines.size());
        for (List<Integer> i: fullLines) {
            int x = i.get(0), y = i.get(1);
            this.deletedBall = this.getCell(x, y);
            this.setCell(x, y, null);
        }
        deletedLines.addAll(fullLines);
        fullLines.clear();
    }

    private void step(int x_old, int y_old, int x_new, int y_new) throws FileNotFoundException {
        if (this.canMove(x_old, y_old, x_new, y_new)){
            this.move(x_old, y_old, x_new, y_new);
            select = new ArrayList<>();
        }
        if (select.size() == 0){
            this.getFullLines();
            if (this.fullLines.size() > 0) {
                this.deleteFullLines();
                if (this.nextBall.size() > 0) this.setCell(x_new, y_new, this.nextBall.get(0));
                if (this.getCountBalls() == 0) {
                    this.score += 1000;
                    MainForm.msg = "Вы убрали всё поле, +1000 очков!!!";
                    this.generate();
                    this.generate();
                }
            } else generate();
        } else if (select.size() == 2 && this.getCell(x_new, y_new) != null && this.getCell(x_new, y_new).getGain() == 1){
            select = Arrays.asList(x_new, y_new);
        } else MainForm.msg = "Ошибка в ходе";
    }

    public void leftMouseClick(int x, int y) throws FileNotFoundException {
        if (y < 0 || y >= this.getHeight() || x < 0 || x >= this.getHeight()) return;
        Ball ball = this.getCell(x, y);
        if ((ball == null && select.size() == 0)) return;
        if (select.size() == 2){
            this.step(select.get(0), select.get(1), x, y);
        } else if (select.size() == 0 && ball.getGain() == 1) {
            select = Arrays.asList(x, y);
        }
    }

    public void rightMouseClick(int x, int y) {
        if (y < 0 || y >= getHeight() || x < 0 || x >= getHeight()) return;
        Ball ball = this.getCell(x, y);
        if (ball == null && select.size() == 0) return;
        select = new ArrayList<>();
    }

    public boolean hasDeleted(){
        return deletedBall != null;
    }

    public Set<List<Integer>> getDeletedLines(){
        return deletedLines;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public static int getColorCount() {
        return COLOR_COUNT;
    }

    public Ball getSelect(){
        return (select.size() != 2) ? null : getCell(select.get(0), select.get(1));
    }

    public boolean hasSelect() {
        return select.size() == 2;
    }

    public Ball getNextBall(int i) {
        return (i < 0 || i >= 3) ? null : nextBalls.get(i);
    }

    public void clearDeletedLines(){
        this.deletedLines.clear();
        this.deletedBall = null;
    }

    private void readRecord(){
        record = Objects.requireNonNull(ArrayUtils.readIntArrayFromFile(FILENAME))[0];
    }

    void writeRecord() throws FileNotFoundException {
        ArrayUtils.writeArrayToFile(FILENAME, new int[]{record});
    }

    public int getRecord(){
        return record;
    }

    private void setCell(int x, int y, Ball ball){
        if (y < 0 || y >= HEIGHT || x < 0 || x >= HEIGHT) return;
        field.get(y).set(x, ball);
    }

    public Ball getCell(int x, int y) {
        return (y < 0 || y>= HEIGHT || x < 0 || x >= HEIGHT) ? null : field.get(y).get(x);
    }

    public int getNextBallSize() {
        return nextBalls.size();
    }

    public int getTime() {
        return mainForm.time;
    }
}
