package view;

public class GameProperties {
    private int bombsCount;
    private int rows;
    private int cols;

    public GameProperties() {
        setProperties(Constants.DEFAULT_BOMBS_COUNT, Constants.DEFAULT_ROWS_COUNT, Constants.DEFAULT_COLUMNS_COUNT);
    }

    public void setProperties(int bombsCount, int rows, int cols) {
        this.bombsCount = bombsCount;
        this.rows = rows;
        this.cols = cols;
    }

    public int getBombsCount() {
        return bombsCount;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }
}