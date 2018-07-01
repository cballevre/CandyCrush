package view;

import controller.BoardController;
import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.transform.Translate;
import javafx.util.Duration;
import model.Board;
import model.Direction;
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

                if(sweet != null) {

                    SweetView sweetView = new SweetView(sweet);
                    Direction direction = sweet.getMoves();

                    sweetView.addEventHandler(MouseEvent.MOUSE_CLICKED, boardController);

                    if(direction != null) {

                        final TranslateTransition translateT = new TranslateTransition(Duration.seconds(0.5), sweetView);
                        translateT.setInterpolator(Interpolator.EASE_BOTH);

                        if(direction.getX() != 0) {
                            translateT.setFromX(75 * direction.getX());
                            translateT.setToX(0);
                        } else if(direction.getY() != 0) {
                            translateT.setFromY(75 * direction.getY());
                            translateT.setToY(0);
                        }

                        translateT.play();

                        sweet.setMoves(null);

                    }

                    this.add(sweetView, sweet.getCol(), sweet.getRow());

                }
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
