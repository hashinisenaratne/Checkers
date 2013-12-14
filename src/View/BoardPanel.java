package View;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import javax.swing.JPanel;

public final class BoardPanel extends JPanel {
    
    Image boardImg, blackchipImg, redchipImg, crownImg, welcomeImg;
    public static int gameState;// 0-welcome, 1-playing
    public static int playerColor; //0-black, 1-red
    Logic.Chip[] blackchips;
    Logic.Chip[] redchips;
    int redMax, blackMax;
    Logic.CheckerBoard checkersBoard;
    private final JButton startButton;
    JCheckBox checkBoxBlack, checkBoxRed;
    static JPanel boardPanel;
    private boolean userMoved;
    
    public BoardPanel(Logic.CheckerBoard cb) {
        
        boardPanel = this;
        gameState=0;
        playerColor=0;
        checkersBoard = cb;
        redMax = 12;
        blackMax = 12;
        
        ImageIcon welcomeIcon = new ImageIcon(this.getClass().getResource("welcome.png"));
        welcomeImg = welcomeIcon.getImage(); //background image for the welcome screen
        ImageIcon boardIcon = new ImageIcon(this.getClass().getResource("board.png"));
        boardImg = boardIcon.getImage();
        ImageIcon blackchipIcon = new ImageIcon(this.getClass().getResource("blackchip.png"));
        blackchipImg = blackchipIcon.getImage();
        ImageIcon redchipIcon = new ImageIcon(this.getClass().getResource("redchip.png"));
        redchipImg = redchipIcon.getImage();
        ImageIcon crownIcon = new ImageIcon(this.getClass().getResource("crown.png"));
        crownImg = crownIcon.getImage();
        
        checkBoxBlack = new JCheckBox("");
        checkBoxRed = new JCheckBox("");
        ButtonGroup bg = new ButtonGroup();
        bg.add(checkBoxBlack);
        bg.add(checkBoxRed);
        //setLayout(new GridBagLayout());
        startButton = new JButton(""); //button to start the game
        this.setLayout(null);
        startButton.setBounds(300, 510, 200, 60);
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoardPanel.gameState = 1;
                BoardPanel.boardPanel.repaint();
            }
        });
        this.add(startButton);
        
        checkBoxBlack.setBounds(330, 310, 100, 50);
        checkBoxRed.setBounds(330, 360, 100, 50);
        checkBoxBlack.setOpaque(true);
        checkBoxBlack.setContentAreaFilled(false);
        checkBoxRed.setOpaque(true);
        checkBoxRed.setContentAreaFilled(false);
        checkBoxBlack.setSelected(true);
        
        checkBoxBlack.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(checkBoxBlack.isSelected())
                    playerColor=0;
            }
        });
        
        checkBoxRed.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(checkBoxRed.isSelected())
                    playerColor=1;
            }
        });
        
        this.add(checkBoxBlack);
        this.add(checkBoxRed);

        //creating the chip arrays and set column, row positions for each
        blackchips = new Logic.Chip[blackMax];
        redchips = new Logic.Chip[redMax];
        for (int i = 0; i < 12; i++) {
            redchips[i] = new Logic.Chip(i % 4, i / 4);
            blackchips[i] = new Logic.Chip(i % 4, 7 - i / 4);
        }
        
        updateChipInfo();
        
        this.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseClicked (MouseEvent e) {
                if(gameState==1 && e.getY ()>=20 && e.getY ()<=580 && e.getX ()>=20 && e.getX ()<=580){                    
                    JOptionPane.showMessageDialog(
                    e.getComponent (), "Row: " + row(e.getY ()) + ", Column: " + col(e.getX ()));
                    userMoved=true;
                }
                
            }
        });
        
    }
    
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        //if in the welcome mode
        if (gameState == 0) {
            g2d.drawImage(welcomeImg, 0, 0, null); //show the welcome screen
        } else {
            startButton.setVisible(false);
            checkBoxBlack.setVisible(false);
            checkBoxRed.setVisible(false);
            g2d.drawImage(boardImg, 0, 0, null);
            updateChipInfo();
            for (int i = 0; i < redMax; i++) {
                //place red chips on the board
                g2d.drawImage(redchipImg, pixelXPos(redchips[i].getCol(), redchips[i].getRow()), pixelYPos(redchips[i].getRow()), null);
                //if the chip is a king, overlap with a crown
                if (redchips[i].isKing()) {
                    g2d.drawImage(crownImg, pixelXPos(redchips[i].getCol(), redchips[i].getRow()), pixelYPos(redchips[i].getRow()), null);
                }
                //System.out.print(redchips[i].row+","+redchips[i].col+" ");
            }
            //System.out.println("");
            for (int i = 0; i < blackMax; i++) {
                //place black chips on the board
                g2d.drawImage(blackchipImg, pixelXPos(blackchips[i].getCol(), blackchips[i].getRow()), pixelYPos(blackchips[i].getRow()), null);
                //if the chip is a king, overlap with a crown
                if (blackchips[i].isKing()) {
                    g2d.drawImage(crownImg, pixelXPos(blackchips[i].getCol(), blackchips[i].getRow()), pixelYPos(blackchips[i].getRow()), null);
                }
                //System.out.print(blackchips[i].row+","+blackchips[i].col+" ");
            }
            //System.out.println("");
        }
        
        this.paintComponents(g);
    }
    
    public int pixelXPos(int col, int row) { // (col,row) left,top
        return (row % 2 != 1) ? col * 140 + 20 : col * 140 + 90;
    }
    
    public int pixelYPos(int row) { // (col,row) left,top
        return row * 70 + 20;
    }
    
    public int col(int x){
        return ((x-20)/70);
    }
    
    public int row(int y){
        return ((y-20)/70);
    }
    
    public void updateChipInfo() {
        Logic.Chip tmpChip;
        redMax = 0;
        blackMax = 0;
        redchips = new Logic.Chip[12];
        blackchips = new Logic.Chip[12];
        
        for (int row = 0; row < checkersBoard.getSize(); row++) {
            for (int col = 0; col < checkersBoard.getSize(); col++) {
                tmpChip = new Logic.Chip(col / 2, row);
                if (checkersBoard.isUsed('R', row, col)) {
                    tmpChip.setOnBoard(true);
                    if (checkersBoard.isUsedByQueen('R', row, col)) {
                        tmpChip.setIsKing(true);
                    }
                    redchips[redMax++] = tmpChip;
                }
                if (checkersBoard.isUsed('B', row, col)) {
                    tmpChip.setOnBoard(true);
                    if (checkersBoard.isUsedByQueen('B', row, col)) {
                        tmpChip.setIsKing(true);
                    }
                    blackchips[blackMax++] = tmpChip;
                }
            }
        }
    }

    public static int getGameState() {
        return gameState;
    }

    public static char getPlayerColor() {
        if(playerColor==0)
            return 'B';
        if(playerColor==1)
            return 'R';
        return '_';
    }

    public boolean isUserMoved() {
        if(userMoved){
         userMoved=false;
         return true;
        }            
        return false;
    }    
    
}