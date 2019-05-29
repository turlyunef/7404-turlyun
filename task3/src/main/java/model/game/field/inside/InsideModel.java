package model.game.field.inside;

import model.game.field.Model;

/**
 * Model level abstraction interface that displays the internal state of a closed cell: the number of bombs around or the bomb
 */
public interface InsideModel extends Model {

    /**
     * Returns a cell of the playing field
     *
     * @param rowIndex index on the rows of this cell
     * @param colIndex index on the columns of this cell
     * @return cell of the playing field
     */
    Cell getCell(int rowIndex, int colIndex);
}