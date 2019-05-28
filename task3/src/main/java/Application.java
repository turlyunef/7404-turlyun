import view.GameFrame;

public class Application {
    /**
     * Game Minesweeper. Designed for desktop. Designed according to the MVC pattern.
     * The model can be used on the server to implement the game in the web architecture.
     * The user will not be able to influence the result, since the win check is performed in the model.
     * The controller performs the opening of the cells of the field with the execution of the query content in the model.
     * View design is based on buttons as cells.
     *
     * @author Turlyun Evgeny Fedorovich
     * @see GameFrame
     * @see controller.Controllers
     * @see model.game.MainModel
     */
    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
        gameFrame.initFrame();
    }
}