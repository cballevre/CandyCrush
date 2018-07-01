package view;

import controller.BoardController;
import javafx.animation.AnimationTimer;
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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BoardView extends GridPane implements Observer {

    private Board board;
    private BoardController boardController;
    private boolean echeance = false;

    private final BlockingQueue<Runnable> updateQueue = new ArrayBlockingQueue<>(1);

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

        try {
            updateQueue.put(() -> {
                for (int i = 0; i < board.getWidth(); i++) {
                    for (int j = 0; j < board.getHeight(); j++) {
                        Sweet sweet = grid[i][j];

                        if (sweet != null) {

                            SweetView sweetView = new SweetView(sweet);
                            Direction direction = sweet.getMoves();

                            sweetView.addEventHandler(MouseEvent.MOUSE_CLICKED, boardController);

                            if (direction != null) {

                                System.out.println("hello1");

                                final TranslateTransition translateT = new TranslateTransition(Duration.seconds(0.3), sweetView);
                                translateT.setInterpolator(Interpolator.EASE_BOTH);

                                if (direction.getX() != 0) {
                                    translateT.setFromX(75 * direction.getX());
                                    translateT.setToX(0);
                                } else if (direction.getY() != 0) {
                                    translateT.setFromY(75 * direction.getY());
                                    translateT.setToY(0);
                                }

                                translateT.setOnFinished(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        echeance = true;

                                    }
                                });

                                translateT.play();

                                sweet.setMoves(null);

                            }

                            this.add(sweetView, sweet.getCol(), sweet.getRow());

                        }
                    }
                }

            });
        } catch (InterruptedException e) {
            e.printStackTrace();
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

        AnimationTimer anim = new AnimationTimer() {
            @Override
            public void handle(long temps) {
                if (!echeance) {
                    refreshGrid();
                }
            }
        };
        anim.start();
    }
}
