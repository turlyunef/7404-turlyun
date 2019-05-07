package controller;

import model.game.Model;

public class Controllers {
    private Controller[][] controllers;
    private Model model;

    public Controllers(int rowsCount, int columnsCount, Model model) {
        this.controllers = new Controller[rowsCount][columnsCount];
        this.model = model;
    }

    public void setController(Controller controller, int rowIndex, int columnIndex) {
        this.controllers[rowIndex][columnIndex] = controller;
    }

    public void addCellsAround(int rowIndex, int columnIndex, int columnsCount) {
        if (model.getCell(rowIndex, columnIndex).getBombsAroundCellCount() == 0){
            if (rowIndex > 0) {

                if (columnIndex > 0) {
                    controllers[rowIndex][columnIndex].addObserver(controllers[rowIndex - 1][columnIndex - 1]);
                }
                if ((columnIndex < columnsCount - 1)) {
                    controllers[rowIndex][columnIndex].addObserver(controllers[rowIndex - 1][columnIndex + 1]);
                }
                controllers[rowIndex][columnIndex].addObserver(controllers[rowIndex - 1][columnIndex]);
            }
            if (rowIndex < controllers.length - 1) {
                if (columnIndex > 0) {
                    controllers[rowIndex][columnIndex].addObserver(controllers[rowIndex + 1][columnIndex - 1]);
                }
                if ((columnIndex < columnsCount - 1)) {
                    controllers[rowIndex][columnIndex].addObserver(controllers[rowIndex + 1][columnIndex + 1]);
                }
                controllers[rowIndex][columnIndex].addObserver(controllers[rowIndex + 1][columnIndex]);
            }
            if (columnIndex > 0) {
                controllers[rowIndex][columnIndex].addObserver(controllers[rowIndex][columnIndex - 1]);
            }
            if ((columnIndex < columnsCount - 1)) {
                controllers[rowIndex][columnIndex].addObserver(controllers[rowIndex][columnIndex + 1]);
            }
        }
    }
}
