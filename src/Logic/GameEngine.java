/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import View.CheckersFrame;

/**
 *
 * @author user
 */
public class GameEngine {

    private CheckerBoard cb;
    private View.CheckersFrame checkersFrame;
    public GameEngine() throws InterruptedException {
        cb = new CheckerBoard(8);
        checkersFrame=new CheckersFrame(cb);
        /////////////////////////////testing
        cb.printBoard();
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        cb.movePiece(2, 0, 3, 1);
        cb.printBoard();
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        cb.movePiece(5, 1, 4, 2);
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        cb.movePiece(2, 2, 3, 3);
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
                
        cb.printBoard();
        if (cb.cutPiece(4, 2, 3, 1)) {
            System.out.println("done");
            cb.printBoard();
        } else {
            System.out.println("false");
        }
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        cb.movePiece(1, 1, 2, 2);
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        System.out.println("R"+cb.pieceCount('R')+" B"+cb.pieceCount('B'));
        cb.movePiece(5, 7, 4, 6);
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        System.out.println("R"+cb.pieceCount('R')+" B"+cb.pieceCount('B'));
        cb.movePiece(0, 2, 1, 1);
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        System.out.println("R"+cb.pieceCount('R')+" B"+cb.pieceCount('B'));
        cb.cutPiece(2, 0, 1, 1);
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        /*
        System.out.println("R"+cb.pieceCount('R')+" B"+cb.pieceCount('B'));
        cb.cutPiece(7,1,6,2);
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        cb.movePiece(1,1,2,0);
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        cb.movePiece(3,3,2,2);
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        cb.movePiece(0,0,1,1);
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        cb.cutPiece(2,2,1,1);
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        cb.movePiece(0,2,1,1);
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        cb.cutPiece(0,0,1,1);        
        
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        
        cb.printBoard();
        checkersFrame.updateFrame();
        Thread.sleep(2000);
        System.out.println("R"+cb.pieceCount('R')+" B"+cb.pieceCount('B'));
        ////////////////////////////////*/
    }
}
