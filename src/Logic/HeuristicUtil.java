/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author user
 */
public class HeuristicUtil {
    private static char[][] checkersBoard;
    private static int boardSize=8;
    private static char typeR='r', typeB='b', empty='_', invalid='#';

    private static List<Chip> typeRList;
    private static List<Chip> typeBList;
    
    private static void populateList(){
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                if(isUsed(typeR, i, j)){
                    typeRList.add(new Chip(j, i));
                }
                else if(isUsed(typeB, i, j)){
                    typeBList.add(new Chip(j, i));
                }
            }
        }
    }
    public static int calcHeuristicValue(char[][] board,char type) {
        checkersBoard=board;
        populateList();
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

    public static int calcHeuristicValueEnding(char[][] board,char type) {
        checkersBoard=board;
        populateList();
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

    private static HashMap calcBoardHeuristicValue3() {
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

    private static HashMap calcBoardHeuristicValue2() {
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

    private static HashMap calcBoardHeuristicValue4() {
        HashMap<Character, Integer> map;
        map = HeuristicUtil.calcBoardHeuristicValue2();
        float heuristicValueR = map.get(typeR);
        float heuristicValueB = map.get(typeB);
        heuristicValueR = heuristicValueR * 10 / HeuristicUtil.pieceCount(typeR);
        heuristicValueB = heuristicValueB * 10 / HeuristicUtil.pieceCount(typeB);
        map.put(typeB, (int) heuristicValueB);
        map.put(typeR, (int) heuristicValueR);
        return map;
    }

    private static HashMap calcBoardHeuristicValueEnding1() {
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

    private static HashMap calcBoardHeuristicValueEnding2() {
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
public static boolean isUsedByNormalPiece(char type, int row, int col) {
        if (checkersBoard[row][col] == Character.toLowerCase(type)) {
            return true;
        }
        return false;
    }

    public static boolean isUsedByQueen(char type, int row, int col) {
        if (checkersBoard[row][col] == Character.toUpperCase(type)) {
            return true;
        }
        return false;
    }
    public static boolean isUsed(char type, int row, int col) {
        if (checkersBoard[row][col] == Character.toLowerCase(type) || checkersBoard[row][col] == Character.toUpperCase(type)) {
            return true;
        }
        return false;
    }
    public static int pieceCount(char type) {
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

}
