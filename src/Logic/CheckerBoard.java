/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author user
 */
public class CheckerBoard {

    public char[][] checkersBoard;
    private int boardSize;
    private char typeR, typeB, empty, invalid;
    private List<Chip> typeRList;
    private List<Chip> typeBList;
    private int lastCutPieceRow,lastCutPieceCol;
    private int maxDepth = 6;

    public CheckerBoard(int size) {
        boardSize = size;
        checkersBoard = new char[boardSize][boardSize];
        typeR = 'r';
        typeB = 'b';
        empty = '_';
        invalid = '#';
        typeRList = new LinkedList<>();
        typeBList = new LinkedList<>();
        lastCutPieceRow=-1;
        lastCutPieceCol=-1;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    checkersBoard[i][j] = empty;    //EMPTY cell
                } else {
                    checkersBoard[i][j] = invalid;
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
                typeRList.add(new Chip(j, i));
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
                typeBList.add(new Chip(j, i));
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
            if (Character.toLowerCase(tmpType) == typeR) {
                Chip tmpChip = typeRList.get(typeRList.indexOf(new Chip(sCol, sRow)));
                tmpChip.setCol(dCol);
                tmpChip.setRow(dRow);
            }
            if (Character.toLowerCase(tmpType) == typeB) {
                Chip tmpChip = typeBList.get(typeBList.indexOf(new Chip(sCol, sRow)));
                tmpChip.setCol(dCol);
                tmpChip.setRow(dRow);
            }

            if (Character.toLowerCase(tmpType) == typeR && dRow == boardSize - 1) {
                checkersBoard[dRow][dCol] = Character.toUpperCase(typeR);     //uppercase typeR to represent RED QUEEN 
                typeRList.get(typeRList.indexOf(new Chip(dCol, dRow))).setIsKing(true);
            }
            if (Character.toLowerCase(tmpType) == typeB && dRow == 0) {
                checkersBoard[dRow][dCol] = Character.toUpperCase(typeB);     //uppercase typeB to represent BLACK QUEEN
                typeBList.get(typeBList.indexOf(new Chip(dCol, dRow))).setIsKing(true);
        }
            lastCutPieceRow=-1;
            lastCutPieceCol=-1;
            return true;
        }
        return false;
    }

    public boolean isMoveable(int sRow, int sCol, int dRow, int dCol) {
        if (((sRow + sCol) % 2 != 0) || (dRow + dCol) % 2 != 0) {
            return false;
        }
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
            if (Math.abs(dCol - sCol) > 2 || Math.abs(dRow - sRow) > 2) {
                //not allowing too long moves
                return false;
            }
            if (Math.abs(dCol - sCol) == 2 && Math.abs(dRow - sRow) == 2) {
                if (Character.toLowerCase(checkersBoard[sRow][sCol]) == Character.toLowerCase(typeR)) {
                    if (Character.toLowerCase(checkersBoard[(dRow + sRow) / 2][(dCol + sCol) / 2]) != Character.toLowerCase(typeB)) {
                        return false;
                    }
                }
                if (Character.toLowerCase(checkersBoard[sRow][sCol]) == Character.toLowerCase(typeB)) {
                    if (Character.toLowerCase(checkersBoard[(dRow + sRow) / 2][(dCol + sCol) / 2]) != Character.toLowerCase(typeR)) {
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    /*public boolean isACut(int sRow, int sCol, int dRow, int dCol) { //this will be always used after true in isMoveable()
        char tmpType = checkersBoard[dRow][dCol];
        Chip tmpChip;
        if (tmpType == typeR) {
            tmpChip = typeRList.get(typeRList.indexOf(new Chip(dCol, dRow)));
            if(!tmpChip.isKing()){
                if(sRow-dRow == (-2) && Math.abs(sCol-dCol)== 2){
                    return true;
                }
            }
            else{
                if(Math.abs(sRow-dRow) == 2 && Math.abs(sCol-dCol)== 2){
                    return true;
                }
            }
        }
        else if (tmpType == typeB) {
            tmpChip = typeBList.get(typeBList.indexOf(new Chip(dCol, dRow)));
            if(!tmpChip.isKing()){
                if(sRow-dRow == 2 && Math.abs(sCol-dCol)== 2){
                    return true;
                }
            }
            else{
                if(Math.abs(sRow-dRow) == 2 && Math.abs(sCol-dCol)== 2){
                    return true;
                }
            }
        }
        return false;
    }*/

    public boolean cutPiece(int attackerRow, int attackerCol, int victimRow, int victimCol) {
        char tmpAttacker, tmpVictim;
        if(attackerRow<0 || attackerCol<0 || victimRow<0 || victimCol<0){
            return false;
        }
        if(attackerRow>=boardSize || attackerCol>=boardSize || victimRow>=boardSize || victimCol>=boardSize){
            return false;
        }
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
                    movePiece(attackerRow, attackerCol, victimRow - 1, victimCol - 1);
                    lastCutPieceRow=victimRow-1;
                    lastCutPieceCol=victimCol-1;
                    checkersBoard[victimRow][victimCol] = empty;
                    if (tmpVictim == typeR) {
                        int index = typeRList.indexOf(new Chip(victimCol, victimRow));
                        typeRList.get(index).setOnBoard(false);
                        typeRList.get(index).setCol(-1);
                    }
                    else if (tmpVictim == typeB) {
                        int index = typeBList.indexOf(new Chip(victimCol, victimRow));
                        typeBList.get(index).setOnBoard(false);
                        typeBList.get(index).setCol(-1);
                    }
                    return true;
                }
                else if (isMoveable(attackerRow, attackerCol, victimRow - 1, victimCol + 1)) {
                    movePiece(attackerRow, attackerCol, victimRow - 1, victimCol + 1);
                    lastCutPieceRow=victimRow-1;
                    lastCutPieceCol=victimCol+1;
                    checkersBoard[victimRow][victimCol] = empty;
                    if (tmpVictim == typeR) {
                        int index = typeRList.indexOf(new Chip(victimCol, victimRow));
                        typeRList.get(index).setOnBoard(false);
                        typeRList.get(index).setCol(-1);
                    }
                    else if (tmpVictim == typeB) {
                        int index = typeBList.indexOf(new Chip(victimCol, victimRow));
                        typeBList.get(index).setOnBoard(false);
                        typeBList.get(index).setCol(-1);
                    }
                    return true;
                }
            }
            else if (attackerRow < victimRow) {
                if (isMoveable(attackerRow, attackerCol, victimRow + 1, victimCol - 1)) {
                    movePiece(attackerRow, attackerCol, victimRow + 1, victimCol - 1);
                    lastCutPieceRow=victimRow+1;
                    lastCutPieceCol=victimCol-1;
                    checkersBoard[victimRow][victimCol] = empty;
                    if (tmpVictim == typeR) {
                        int index = typeRList.indexOf(new Chip(victimCol, victimRow));
                        typeRList.get(index).setOnBoard(false);
                        typeRList.get(index).setCol(-1);
                    }
                    else if (tmpVictim == typeB) {
                        int index = typeBList.indexOf(new Chip(victimCol, victimRow));
                        typeBList.get(index).setOnBoard(false);
                        typeBList.get(index).setCol(-1);
                    }
                    return true;
                }
                else if (isMoveable(attackerRow, attackerCol, victimRow + 1, victimCol + 1)) {
                    movePiece(attackerRow, attackerCol, victimRow + 1, victimCol + 1);
                    lastCutPieceRow=victimRow+1;
                    lastCutPieceCol=victimCol+1;
                    checkersBoard[victimRow][victimCol] = empty;
                    if (tmpVictim == typeR) {
                        int index = typeRList.indexOf(new Chip(victimCol, victimRow));
                        typeRList.get(index).setOnBoard(false);
                        typeRList.get(index).setCol(-1);
                    }
                    else if (tmpVictim == typeB) {
                        int index = typeBList.indexOf(new Chip(victimCol, victimRow));
                        typeBList.get(index).setOnBoard(false);
                        typeBList.get(index).setCol(-1);
                    }
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
                    if (isUsed(type, i, j)) {
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public boolean isUsed(char type, int row, int col) {
        if (checkersBoard[row][col] == Character.toLowerCase(type) || checkersBoard[row][col] == Character.toUpperCase(type)) {
            return true;
        }
        return false;
    }

    public boolean isUsedByNormalPiece(char type, int row, int col) {
        if (checkersBoard[row][col] == Character.toLowerCase(type)) {
            return true;
        }
        return false;
    }

    public boolean isUsedByQueen(char type, int row, int col) {
        if (checkersBoard[row][col] == Character.toUpperCase(type)) {
            return true;
        }
        return false;
    }

    public int getSize() {
        return boardSize;
    }

    public List<Chip> getTypeRList() {
        return typeRList;
    }

    public List<Chip> getTypeBList() {
        return typeBList;
    }

    public int calcHeuristicValue(char type) {
        HashMap<Character, Integer> map = calcBoardHeuristicValue3();
        int heuristicValueR = map.get(typeR);
        int heuristicValueB = map.get(typeB);
        if (Character.toLowerCase(type) == Character.toLowerCase(typeR)) {
            return heuristicValueR - heuristicValueB;
        } else if (Character.toLowerCase(type) == Character.toLowerCase(typeB)) {
            return heuristicValueB - heuristicValueR;
        }
        return -1;
    }

    public int calcHeuristicValueEnding(char type) {
        HashMap<Character, Integer> map = calcBoardHeuristicValueEnding1();
        int heuristicValueR = map.get(typeR);
        int heuristicValueB = map.get(typeB);
        if (Character.toLowerCase(type) == Character.toLowerCase(typeR)) {
            return heuristicValueR - heuristicValueB;
        } else if (Character.toLowerCase(type) == Character.toLowerCase(typeB)) {
            return heuristicValueB - heuristicValueR;
        }
        return -1;
    }

    private HashMap calcBoardHeuristicValue3() {
        int heuristicValueR = 0;
        int heuristicValueB = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    if (isUsedByNormalPiece(typeR, i, j)) {
                        heuristicValueR += 5 + i;
                    }
                    if (isUsedByQueen(typeR, i, j)) {
                        heuristicValueR += 5 + i + 2;
                    }
                }
            }
        }
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    if (isUsed(typeB, i, j)) {
                        heuristicValueB += 5 + (7 - i);
                    }
                    if (isUsedByQueen(typeB, i, j)) {
                        heuristicValueB += 5 + (7 - i) + 2;
                    }
                }
            }
        }
        HashMap<Character, Integer> map;
        map = new HashMap<Character, Integer>();
        map.put(typeB, heuristicValueB);
        map.put(typeR, heuristicValueR);
        return map;
    }

    private HashMap calcBoardHeuristicValue2() {
        int heuristicValueR = 0;
        int heuristicValueB = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    if (isUsedByNormalPiece(typeR, i, j)) {
                        if (i < 4) {
                            heuristicValueR += 5;
                        } else {
                            heuristicValueR += 7;
                        }
                    }
                    if (isUsedByQueen(typeR, i, j)) {
                        heuristicValueR += 10;
                    }
                }
            }
        }

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    if (isUsed(typeB, i, j)) {
                        if (i > 3) {
                            heuristicValueB += 5;
                        } else {
                            heuristicValueB += 7;
                        }
                    }
                    if (isUsedByQueen(typeB, i, j)) {
                        heuristicValueB += 10;
                    }
                }
            }
        }
        HashMap<Character, Integer> map;
        map = new HashMap<Character, Integer>();
        map.put(typeB, heuristicValueB);
        map.put(typeR, heuristicValueR);
        return map;
    }

    private HashMap calcBoardHeuristicValue4() {
        HashMap<Character, Integer> map;
        map = this.calcBoardHeuristicValue2();
        float heuristicValueR = map.get(typeR);
        float heuristicValueB = map.get(typeB);
        heuristicValueR = heuristicValueR * 10 / this.pieceCount(typeR);
        heuristicValueB = heuristicValueB * 10 / this.pieceCount(typeB);
        map.put(typeB, (int) heuristicValueB);
        map.put(typeR, (int) heuristicValueR);
        return map;
    }

    private HashMap calcBoardHeuristicValueEnding1() {
        HashMap<Character, Integer> map;
        map = new HashMap<Character, Integer>();
        int heuristicValueR = 0;
        int heuristicValueB = 0;

        for (Chip tmp : typeRList) {
            int distance = 1;
            if (tmp.isOnBoard()) {
                for (int i = Math.max(0, tmp.getRow() - distance); i < Math.min(boardSize, tmp.getRow() + distance); i++) {
                    for (int j = Math.max(0, tmp.getCol() - distance); j < Math.min(boardSize, tmp.getCol() + distance); j++) {
                        if (Character.toUpperCase(checkersBoard[i][j]) == Character.toUpperCase(typeB)) {
                            heuristicValueR += distance;
                        }
                    }
                }
            }
        }
        for (Chip tmp : typeBList) {
            int distance = 1;
            if (tmp.isOnBoard()) {
                for (int i = Math.max(0, tmp.getRow() - distance); i < Math.min(boardSize, tmp.getRow() + distance); i++) {
                    for (int j = Math.max(0, tmp.getCol() - distance); j < Math.min(boardSize, tmp.getCol() + distance); j++) {
                        if (Character.toUpperCase(checkersBoard[i][j]) == Character.toUpperCase(typeR)) {
                            heuristicValueB += distance;
                        }
                    }
                }
            }
        }

        map.put(typeB, heuristicValueB);
        map.put(typeR, heuristicValueR);
        return map;
    }

    private HashMap calcBoardHeuristicValueEnding2() {
        HashMap<Character, Integer> map;
        map = new HashMap<Character, Integer>();
        int heuristicValueR = 0;
        int heuristicValueB = 0;

        for (Chip tmp : typeRList) {
            int distance = boardSize - 1;
            boolean done = false;
            if (tmp.isOnBoard()) {
                for (int i = Math.min(boardSize - 1, tmp.getRow() + distance); i >= Math.max(0, tmp.getRow() - distance); i--) {
                    for (int j = Math.min(boardSize - 1, tmp.getCol() + distance); j >= Math.max(0, tmp.getCol() - distance); j--) {
                        if (Character.toUpperCase(checkersBoard[i][j]) == Character.toUpperCase(typeB)) {
                            heuristicValueR = distance;
                            done = true;
                            break;
                        }
                    }
                    if (done) {
                        break;
                    }
                }
            }
        }
        for (Chip tmp : typeBList) {
            int distance = boardSize - 1;
            boolean done = false;
            if (tmp.isOnBoard()) {
                for (int i = Math.min(boardSize - 1, tmp.getRow() + distance); i >= Math.max(0, tmp.getRow() - distance); i--) {
                    for (int j = Math.min(boardSize - 1, tmp.getCol() + distance); j >= Math.max(0, tmp.getCol() - distance); j--) {
                        if (Character.toUpperCase(checkersBoard[i][j]) == Character.toUpperCase(typeR)) {
                            heuristicValueB = distance;
                            done = true;
                            break;
                        }
                    }
                    if (done) {
                        break;
                    }
                }
            }
        }

        map.put(typeB, heuristicValueB);
        map.put(typeR, heuristicValueR);
        return map;
    }

    public char getTypeR() {
        return typeR;
    }

    public char getTypeB() {
        return typeB;
    }

    public boolean isMoveableByType(char type, int sRow, int sCol, int dRow, int dCol) {
        if (Character.toLowerCase(checkersBoard[sRow][sCol]) == Character.toLowerCase(type)) {
            return isMoveable(sRow, sCol, dRow, dCol);
        }
        return false;
    }

    public boolean movePieceByType(char type, int sRow, int sCol, int dRow, int dCol) {
        if (isMoveableByType(type, sRow, sCol, dRow, dCol)) {
            return movePiece(sRow, sCol, dRow, dCol);
        }
        return false;
    }

    public boolean hasMoves(int row, int col) {
        if (isMoveable(row, col, row + 1, col + 1)) {
            return true;
        }
        if (isMoveable(row, col, row - 1, col + 1)) {
            return true;
        }
        if (isMoveable(row, col, row - 1, col - 1)) {
            return true;
        }
        if (isMoveable(row, col, row + 1, col - 1)) {
            return true;
        }
        if (isMoveable(row, col, row + 2, col + 2)) {
            return true;
        }
        if (isMoveable(row, col, row - 2, col + 2)) {
            return true;
        }
        if (isMoveable(row, col, row - 2, col - 2)) {
            return true;
        }
        if (isMoveable(row, col, row + 2, col - 2)) {
            return true;
        }
        return false;
    }

    public boolean hasMoves(char type) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    if (Character.toLowerCase(checkersBoard[i][j]) == Character.toLowerCase(type)) {
                        if (hasMoves(i, j)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean move(int sRow, int sCol, int dRow, int dCol) {
        if (cutPiece(sRow, sCol, (dRow + sRow) / 2, (dCol + sCol) / 2)) {
            return true;
        }
        if (movePiece(sRow, sCol, dRow, dCol)) {
            return true;
        }
        return false;
    }

    public boolean hasCuts(int row, int col) {
        if (isMoveable(row, col, row + 2, col + 2)) {
            return true;
        }
        if (isMoveable(row, col, row - 2, col + 2)) {
            return true;
        }
        if (isMoveable(row, col, row - 2, col - 2)) {
            return true;
        }
        if (isMoveable(row, col, row + 2, col - 2)) {
            return true;
        }
        return false;
    }
    public boolean hasCuts(char[][] board, int row, int col) {
        if (canMove(board, row, col, row + 2, col + 2)) {
            return true;
        }
        if (canMove(board,row, col, row - 2, col + 2)) {
            return true;
        }
        if (canMove(board,row, col, row - 2, col - 2)) {
            return true;
        }
        if (canMove(board,row, col, row + 2, col - 2)) {
            return true;
        }
        return false;
    }

    public boolean hasMoreCuts(char type) {
        if(lastCutPieceRow!=-1){
            return hasCuts(lastCutPieceRow, lastCutPieceCol);
        }
        return false;
    }
    
    public boolean cutPieceByType(char type,int attackerRow, int attackerCol, int victimRow, int victimCol){
        if(Character.toLowerCase(checkersBoard[attackerRow][attackerCol])== Character.toLowerCase(type)){
            return cutPiece(attackerRow, attackerCol, victimRow, victimCol);
        }
        return false;
    }
    
    
    char RKing = 'R';
    char BKing = 'B';
    
    //the board should consist of 'r' and 'b' for normal and 'R' and 'B' for kings
    boolean canMove(char[][] board, int sRow, int sCol, int dRow, int dCol )
{
	if(sRow == dRow || sCol == dCol || dRow<0 ||dRow>7 ||dCol <0 || dCol >7 || Math.abs(sRow - dRow) >2 || Math.abs(sCol - dCol) >2 )
	{
		return false;
	}

	if(Math.abs(sRow - dRow) ==1 && Math.abs(sCol - dCol) == 1)
	{
		if(board[dRow][dCol] == empty)
		{
                    if(board[sRow][sCol] == RKing || board[sRow][sCol] == BKing)
                    {
                        return true; // a non capture
                    }
                    if(board[sRow][sCol] == typeB && sRow > dRow)
                    {
                        return true; // a non capture
                    }
                    if(board[sRow][sCol] == typeR && sRow < dRow )
                    {
                        return true; // a non capture
                    }
		}
	}

	if(Math.abs(sRow - dRow) ==2 && Math.abs(sCol - dCol) == 2)
        {
                if(board[dRow][dCol] == empty && board[(sRow + dRow)/2][(sCol + dCol)/2] != empty && Character.toLowerCase(board[sRow ][sCol]) != Character.toLowerCase(board[(sRow + dRow)/2][(sCol + dCol)/2]))
                {
                    if(board[sRow][sCol] == RKing || board[sRow][sCol] == BKing)
                    {
                        return true; // a capture
                    }
                    if(board[sRow][sCol] == typeB && sRow > dRow)
                    {
                        return true; // a capture
                    }
                    if(board[sRow][sCol] == typeR && sRow < dRow )
                    {
                        return true; // a capture
                    }
                        
                }
	}
	return false;
}
    public List<int[]> getAllCaptures(char[][] board, char colour, Chip predecessorOfMultiMove)
    {
        ArrayList<int[]> ret = new ArrayList<int[]>();
        if(predecessorOfMultiMove == null)
        {
            for( int row=0; row<8; row++)
            {
                for (int col=0; col<8; col++)
                {
                    if(Character.toLowerCase(board[row][col])==Character.toLowerCase(colour))
                    {
                        if (canMove(board, row, col, row + 2, col + 2)) {
                            ret.add(new int[]{row, col, row + 2, col + 2});
                        }
                        if (canMove(board, row, col, row - 2, col + 2)) {
                            ret.add(new int[]{row, col, row - 2, col + 2});
                        }
                        if (canMove(board, row, col, row - 2, col - 2)) {
                            ret.add(new int[]{row, col, row - 2, col - 2});
                        }
                        if (canMove(board, row, col, row + 2, col - 2)) {
                            ret.add(new int[]{row, col, row + 2, col - 2});
                        }
                    }
                }
            }
        }
        else
        {
            int row = predecessorOfMultiMove.getRow(), col =predecessorOfMultiMove.getCol();
            if(Character.toLowerCase(board[row][col])==Character.toLowerCase(colour))
            {
                if (canMove(board, row, col, row + 2, col + 2)) {
                    ret.add(new int[]{row, col, row + 2, col + 2});
                }
                if (canMove(board, row, col, row - 2, col + 2)) {
                    ret.add(new int[]{row, col, row - 2, col + 2});
                }
                if (canMove(board, row, col, row - 2, col - 2)) {
                    ret.add(new int[]{row, col, row - 2, col - 2});
                }
                if (canMove(board, row, col, row + 2, col - 2)) {
                    ret.add(new int[]{row, col, row + 2, col - 2});
                }
            }
        }
        return ret;
    }
    
    List<int[]> getAllNonCaptures(char[][] board, char colour)
    {
        ArrayList<int[]> ret = new ArrayList<int[]>();
        for( int row=0; row<8; row++)
        {
            for (int col=0; col<8; col++)
            {
                if(Character.toLowerCase(board[row][col])==Character.toLowerCase(colour))
                {
                    if (canMove(board, row, col, row + 1, col + 1)) {
                        ret.add(new int[]{row, col, row + 1, col + 1});
                    }
                    if (canMove(board, row, col, row - 1, col + 1)) {
                        ret.add(new int[]{row, col, row - 1, col + 1});
                    }
                    if (canMove(board, row, col, row - 1, col - 1)) {
                        ret.add(new int[]{row, col, row - 1, col - 1});
                    }
                    if (canMove(board, row, col, row + 1, col - 1)) {
                        ret.add(new int[]{row, col, row + 1, col - 1});
                    }
                }
            }
        }
        return ret;
    }
    
public static char[][] cloneArray(char[][] src) {
    int length = src.length;
    char[][] target = new char[length][src[0].length];
    for (int i = 0; i < length; i++) {
        System.arraycopy(src[i], 0, target[i], 0, src[i].length);
    }
    return target;
}
    int[] getMoveFromMinMax (char colour, Chip predecessorOfMultiMove)//assumed that there is at least one possible move
{
    /*char[][] currentBoard = new char[boardSize][boardSize];
    for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if ((i + j) % 2 == 0) {
                    currentBoard[i][j] = empty;    //EMPTY cell
                } else {
                    currentBoard[i][j] = invalid;
                }
            }
        }
    
    for(Chip piece : typeBList)
    {
        if(piece.isOnBoard())
        {
            if(piece.isKing())
            {
                currentBoard[piece.getRow()][piece.getCol()] = 'B';
            }
            else
            {
                currentBoard[piece.getRow()][piece.getCol()] = 'b';
            }
        }
    }
    
    for(Chip piece : typeRList)
    {
        if(piece.isOnBoard())
        {
            if(piece.isKing())
            {
                currentBoard[piece.getRow()][piece.getCol()] = 'R';
            }
            else
            {
                currentBoard[piece.getRow()][piece.getCol()] = 'r';
            }
        }
    }*/
    char[][] currentBoard = checkersBoard;
    for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                System.out.print(currentBoard[i][j]);
            }
            System.out.println();
        }
    System.out.println();
    
    List<int[]> possibleMoves = getAllCaptures(currentBoard, colour,predecessorOfMultiMove);
    int tmpMax;
    int[] bestMove = null;
	if(!possibleMoves.isEmpty())// check if there are possible captures
	{
		tmpMax = Integer.MIN_VALUE;

		for(int[] move : possibleMoves)
		{
			Node node = new Node(1,cloneArray(currentBoard));
			node.makeMove(move);
                        if(hasCuts(node.board, move[2], move[3]))
                        {
//                            List<int[]> multiMoves = getAllCaptures(node.board, colour, new Chip(move[2], move[3]));
//                            for(int[] multiMove : multiMoves)
//                            {
//                                node = new Node(1,cloneArray(currentBoard));
//                                node.makeMove(multiMove);
//                                int value = findValue(node, colour, true);
//                                if( value > tmpMax)
//                                {
//                                        tmpMax= value;
//                                        bestMove = move;
//                                } 
//                            }
//                            continue;
                            return move;
                        }
                        int value = findValue(node, colour, true);
			if( value > tmpMax)
			{
				tmpMax= value;
				bestMove = move;
			}
		}
		return bestMove;
	}

	possibleMoves = getAllNonCaptures(currentBoard, colour);
	tmpMax = Integer.MIN_VALUE;

	for(int[] move : possibleMoves)
	{
		Node node = new Node(1,cloneArray(currentBoard));
                node.makeMove(move);
                int value = findValue(node, colour, true);
                if( value > tmpMax)
                {
                        tmpMax= value;
                        bestMove = move;
                }
	}
	return bestMove;

}
    
