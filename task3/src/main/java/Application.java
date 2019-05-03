import model.game.ContentTable;
import model.game.TableGenerationException;

public class Application {
    public static void main(String[] args) throws TableGenerationException {
        ContentTable contentTable = new ContentTable();
        System.out.println("Table:");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (contentTable.cellIsBomb(i, j)) {
                    System.out.print(" X ");
                } else {
                    System.out.printf(" %d ", contentTable.getCell(i, j).getBombsAroundCellCount());
                }
            }
            System.out.println();
        }
    }
}
