package model.game.field.inside;

import model.game.field.Model;

public interface InsideModel extends Model {

    Cell getCell(int rowIndex, int columnIndex);
}