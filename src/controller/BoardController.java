package controller;

import javafx.event.Event;
import javafx.event.EventHandler;
import model.*;
import view.SweetView;

import java.util.ArrayList;
import java.util.Collections;

public class BoardController implements EventHandler{

    /**
     * Modèle du plateau de jeu
     */
    private Board board;

    /**
     * Dernier bonbon selectionné lors d'un click
     */
    private Sweet selected;

    public BoardController(Board board) {
        this.board = board;
    }

    /**
     * Event Handler lors d'un click sur un bonbon. Permet de gerer le gameplay.
     * @param event
     */
    @Override
    public void handle(Event event) {

        Sweet clicked = ((SweetView) event.getSource()).getSweet();

        if(selected != null) {

            if(selected.isNeighbor(clicked)) {

                board.swapSweet(selected, clicked);

                if(detectChains(selected) || detectChains(clicked)) {
                    do {
                        reorder();
                    } while(makeCombo());
                } else {
                    board.setGrid(swapSweet(board.getGrid(),clicked, selected));
                }

                selected.setSelected(false);
                selected = null;

            } else {
                selected.setSelected(false);
                selected = clicked;
                selected.setSelected(true);
            }
        } else {
            selected = clicked;
            selected.setSelected(true);
        }
    }

    /**
     * Detecter s'il existe des chaines de bonbon de la même couleur et si tel est le cas les suppriment.
     * @param sweet : Bonbon concerner par la detection
     * @return : True s'il y a une ou plusieurs chaines et False s'il n'a rien trouvé
     */
    private boolean detectChains(Sweet sweet) {

        boolean result = false;
        Sweet[][] grid = board.getGrid();

        ArrayList<Sweet> listCol = new ArrayList<>();
        ArrayList<Sweet> listRow = new ArrayList<>();

        listRow.addAll(scan(sweet, Direction.LEFT));
        listRow.addAll(scan(sweet, Direction.RIGHT));

        listCol.addAll(scan(sweet, Direction.UP));
        listCol.addAll(scan(sweet, Direction.DOWN));

        if((listCol.size() + 1) > 2) {
            if((listCol.size()+1) > 3) {
                Sweet tmp = listCol.remove((listCol.size()+1)/2);
                tmp.setType(TypeSweet.COL);
            }
            grid = removeListSweet(grid, listCol);
            result = true;
        }

        if((listRow.size() + 1) > 2) {
            if((listRow.size()+1) > 3) {
                Sweet tmp = listRow.remove((listRow.size()+1)/2);
                tmp.setType(TypeSweet.ROW);
            }
            grid = removeListSweet(grid, listRow);
            result = true;
        }

        if(result) {
            grid = removeSweet(grid, sweet);
            board.setGrid(grid);
        }

        return result;

    }

    /**
     * Gere la gravité des bonbons afin de supprimer tout les espaces vide crée par les bonbons supprimer
     */
    private void reorder() {

        Sweet[][] grid = board.getGrid();

        for (int col = 0; col < board.getNbCol(); col++) {

            int row = 0;

            while (row < board.getNbRow()) {
                if(grid[col][row] == null) {
                    int test = row;
                    do {
                        try {
                            Sweet sweet = grid[col][test-1];
                            if(grid[col][row-1] != null) {
                                grid = replaceSweet(grid, grid[col][test-1], col, test);
                            }
                        } catch (Exception e) {
                            Sweet sweet = Sweet.makeRandom(col,test);
                            sweet.setMoves(Direction.UP);
                            grid[col][test] = sweet;
                        }
                        test--;
                    } while (test-1 > 0);
                    row = 0;
                } else {
                    row++;
                }
            }
        }

        board.setGrid(grid);

    }

