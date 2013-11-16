import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JPanel;

public class Board extends JPanel{
    
    Image boardImg, blackchipImg, redchipImg, crownImg, welcomeImg;
    Chip[] blackchips;
    Chip[] redchips;
    static int gameState;// 0 - welcome, 1- game running, 2- finished
    private final JButton startButton;
    
    public Board() {
        
        ImageIcon welcomeIcon = new ImageIcon(this.getClass().getResource("welcome.png"));
        welcomeImg = welcomeIcon.getImage();
        
        ImageIcon boardIcon = new ImageIcon(this.getClass().getResource("board.png"));
        boardImg = boardIcon.getImage();
        
        ImageIcon blackchipIcon = new ImageIcon(this.getClass().getResource("blackchip.png"));
        blackchipImg = blackchipIcon.getImage();
        
        ImageIcon redchipIcon = new ImageIcon(this.getClass().getResource("redchip.png"));
        redchipImg = redchipIcon.getImage();
        
        ImageIcon crownIcon = new ImageIcon(this.getClass().getResource("crown.png"));
        crownImg = crownIcon.getImage();
        
        startButton = new JButton("Start");
        this.add(startButton);
        startButton.addActionListener(new StartAction());
        
        blackchips = new Chip[12];
        redchips = new Chip[12];
        for(int i = 0; i < 12; i++){
            redchips[i] = new Chip(i%4,i/4);
            blackchips[i] = new Chip(i%4,7-i/4);
        }
        
        this.revalidate();
        this.repaint();
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if(gameState == 0){
            g2d.drawImage(welcomeImg, 0, 0, null);
        }
        else{
            startButton.setVisible(false);
            
            g2d.drawImage(boardImg, 0, 0, null);
            
            updateChipInfo();
            
            for(int i = 0;i<12;i++){
                g2d.drawImage(redchipImg, pixelXPos(redchips[i].col, redchips[i].row), pixelYPos(redchips[i].row), null);
                if(redchips[i].isKing)
                    g2d.drawImage(crownImg, pixelXPos(redchips[i].col, redchips[i].row), pixelYPos(redchips[i].row), null);
                
                g2d.drawImage(blackchipImg, pixelXPos(blackchips[i].col,redchips[i].row), pixelYPos(blackchips[i].row), null);
                if(blackchips[i].isKing)
                    g2d.drawImage(crownImg, pixelXPos(blackchips[i].col,redchips[i].row), pixelYPos(blackchips[i].row), null);
            }
        }
    }
    
    public int pixelXPos (int col, int row){ // (col,row) left,top
        return (row%2==1) ? col * 140 + 20: col * 140 + 90;
    }
    
    public int pixelYPos (int row){ // (col,row) left,top
        return row * 70 + 20;
    }
    
    public void updateChipInfo(){
        
    }
    
    /*@Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        gameState=1;
    }*/
            
}

class StartAction implements ActionListener {        
  @Override
  public void actionPerformed (ActionEvent e) {     
        Board.gameState=1;
  }
}   

class Chip{
    int col, row;
    boolean onBoard, isKing;
    
    public Chip(int col, int row){
        this.col = col;
        this.row = row;
        onBoard = true;
        isKing = false;
    }
}