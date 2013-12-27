/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import View.CheckersFrame;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.midi.Soundbank;

/**
 *
 * @author user
 */
public class GameEngine {

    private final CheckerBoard cb;
    private View.CheckersFrame checkersFrame;
    private char manualColour;
    private int gameState;

    public GameEngine() {
        cb = new CheckerBoard(8);
        checkersFrame = new CheckersFrame(cb);
        gameState = 0;
        process();

        

//        redrawGUI();
//
//        cb.movePiece(2, 0, 3, 1);
//
//        redrawGUI();
//
//        cb.movePiece(5, 1, 4, 2);
//
//        redrawGUI();
//
//        cb.movePiece(2, 2, 3, 3);
//
//        redrawGUI();
//
//        if (cb.cutPiece(4, 2, 3, 1)) {
//            System.out.println("done");
//        } else {
//            System.out.println("false");
//        }
//
//        redrawGUI();
//
//        cb.movePiece(1, 1, 2, 2);
//
//        redrawGUI();
//
//        cb.movePiece(5, 7, 4, 6);
//
//        redrawGUI();
//
//        cb.movePiece(0, 2, 1, 1);
//
//        redrawGUI();
//
//        cb.cutPiece(2, 0, 1, 1);
//
//        redrawGUI();
//
//        cb.movePiece(0, 0, 1, 1);
//
//        redrawGUI();
//
//        cb.cutPiece(0, 2, 1, 1);
//
//        redrawGUI();
    }

    public void redrawGUI() {
        try {
            checkersFrame.updateFrame();
            cb.printBoard();
            System.out.println("R" + cb.pieceCount('R') + " B" + cb.pieceCount('B'));
            System.out.println("R" + cb.calcHeuristicValue('R') + " B" + cb.calcHeuristicValue('B'));
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void waitUntilGamestated() {
        while (checkersFrame.getGameState() < 1) {
            Thread.yield();
        }
        manualColour = checkersFrame.getPlayerColor();
        cb.reset();
        gameState = 1;
    }

    public void process() {
        waitUntilGamestated();
        while (gameState != 2) {
            if(checkersFrame.getGameState() < 1){
                break;
            }
            if(Character.toLowerCase(manualColour)!=cb.getTypeB()){ //manual color is red
                if(!cb.hasMoves(cb.getTypeB())){
                    checkersFrame.setWin(false);
                    System.out.println("Red Wins");
                    gameState=2;
                    checkersFrame.setGameState(2);
                    break;
                }
                else{
                    takeMove(cb.getTypeB());
                    redrawGUI();
                }
                if(!cb.hasMoves(cb.getTypeR())){
                    checkersFrame.setWin(true);
                    System.out.println("Black Wins");
                    gameState=2;
                    checkersFrame.setGameState(2);
                    break;
                }
                else{                    
                    checkersFrame.setManualMove();
                    waitForUserMove();
                }
            }
            
            if(Character.toLowerCase(manualColour)!=cb.getTypeR()){ //manual color is black
                if(!cb.hasMoves(cb.getTypeB())){
                    checkersFrame.setWin(false);
                    System.out.println("Red Wins");
                    gameState=2;
                    checkersFrame.setGameState(2);
                    break;
                }
                else{                    
                    checkersFrame.setManualMove();
                    waitForUserMove();
                }
                if(!cb.hasMoves(cb.getTypeR())){
                    checkersFrame.setWin(true);
                    System.out.println("Black Wins");
                    gameState=2;                    
                    checkersFrame.setGameState(2);
                    break;
                }
                else{
                    takeMove(cb.getTypeR());
                    redrawGUI();
                }
            }
        }
        redrawGUI();             
        gameState = 0;
        process();

    }

    public void takeMove(char type) {
//        int sR, sC, eR, eC;
//        do {
//            sR = (int) ((Math.random() * 10) % 8);
//            sC = (int) ((Math.random() * 10) % 8);
//            eR = sR +(int) Math.pow(-1,(int)(Math.random() * 100));
//            eC = sC+(int) Math.pow(-1,(int)(Math.random() * 100));
//            //System.out.println("nomove" + sR + "," + sC + "," + eR + "," + eC);
//            if(cb.cutPieceByType(type,sR, sC, eR, eC)){
//                break;
//            }
//        } while (!cb.movePieceByType(type,sR, sC, eR, eC));// || !cb.cutPiece(sR, sC, eR, eC));
        
        boolean isCut = false;
        if(!cb.getAllCaptures(cb.checkersBoard, type, null).isEmpty())
        {
            isCut=true;
        }
        int[] move = cb.getMoveFromMinMax(type,null);
        if(!cb.cutPieceByType(type,move[0], move[1], (move[0]+move[2])/2, (move[1]+move[3])/2))
        {
            cb.movePieceByType(type,move[0], move[1], move[2], move[3]);
        }
        
        if(checkersFrame.getPlayerColor()=='B'){
            checkersFrame.addMovesR();
        }
        else if(checkersFrame.getPlayerColor()=='R'){
            checkersFrame.addMovesB();
        }        
        Thread.yield();
        if(isCut)
        {
            //while(cb.hasCuts(cb.checkersBoard,move[2], move[3]))
            while(!cb.getAllCaptures(cb.checkersBoard, type, new Chip(move[2], move[3])).isEmpty())
            {
                move = cb.getMoveFromMinMax(type,new Chip(move[2], move[3]));
                if(!cb.cutPieceByType(type,move[0], move[1], (move[0]+move[2])/2, (move[1]+move[3])/2))
                {
                    cb.movePieceByType(type,move[0], move[1], move[2], move[3]);
                }

                if(checkersFrame.getPlayerColor()=='B'){
                    checkersFrame.addMovesR();
                }
                else if(checkersFrame.getPlayerColor()=='R'){
                    checkersFrame.addMovesB();
                }
                Thread.yield();
            }
        }
        //System.out.println("move" + sR + "," + sC + "," + eR + "," + eC);
    }
    public void waitForUserMove(){
        while(!checkersFrame.isUserMoved()){
            Thread.yield();
            if(checkersFrame.getGameState() < 1){
                break;
            }
        }
    }
}
