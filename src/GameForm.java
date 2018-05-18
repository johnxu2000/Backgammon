/*
The driver class that contains the UI and listeners necessary for selected stacks, hitpiece etc. to function correctly.
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameForm{
    private JPanel gamePanel;
    private JPanel backgammonPanel;
    private JPanel menuPanel;
    private JButton roll;
    private JButton rules;
    private JButton newGameButton;
    private JButton skipTurnButton;
    private JRadioButton orangeRadioButton;
    private JRadioButton yellowRadioButton;
    private JRadioButton magentaRadioButton;
    private JRadioButton blackRadioButton;
    private JRadioButton blueRadioButton;
    private JRadioButton cyanRadioButton;
    private JLabel player2Colours;
    private JLabel player1Colours;
    int numClicks = 0;
    int startingStack, endingStack;
    boolean canMove = false;
    boolean canRoll = true;
    int numRolls = 0;
    boolean canSkipTurn = false;

    public GameForm(){
        orangeRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setColour(Color.orange);
            }
        });
        magentaRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setColour(Color.magenta);
            }
        });
        yellowRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setColour(Color.yellow);
            }
        });
        blackRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setColour(Color.black);
            }
        });
        blueRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setColour(Color.blue);
            }
        });
        cyanRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setColour(Color.cyan);
            }
        });
         skipTurnButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(canSkipTurn) {
                    ((Board) gamePanel).setOutlinedPieceToFalse();
                    canMove = false;
                    canRoll = true;
                    ((Board) gamePanel).changeTurn(((Board) gamePanel).getTurn());
                }
            }
        });
        newGameButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Board)gamePanel).start();
                ((Board) gamePanel).setOutlinedPieceToFalse();
                canRoll = true;
                canMove = false;
                numRolls = 0;
                ((Board) gamePanel).setConditions();
                orangeRadioButton.setVisible(true);
                blueRadioButton.setVisible(true);
                magentaRadioButton.setVisible(true);
                blackRadioButton.setVisible(true);
                yellowRadioButton.setVisible(true);
                cyanRadioButton.setVisible(true);
                player1Colours.setVisible(true);
                player2Colours.setVisible(true);

            }
        });
        roll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(canRoll) {
                    canSkipTurn = true;
                    ((Board) gamePanel).rollDice1();
                    ((Board) gamePanel).rollDice2();
                    numRolls++;
                    if(numRolls == 1){
                        ((Board)gamePanel).chooseTurn();
                    }
                    else {
                        canMove = true;
                        canRoll = false;
                    }
                }
            }
        });
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(canMove) {
                    super.mouseClicked(e);
                    if (((Board) gamePanel).getPlayerHitPieceStatus() && numClicks == 0) {
                        if (e.getX() < 930 && e.getX() > 860) {
                            startingStack = -1;
                            numClicks = 1;
                            ((Board) gamePanel).selectedPiece(startingStack);
                        }
                    } else {
                        int boundaryX = 750;
                        int boundaryX2 = 700;
                        int boundaryY = 265;
                        int boundaryY2 = 450;
                        for (int i = 0; i < 24; i++) {
                            if (e.getX() < boundaryX && e.getX() > boundaryX2) {
                                if ((i < 12 && boundaryY < e.getY() && e.getY() < boundaryY2) || (i >= 12 && boundaryY > e.getY() && e.getY() > boundaryY2)) {
                                    numClicks++;
                                    if (numClicks == 2) {
                                        ((Board) gamePanel).setOutlinedPieceToFalse();
                                        endingStack = i;
                                        if (((Board) gamePanel).selectedStack(startingStack, endingStack)) {
                                            if (((Board) gamePanel).getTotalNumSpaces() == 0) {
                                                canSkipTurn = false;
                                                canMove = false;
                                                canRoll = true;
                                                ((Board) gamePanel).changeTurn(((Board) gamePanel).getTurn());
                                                ((Board) gamePanel).setConditions();
                                            }
                                            numClicks = 0;
                                        } else {
                                            numClicks = 0;
                                        }
                                    } else {
                                        startingStack = i;
                                        ((Board) gamePanel).selectedPiece(startingStack);
                                    }
                                    break;
                                }
                            }
                            if (i < 11) {
                                boundaryX -= 50;
                                boundaryX2 -= 50;
                            } else if (i == 11) {
                                boundaryY -= 30;
                                boundaryY2 -= 400;
                            } else {
                                boundaryX += 50;
                                boundaryX2 += 50;
                            }
                        }
                        if(e.getX() > 750 && e.getX() < 800){
                            if(e.getY() > 50 && e.getY() < 450){
                                ((Board) gamePanel).setOutlinedPieceToFalse();
                                if (((Board) gamePanel).canGoOffBoard(startingStack)) {
                                    if(((Board) gamePanel).winner()){
                                        canMove = false;
                                    }
                                    if (((Board) gamePanel).getTotalNumSpaces() == 0) {
                                        canMove = false;
                                        canRoll = true;
                                        ((Board) gamePanel).changeTurn(((Board) gamePanel).getTurn());
                                        ((Board) gamePanel).setConditions();
                                    }
                                    numClicks = 0;
                                    canSkipTurn = false;
                                }
                            }
                        }


                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mouseEntered(e);

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                Object source = e.getSource();
                if (source instanceof Piece) {
                    System.out.println("Mouse is over a Piece");
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });
        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop;
                if (Desktop.isDesktopSupported()){
                    desktop = Desktop.getDesktop();
                    try {
                        File file = new File(getClass().getClassLoader().getResource("BackgammonRules.txt").getFile());
                        file.setWritable(false, false);
                        file.setExecutable(false);
                        desktop.open(file);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public void setColour(Color colour){
        if(numRolls == 0) {
            ((Board) gamePanel).setColour(colour);
        }
        else{
            orangeRadioButton.setVisible(false);
            blueRadioButton.setVisible(false);
            magentaRadioButton.setVisible(false);
            blackRadioButton.setVisible(false);
            yellowRadioButton.setVisible(false);
            cyanRadioButton.setVisible(false);
            player1Colours.setVisible(false);
            player2Colours.setVisible(false);
        }
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Backgammon");
        GameForm form = new GameForm();
        frame.add(form.backgammonPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setPreferredSize(new Dimension(1000, 600));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        gamePanel = new Board();


    }
}
