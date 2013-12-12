/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import View.CheckersFrame;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class GameEngine {

    private final CheckerBoard cb;
    private View.CheckersFrame checkersFrame;
    public GameEngine(){
        cb = new CheckerBoard(8);
        checkersFrame=new CheckersFrame(cb);
        /////////////////////////////testing
        while(checkersFrame.getGameState()==0){
//            System.out.println("without this line not working");
            Thread.yield();
        }
        redrawGUI();        
        
        cb.movePiece(2, 0, 3, 1);
        
        redrawGUI();
                
        cb.movePiece(5, 1, 4, 2);
        
        redrawGUI();        
        
        cb.movePiece(2, 2, 3, 3);
        
        redrawGUI();                
                        
        if (cb.cutPiece(4, 2, 3, 1)) {
            System.out.println("done");            
        } else {
            System.out.println("false");
        }
        
        redrawGUI();        
        
        cb.movePiece(1, 1, 2, 2);
        
        redrawGUI();
        
        cb.movePiece(5, 7, 4, 6);
        
        redrawGUI();
                
        cb.movePiece(0, 2, 1, 1);
        
        redrawGUI();
                
        cb.cutPiece(2, 0, 1, 1);        
        
        redrawGUI();
                
        cb.movePiece(0, 0, 1, 1);
        
        redrawGUI();
        
        cb.cutPiece(0, 2, 1, 1);       
        
        redrawGUI();
                
    }
    public void redrawGUI(){
        try {
            checkersFrame.updateFrame();            
            cb.printBoard();
            System.out.println("R"+cb.pieceCount('R')+" B"+cb.pieceCount('B'));
            System.out.println("R"+cb.calcHeuristicValue('R')+" B"+cb.calcHeuristicValue('B'));
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
