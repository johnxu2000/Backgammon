/*
(Mainly Ramin, what John did is stated) - The driver class that contains the UI and listeners necessary for selected stacks, hitpiece etc. to function correctly.
It is used to call functions or perform tasks when buttons or the mouse preforms an action
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
        //when the orange radiobutton is clicked, the setColour method is called to see if the colour can be changed
        orangeRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setColour(Color.orange);
            }
        });
        //when the magenta radiobutton is clicked, the setColour method is called to see if the colour can be changed
        magentaRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setColour(Color.magenta);
            }
        });
        //when the yellow radiobutton is clicked, the setColour method is called to see if the colour can be changed
        yellowRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setColour(Color.yellow);
            }
        });
        //when the black radiobutton is clicked, the setColour method is called to see if the colour can be changed
        blackRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setColour(Color.black);
            }
        });
        //when the blue radiobutton is clicked, the setColour method is called to see if the colour can be changed
        blueRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setColour(Color.blue);
            }
        });
        //when the cyan radiobutton is clicked, the setColour method is called to see if the colour can be changed
        cyanRadioButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setColour(Color.cyan);
            }
        });

        //When the skip turn button is clicked, it will see if the player can skip the turn and if so, it changes the turn of the players
        //Essentially, it restarts the turn, but whatever player clicked the skip turn button means that the other player gets to play now
        //This button is significant in the sense that players that can't move have to click it for the game to go on
         skipTurnButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(canSkipTurn) { //if the player skips their turn after rolling the dice and seeing their values
                    ((Board) gamePanel).setOutlinedPieceToFalse(); //doesn't outline a piece
                    canMove = false;
                    canRoll = true;
                    ((Board) gamePanel).changeTurn(); //turn change

                }
            }
        });


         /*
         When the new game button is called, the Board restarts and the menu panel returns to its original state
          */
        newGameButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Board)gamePanel).start(); //calls method to restart board
                ((Board) gamePanel).setOutlinedPieceToFalse();
                canRoll = true;
                canMove = false;
                numRolls = 0; //restarts number of rolls in a single game
                ((Board) gamePanel).setConditions(); //checking conditions go back to original state
                //Radiobuttons are set to be visible if players want to switch colours of pieces before game starts
                orangeRadioButton.setSelected(true);
                blackRadioButton.setSelected(true);
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

        //John did this method
        /*
        The roll button is in charge of choosing who goes first after the first roll and starting the turn of each player
        when it is clicked and the dice values are shown
         */
        roll.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(canRoll) {
                    canSkipTurn = true; //player can now skip their turn after seeing their dice values
                    ((Board) gamePanel).rollDice1(); //rolls first dice
                    ((Board) gamePanel).rollDice2(); //rolls second dice
                    numRolls++;
                    if(numRolls == 1){ //if it is the start
                        ((Board)gamePanel).chooseStartingTurn(); //chooses turn
                    }
                    else {
                        canMove = true;
                        canRoll = false;
                    }
                }
            }
        });

        /*
        The MouseListener is in charge of detecting the area that a player clicks. Depending on the coordinates, the initialSpot(Piece chosen
        to move) and finalSpot(where the piece wants to go) are sent to teh board class to validate the moves. If not, this method
        continues to run until a valid decision is made
         */
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(canMove) { //if the player has rolled, canMove is true
                    super.mouseClicked(e);
                    /*If there is a hit piece for the player, the starting stack is set to -1, so that the board class knows
                      that it is dealing with a hitPiece
                     */
                    if (((Board) gamePanel).getPlayerHitPieceStatus() && numClicks == 0) {
                        if (e.getX() < 930 && e.getX() > 860) {
                            startingStack = -1;
                            numClicks = 1;
                            ((Board) gamePanel).selectedPiece(startingStack); //outlinesPiece
                        }

                    }
                    /*
                    If the player does not have a hit piece, the following code runs
                     */
                    else {
                        //Boundaries are used to detect what stack is selected
                        int boundaryX = 750;
                        int boundaryX2 = 700;
                        int boundaryY = 265;
                        int boundaryY2 = 450;

                        //Goes in a loop for all 24 stacks, so that the boundaries of each stack are checked, to see what stack is clicked
                        for (int i = 0; i < 24; i++) {
                            if (e.getX() < boundaryX && e.getX() > boundaryX2) {
                                if ((i < 12 && boundaryY < e.getY() && e.getY() < boundaryY2) || (i >= 12 && boundaryY > e.getY() && e.getY() > boundaryY2)) {
                                    numClicks++;
                                    //If a complete move has been inputed (starting stack and ending stack are selected)
                                    if (numClicks == 2) {
                                        ((Board) gamePanel).setOutlinedPieceToFalse();
                                        endingStack = i; //ending stack is set to the latest click

                                        //if the move is valid
                                        if (((Board) gamePanel).selectedMove(startingStack, endingStack)) {

                                            //If the turn is complete (all spaces that the player is allowed to move are used
                                            if (((Board) gamePanel).getSpacesLeftToMove() == 0) {
                                                canSkipTurn = false;
                                                canMove = false; //next player cannot move pieces until rolling the dice
                                                canRoll = true; //next player has to roll
                                                ((Board) gamePanel).changeTurn(); //changes turn
                                                ((Board) gamePanel).setConditions(); //sets checking conditions back to original state
                                            }
                                            numClicks = 0; //if player has not completed turn
                                        } else {
                                            numClicks = 0; //if move was not valid
                                        }
                                    }
                                    //If it is the first valid click (starting stack)
                                    else {
                                        startingStack = i;
                                        ((Board) gamePanel).selectedPiece(startingStack);
                                    }
                                    break;
                                }
                            }

                            //The following calculations are made so, that boundary checks go from stack to stack
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

                        //If the player clicks one of the brown spots on the right, they are trying to move one of their pieces of the board
                        if(e.getX() > 750 && e.getX() < 800){
                            if(e.getY() > 50 && e.getY() < 450){
                                ((Board) gamePanel).setOutlinedPieceToFalse();

                                //If the player is able to take their piece off the board
                                if (((Board) gamePanel).canGoOffBoard(startingStack)) {
                                    //if the player wins, the game is over and nothing can be clicked or moved excpet for the new game button
                                    if(((Board) gamePanel).winner()){
                                        canMove = false;
                                    }

                                    //If the player has finished their trun
                                    if (((Board) gamePanel).getSpacesLeftToMove() == 0) {
                                        canMove = false;
                                        canRoll = true;
                                        ((Board) gamePanel).changeTurn(); //change turn
                                        ((Board) gamePanel).setConditions(); //reset conditions
                                    }

                                    numClicks = 0; //resets move enter
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
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }
        });

        //John did this method
        /*
        If the rules button is clicked, a text file opens up and the reader can read the rules of the game
         */
        rules.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop;
                if (Desktop.isDesktopSupported()){
                    desktop = Desktop.getDesktop();
                    try {
                        File file = new File(getClass().getClassLoader().getResource("BackgammonRules.txt").getFile()); //Ramin fixed it a bit
                        file.setWritable(false, false); //prevents the reader from changing the rules
                        file.setExecutable(false);
                        desktop.open(file); //physically opens the file when the button is clicked
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    /*When this method is called, the colour of the pieces is set if the game hasn't started. If the game starts
    and the players try to change the colour of their pieces, the game won't let them and the radio buttons will
    disappear
     */
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

    //John did the main method
    /*
    Driver - It sets the content to visible, sets the preferences of the the frame and creates the gameForm
     */
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

    //John did this method
    //gamePanel is used to represent the board class
    private void createUIComponents() {
        // TODO: place custom component creation code here
        gamePanel = new Board();


    }
}