char invertColour(char colour)
{
    return Character.toLowerCase(colour)=='r'?'b':'r';
}

int findValue(Node node, char colour, boolean isMin)
{
	if(node.depth == maxDepth)
	{
		return calcHeuristic(node.board, colour);
	}
	
        List<int[]> possibleMoves = getAllCaptures(node.board, (node.depth%2==1)?invertColour(colour):colour, null);
        int tmp;
	if(!possibleMoves.isEmpty() && !isMin)// check if there are possible captures
	{
		tmp = Integer.MIN_VALUE;

		for(int[] move : possibleMoves)
		{
			Node newNode = new Node(node.depth+1,cloneArray(node.board));
			newNode.makeMove(move);
                        if(hasCuts(newNode.board, move[2], move[3]))
                        {
                            return 1000;
                        }
                        int value = findValue(newNode, colour, true);
			if( value > tmp)
			{
				tmp= value;
			}
		}
		return tmp;
	}
        
	if(!possibleMoves.isEmpty() && isMin)// check if there are possible captures
	{
		tmp = Integer.MAX_VALUE;

		for(int[] move : possibleMoves)
		{
			Node newNode = new Node(node.depth+1,cloneArray(node.board));
			newNode.makeMove(move);
                        if(hasCuts(newNode.board, move[2], move[3]))
                        {
                            return -1000;
                        }
                        int value = findValue(newNode, colour, false);
			if( value < tmp)
			{
				tmp= value;
			}
		}
		return tmp;
	}
        
        possibleMoves = getAllNonCaptures(node.board, (node.depth%2==1)?invertColour(colour):colour);
        
	if(!possibleMoves.isEmpty() && !isMin)// check if there are possible non captures
	{
		tmp = Integer.MIN_VALUE;

		for(int[] move : possibleMoves)
		{
			Node newNode = new Node(node.depth+1,cloneArray(node.board));
			newNode.makeMove(move);
                        int value = findValue(newNode, colour, true);
			if( value > tmp)
			{
				tmp= value;
			}
		}
		return tmp;
	}
        
	if(!possibleMoves.isEmpty() && isMin)// check if there are possible non captures
	{
		tmp = Integer.MAX_VALUE;

		for(int[] move : possibleMoves)
		{
			Node newNode = new Node(node.depth+1,cloneArray(node.board));
			newNode.makeMove(move);
                        int value = findValue(newNode, colour, false);
			if( value < tmp)
			{
				tmp= value;
			}
		}
		return tmp;
	}
	return calcHeuristic(node.board, colour);// if the execution reaches this line. it means that the node is a leaf.
}


