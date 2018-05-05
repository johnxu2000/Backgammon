

import javafx.scene.shape.Circle;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GameForm{
    private JPanel gamePanel;
    private JPanel backgammonPanel;
    private JPanel menuPanel;
    private JButton roll;
    int numClicks = 0;
    int startingStack, endingStack;
    boolean canMove = false;
    boolean move;

    public GameForm(){
        roll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Board) gamePanel).rollDice1();
                ((Board) gamePanel).rollDice2();
                canMove = true;
            }
        });
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(canMove) {
                    super.mouseClicked(e);
                    int boundaryX = 750;
                    int boundaryX2 = 700;
                    int boundaryY = 265;
                    int boundaryY2 = 450;
                    for (int i = 0; i < 24; i++) {
                        if (e.getX() < boundaryX && e.getX() > boundaryX2) {
                            if (i < 12 && boundaryY < e.getY() && e.getY() < boundaryY2) {
                                numClicks++;
                                if (numClicks == 2) {
                                    endingStack = i;
                                    if(((Board) gamePanel).selectedStack(startingStack, endingStack)){
                                        canMove = false;
                                        ((Board) gamePanel).setOutlinedPiece();
                                    }
                                    numClicks = 0;
                                } else {
                                    startingStack = i;
                                    ((Board) gamePanel).selectedPiece(startingStack);
                                }
                                break;
                            } else if (i >= 12 && boundaryY > e.getY() && e.getY() > boundaryY2) {
                                numClicks++;
                                if (numClicks == 2) {
                                    endingStack = i;
                                    if(((Board) gamePanel).selectedStack(startingStack, endingStack)){
                                        canMove = false;
                                        ((Board) gamePanel).setOutlinedPiece();

                                    }
                                    numClicks = 0;
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
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("");
        GameForm form = new GameForm();
        frame.add(form.backgammonPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(1000, 600));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        gamePanel = new Board();


    }
}
