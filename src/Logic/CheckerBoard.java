/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

/**
 *
 * @author user
 */
public class CheckerBoard {

    private char[][] checkersBoard;
    private int boardSize;
    private char typeR,typeB,empty;

    public CheckerBoard(int size) {
        boardSize = size;
        checkersBoard = new char[boardSize][boardSize];
        typeR='r';
        typeB='b';
        empty='_';
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    checkersBoard[i][j] = empty;    //EMPTY cell
                }
            }
        }
        for (int i = 0; i < (boardSize / 2) - 1; i++) {
            int j;
            if (i % 2 == 0) {
                j = 0;
            } else {
                j = 1;
            }
            while (j < boardSize) {
                checkersBoard[i][j] = typeR;    //RED piece
                j += 2;
            }
        }
        for (int i = boardSize - 1; i >= (boardSize) / 2 + 1; i--) {
            int j;
            if (i % 2 == 0) {
                j = 0;
            } else {
                j = 1;
            }
            while (j < boardSize) {
                checkersBoard[i][j] = typeB;     //BLACK piece
                j += 2;
            }
        }
    }

    public void printBoard() {
        System.out.println("*********************************");
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    System.out.print(checkersBoard[i][j] + " " + i + "," + j + "\t");
                } else {
                    System.out.print("\t");
                }
            }
            System.out.println("");
        }
        System.out.println("*********************************");
    }

    public boolean movePiece(int sRow, int sCol, int dRow, int dCol) {
        if (isMoveable(sRow, sCol, dRow, dCol)) {
            char tmpType = checkersBoard[sRow][sCol];
            checkersBoard[sRow][sCol] = empty;
            checkersBoard[dRow][dCol] = tmpType;
            if (tmpType == typeR && dRow == boardSize - 1) {
                checkersBoard[dRow][dCol] = Character.toUpperCase(typeR);     //uppercase typeR to represent RED QUEEN                
            }
            if (tmpType == typeB && dRow == 0) {
                checkersBoard[dRow][dCol] = Character.toUpperCase(typeB);     //uppercase typeB to represent BLACK QUEEN
            }
            return true;
        }
        return false;
    }

    public boolean isMoveable(int sRow, int sCol, int dRow, int dCol) {
        if (dRow >= boardSize || dRow < 0) {
            return false;
        }
        if (dCol >= boardSize || dCol < 0) {
            return false;
        }
        if (checkersBoard[sRow][sCol] == empty) {
            return false;
        } else if ((checkersBoard[dRow][dCol] == empty)) {
            if (sCol == dCol) {
                return false;
            }
            char tmpType = checkersBoard[sRow][sCol];
            if (tmpType == typeR) {
                if (sRow - dRow >= 0) {
                    return false;
                }
            }
            if (tmpType == typeB) {
                if (sRow - dRow <= 0) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean cutPiece(int attackerRow, int attackerCol, int victimRow, int victimCol) {
        char tmpAttacker, tmpVictim;
        if (checkersBoard[attackerRow][attackerCol] == empty || checkersBoard[victimRow][victimCol] == empty) {
            return false;
        } else {
            tmpAttacker = checkersBoard[attackerRow][attackerCol];
            tmpVictim = checkersBoard[victimRow][victimCol];
        }
        if (tmpAttacker == tmpVictim) {
            return false;
        } else {
            if (attackerRow > victimRow) {
                if (isMoveable(attackerRow, attackerCol, victimRow - 1, victimCol - 1)) {
                    checkersBoard[victimRow][victimCol] = empty;
                    movePiece(attackerRow, attackerCol, victimRow - 1, victimCol - 1);
                    return true;
                }
                if (isMoveable(attackerRow, attackerCol, victimRow - 1, victimCol + 1)) {
                    checkersBoard[victimRow][victimCol] = empty;
                    movePiece(attackerRow, attackerCol, victimRow - 1, victimCol + 1);
                    return true;
                }
            }
            if (attackerRow < victimRow) {
                if (isMoveable(attackerRow, attackerCol, victimRow + 1, victimCol - 1)) {
                    checkersBoard[victimRow][victimCol] = empty;
                    movePiece(attackerRow, attackerCol, victimRow + 1, victimCol - 1);
                    return true;
                }
                if (isMoveable(attackerRow, attackerCol, victimRow + 1, victimCol + 1)) {
                    checkersBoard[victimRow][victimCol] = empty;
                    movePiece(attackerRow, attackerCol, victimRow + 1, victimCol + 1);
                    return true;
                }
            }
        }
        return false;
    }

    public int pieceCount(char type) {
        int count = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    if(isUsedByNormalPiece(type, i, j) || isUsedByQueen(type, i, j)){
                        count++;
                    }
                }
            }
        }
        return count;
    }
    public boolean isUsed(char type, int row,int col){
        if(checkersBoard[row][col]==Character.toLowerCase(type) || checkersBoard[row][col]==Character.toUpperCase(type)){
            return true;
        }
        return false;
    }
    public boolean isUsedByNormalPiece(char type, int row,int col){
        if(checkersBoard[row][col]==Character.toLowerCase(type)){
            return true;
        }
        return false;
    }
    public boolean isUsedByQueen(char type, int row,int col){
        if(checkersBoard[row][col]==Character.toUpperCase(type)){
            return true;
        }
        return false;
    }
    public int getSize(){
        return boardSize;
    }
}
