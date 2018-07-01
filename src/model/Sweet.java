package model;

import utils.Math;

import java.util.Observable;
import java.util.Observer;

public class Sweet extends Observable implements Observer {

    private Color color;
    private TypeSweet type = TypeSweet.NORMAL;
    private boolean isSelected = false;
    private boolean stateChanged = false;
    private Direction moves = null;
    private int col;
    private int row;

    public Sweet(Color color, int col, int row) {
        this.color = color;
        this.col = col;
        this.row = row;
    }

    public Sweet(Color color, int col, int row, TypeSweet type) {
        this.color = color;
        this.col = col;
        this.row = row;
        this.type = type;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public TypeSweet getType() {
        return type;
    }

    public void setType(TypeSweet type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
        notifView();
    }

    public Direction getMoves() {
        return moves;
    }

    public void setMoves(Direction moves) {
        this.moves = moves;
    }

    public boolean isStateChanged() {
        return stateChanged;
    }

    public void setStateChanged(boolean stateChanged) {
        this.stateChanged = stateChanged;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public static Sweet makeRandom(int col, int row) {
        int rand = Math.random(0, 5);
        return new Sweet(Color.valueOf(rand), col, row);
    }

    public void addView(Observer view)
    {
        addObserver(view);
    }

    public void removeView(Observer view)
    {
        deleteObserver(view);
    }

    public void notifView()
    {
        setChanged();
        notifyObservers();
    }

    @Override
    public void update(Observable o, Object arg) {

    }

    public boolean isNeighbor(Sweet sweet) {

        boolean result = false;

        if(sweet.getCol() == col) {
            if(sweet.getRow() + 1 == row || sweet.getRow() - 1 == row) {
                result = true;
            }
        } else if(sweet.getRow() == row) {
            if(sweet.getCol() + 1 == col || sweet.getCol() -1 == col) {
                result = true;
            }
        }

        return result;
    }
}