int calcHeuristic(char[][] board, char colour)
{
    //return new Random().nextInt(100);
    //return HeuristicUtil.calcHeuristicValue(board, colour);
    return eval(board, colour);
}

final static int normal = 100;         //one checker worth 100
final static int king=200;             //a King's worth
final static int pos=1;                //one position along the -j worth 1
final static int edge=10;               // effect of king being on the edge


public int eval(char [][] board, char colour){
        int score=0;

        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
	            if (board[i][j] == typeR)
                {
                      score-=normal;
                      score-=pos*i*i;
                }

                else if (board[i][j] ==RKing){
                    score-=king;
                    if (i==0 || i==7)
                        score += edge;
                    if (j==0 || j==7)
                        score += edge;
                }

                else if (board[i][j] == typeB)
                {
                      score+=normal;
                      score+=pos*(7-i)*(7-i);
                }

                else if (board[i][j] == BKing){
                    score+=king;
                    if (i==0 || i==7)
                        score -= edge;
                    if (j==0 || j==7)
                        score -= edge;
                }
            }
        }
        //score += (int)(Math.random() * 10);                    //Adds a random weight

        if(Character.toLowerCase(colour)== typeR)
        {
            score*=-1;
        }
        return score;
    }
}


class Node
{
    int depth;
    char[][] board;
    
    public Node(int dep, char[][] bord)
    {
        depth = dep;
        board =bord;
        
    }
    
    void makeMove(int[] move)//move is assumed to be valid
    {
        char tmp = board[move[0]][move[1]];
        board[move[0]][move[1]] = '_';
        board[move[2]][move[3]] = tmp;
        if(Math.abs(move[0]-move[2]) == 2)
        {
            board[(move[0]+move[2])/2][(move[1]+move[3])/2] = '_';
        }
    }
}
