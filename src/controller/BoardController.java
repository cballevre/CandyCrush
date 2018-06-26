package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import model.Board;
import model.Sweet;
import view.SweetView;

import java.util.ArrayList;

public class BoardController implements EventHandler{

    private Board board;
    private Sweet selected;

    public BoardController(Board board) {
        this.board = board;
    }

    @Override
    public void handle(Event event) {

        Sweet clicked = ((SweetView) event.getSource()).getSweet();

        if(selected != null) {
            if(!selected.equals(clicked)) {
                if(selected.isNeighbor(clicked)) {
                    board.swapSweet(selected, clicked);
                    if(searchLine(selected)) {
                        selected.setSelected(false);
                        selected = null;
                    } else {
                        board.swapSweet(clicked, selected);
                    }
                } else {
                    selected.setSelected(false);
                    selected = clicked;
                    selected.setSelected(true);
                }
            } else {
                selected.setSelected(false);
                selected = null;
            }
        } else {
            selected = clicked;
            selected.setSelected(true);
        }

    }

    public boolean searchLine(Sweet selected) {

        return true;


    }

}