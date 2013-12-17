package View;

import View.BoardPanel;
import javax.swing.JFrame;

public class CheckersFrame extends JFrame {
    BoardPanel panel;
    public CheckersFrame(Logic.CheckerBoard cb) {
        panel=new BoardPanel(cb);
        add(panel);
        setTitle("Checkers");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(800, 600);//806,629
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    public void updateFrame(){
        panel.repaint();
    }
    public int getGameState(){
        return BoardPanel.getGameState();
    }
    
    public void setGameState(int state){
        panel.setGameState(state);
    }
    
    public char getPlayerColor(){
        return BoardPanel.getPlayerColor();
    }

    public boolean isUserMoved() {
        return panel.isMoved();
    }
    public void setManualMove(){
        panel.setManualMove();
    }
    public void addMovesB(){
        panel.addMovesB();
    }
    
    public void addMovesR(){
        panel.addMovesR();
    }
    
    public void setWin(boolean bwin){
        panel.setWin(bwin);
    }
}