package model;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Board extends Observable{

    /**
     * Data of the game board
     */
    private Sweet[][] grid;

    /**
     * Horizontal length of the game board
     */
    private int nbCol;

    /**
     * Vertical length of the game board
     */
    private int nbRow;

    /**
     * Create a new game board
     * @param nbCol : Horizontal length
     * @param nbRow : Vertical length
     */
    public Board(int nbCol, int nbRow) {
        this.nbCol = nbCol;
        this.nbRow = nbCol;

        init();
    }

    /**
     * Generate a random game board fill with sweet
     */
    private void init() {

        grid = new Sweet[nbCol][nbRow];

        for (int col = 0; col < nbCol; col++) {
            for (int row = 0; row < nbRow; row++) {
                Sweet sweet;
                do {
                    sweet = Sweet.makeRandom(col, row);
                } while ((col >= 2 && grid[col - 1][row].getColor() == sweet.getColor() && grid[col - 2][row].getColor() == sweet.getColor())
                || (row >= 2 && grid[col][row - 1].getColor() == sweet.getColor() && grid[col][row - 2].getColor() == sweet.getColor()));
                grid[col][row] = sweet;
            }
        }
    }


    public void removeListSweet(ArrayList<Sweet> list) {
        for (Sweet sweet : list) {
            grid[sweet.getCol()][sweet.getRow()].setDeleted(true);
        }
        notifVues();
    }

    public void removeSweet(Sweet sweet) {
        grid[sweet.getCol()][sweet.getRow()].setDeleted(true);
        notifVues();
    }

    public void swapSweet(Sweet selected, Sweet clicked) {

        int tmpCol = selected.getCol();
        int tmpRow = selected.getRow();

        int x1 = clicked.getCol() - selected.getCol();
        int y1 = clicked.getRow() - selected.getRow();

        int x2 = selected.getCol() - clicked.getCol();
        int y2 = selected.getRow() - clicked.getRow();

        grid[clicked.getCol()][clicked.getRow()] = selected;
        selected.setCol(clicked.getCol());
        selected.setRow(clicked.getRow());
        selected.setMoves(Direction.valueOf(x2, y2));

        grid[tmpCol][tmpRow] = clicked;
        clicked.setCol(tmpCol);
        clicked.setRow(tmpRow);
        clicked.setMoves(Direction.valueOf(x1, y1));

        notifVues();

    }

    public void replaceSweet(Sweet sweet, int i, int j) {

        int tmpCol = sweet.getCol();
        int tmpRow = sweet.getRow();

        int x = sweet.getCol() - i;
        int y = sweet.getRow() - j;

        grid[i][j] = sweet;
        sweet.setCol(i);
        sweet.setRow(j);
        sweet.setMoves(Direction.valueOf(x, y));

        grid[tmpCol][tmpRow] = null;
        notifVues();

    }

    public void generateSweet(int i, int j) {
        Sweet sweet = Sweet.makeRandom(i, j);
        grid[i][j] = sweet;
        notifVues();
    }


    /**
     *
     * @param vue
     */
    public void addVue(Observer vue) {
        addObserver(vue);
    }

    public void removeVue(Observer vue) {
        deleteObserver(vue);
    }

    public void notifVues() {
        setChanged();
        notifyObservers();
    }


    public Sweet[][] getGrid() {
        return grid;
    }

    public void setGrid(Sweet[][] grid) {
        this.grid = grid;
        notifVues();
    }

    public int getNbCol() {
        return nbCol;
    }

    public void setNbCol(int nbCol) {
        this.nbCol = nbCol;
        notifVues();
    }

    public int getNbRow() {
        return nbRow;
    }

    public void setNbRow(int nbRow) {
        this.nbRow = nbRow;
        notifVues();
    }

}