    /**
     * Cherche et realise les combo de bonbons sur la grille
     * @return S'il y a un combo, la fonction retourne true sinon elle retourne false
     */
    private boolean makeCombo() {

        boolean result = false;
        Sweet[][] grid = board.getGrid();

        ArrayList<Sweet> list = new ArrayList<>();

        for (int row = 0; row < board.getNbRow(); row++) {
            for (int col = 0; col < board.getNbCol(); col++) {
                if (grid[col][row] != null) {
                    if (col < board.getNbCol() - 1) {
                        ArrayList<Sweet> tmp = scan(grid[col][row], Direction.RIGHT);
                        if((tmp.size()+1) > 2) {
                            if((tmp.size()+1) > 3) {
                                Sweet sweet = tmp.remove((tmp.size()+1)/2);
                                sweet.setType(TypeSweet.ROW);
                            }
                            list.addAll(tmp);
                        }
                    }
                    if (row < board.getNbRow() - 1) {
                        ArrayList<Sweet> tmp = scan(grid[col][row], Direction.DOWN);
                        if((tmp.size()+1) > 2) {
                            if((tmp.size()+1) > 3) {
                                Sweet sweet = tmp.remove((tmp.size()+1)/2);
                                sweet.setType(TypeSweet.COL);
                            }
                            list.addAll(tmp);
                        }
                    }
                }
            }
        }

        if(list.size() > 0) {
            result = true;
        }

        removeListSweet(grid, list);

        board.setGrid(grid);

        return result;
    }

    /**
     * Détecte la chaine de bonbon dans une direction donnée
     * @param sweet : Bonbon concerner par la detection
     * @param direction : La direction de la detection
     * @return : Retourne la chaine de bonbon trouvé sinon une liste vide
     */
    private ArrayList<Sweet> scan(Sweet sweet, Direction direction) {

        Color sweetColor = sweet.getColor();
        ArrayList<Sweet> list = new ArrayList<>();
        Sweet[][] grid = board.getGrid();
        boolean test = true;
        int i = 1;

        do {

            Sweet selected = null;

            try {
                if(direction.getX() != 0) {
                    selected = grid[sweet.getCol() + (i * direction.getX())][sweet.getRow()];
                } else {
                    selected = grid[sweet.getCol()][sweet.getRow() + (i * direction.getY())];
                }

                if(selected.getColor() == sweetColor) {
                    list.add(selected);
                } else{
                    test = false;
                }

            } catch (Exception e) {
                test = false;
            }


            i++;

        } while(test);

        return list;

    }

    /**
     * Permet la suppression d'un ensemble de bonbon sans avoir à rafraichir la grid en front
     * @param grid : Grille de bonbon a modifier
     * @param list : Liste d'objet a supprimer
     * @return : Retourne la grille modifier
     */
    private Sweet[][] removeListSweet(Sweet[][] grid, ArrayList<Sweet> list) {
        for (Sweet sweet : list) {
            if(sweet.getType() == TypeSweet.COL) {
                for (int col = 0; col < board.getNbCol(); col++) {
                    grid[sweet.getCol()][col] = null;
                }
            } else if(sweet.getType() == TypeSweet.ROW) {
                for (int row = 0; row < board.getNbRow(); row++) {
                    grid[sweet.getCol()][row] = null;
                }
            }
            grid[sweet.getCol()][sweet.getRow()] = null;
        }
        return grid;
    }

    /**
     * Permet la suppression d'un bonbon sans avoir à rafraichir la grid en front
     * @param grid : Grille de bonbon a modifier
     * @param sweet : Le bonbon a supprimer
     * @return : Retourne la grille modifier
     */
    private Sweet[][] removeSweet(Sweet[][] grid, Sweet sweet) {
        grid[sweet.getCol()][sweet.getRow()] = null;
        return grid;
    }

    /**
     * Permet l'échange de deux bonbon sans avoir à rafraichir la grid en front
     * @param grid : Grille de bonbon a modifier
     * @param selected
     * @param clicked
     * @return : Retourne la grille modifier
     */
    private Sweet[][] swapSweet(Sweet[][] grid, Sweet selected, Sweet clicked) {

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

        return grid;

    }

    /**
     * Permet de placer un bonbon dans une nouvelle case sans avoir à rafraichir la grid en front
     * @param grid : Grille de bonbon a modifier
     * @param sweet : Bonbon a placer
     * @param i : col de la nouvelle case
     * @param j : row de la nouvelle case
     * @return : Retourne la grille modifier
     */
    private Sweet[][] replaceSweet(Sweet[][] grid, Sweet sweet, int i, int j) {

        int tmpCol = sweet.getCol();
        int tmpRow = sweet.getRow();

        int x = sweet.getCol() - i;
        int y = sweet.getRow() - j;

        grid[i][j] = sweet;
        sweet.setCol(i);
        sweet.setRow(j);
        sweet.setMoves(Direction.valueOf(x, y));

        grid[tmpCol][tmpRow] = null;

        return grid;

    }

}

