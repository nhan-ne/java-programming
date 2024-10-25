package resource.code.Model;

public class GameModel {
    private final int rows;
    private final int cols;

    public GameModel(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public boolean isBorder(int row, int col) {
        return row == 0 || row == rows - 1 || col == 0 || col == cols - 1;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}