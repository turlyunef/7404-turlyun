package model.game.field.outside;

import model.game.field.Model;

public interface OutsideModel extends Model {

    CellStatus changeCellStatus(int rowIndex, int columnIndex);

    void setOpen(int rowIndex, int columnIndex);

    int getFlagCount();
}