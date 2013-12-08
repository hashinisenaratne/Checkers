/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

public class Chip {

    private int col, row;
    private boolean onBoard, king;

    public Chip(int col, int row) {
        this.col = col;
        this.row = row;
        onBoard = true;
        king = false;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setIsKing(boolean isKing) {
        this.king = isKing;
    }

    public void setOnBoard(boolean onBoard) {
        this.onBoard = onBoard;
    }

    public boolean isKing() {
        return king;
    }

    public boolean isOnBoard() {
        return onBoard;
    }

    @Override
    public boolean equals(Object obj) {
        Chip tmpChip=(Chip)obj;
        if(tmpChip.getCol()==this.getCol() && tmpChip.getRow()==this.getRow()){
            return true;
        }
        return super.equals(obj);
    }
    
}

