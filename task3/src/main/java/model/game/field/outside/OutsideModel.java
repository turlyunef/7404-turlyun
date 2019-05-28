package model.game.field.outside;

import model.game.field.Model;

public interface OutsideModel extends Model {

    CellStatus changeCellStatus(int rowIndex, int colIndex);

    void setOpen(int rowIndex, int colIndex);

    int getFlagCount();
}