package view;

import controller.BoardController;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import model.Board;
import model.Sweet;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class BoardView extends GridPane implements Observer {

    private Board board;
    private BoardController boardController;

    public BoardView(Board board, BoardController boardController) {
        this.board = board;
        this.boardController = boardController;

        init();
    }

    private void init() {

        board.addVue(this);

        refreshGrid();

    }

    private void refreshGrid() {

        Sweet[][] grid = board.getGrid();

        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                Sweet sweet = grid[i][j];
                SweetView sweetView = new SweetView(sweet);
                sweetView.addEventHandler(MouseEvent.MOUSE_CLICKED, boardController);
                this.add(sweetView, sweet.getCol(), sweet.getRow());
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public BoardController getBoardController() {
        return boardController;
    }

    public void setBoardController(BoardController boardController) {
        this.boardController = boardController;
    }

    @Override
    public void update(Observable o, Object arg) {
        getChildren().clear();

        refreshGrid();

        init();
    }
}
