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
    int numClicks = 0;
    int startingStack, endingStack;
    boolean canMove = false;
    boolean canRoll = true;

    public GameForm(){
        roll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(canRoll) {
                    ((Board) gamePanel).rollDice1();
                    ((Board) gamePanel).rollDice2();
                    canMove = true;
                    canRoll = false;
                }
            }
        });
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(canMove) {
                    super.mouseClicked(e);
                        if ((((Board) gamePanel).getTurn() == 1 && ((Board) gamePanel).getPlayer1HitPieceStatus() && numClicks == 0) || (((Board) gamePanel).getTurn() == 2 && ((Board) gamePanel).getPlayer2HitPieceStatus() && numClicks == 0)) {
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
                Desktop desktop = null;
                if (Desktop.isDesktopSupported()){
                    desktop = Desktop.getDesktop();
                    try {
                        desktop.open(new File("C:\\Users\\johnx\\IdeaProjects\\Backgammon\\res"));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("");
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
