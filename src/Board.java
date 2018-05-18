import javax.swing.*;
import java.awt.*;

/*
The Board class draws the dices and pieces at the same time also contains essential algorithms for the game to run. It takes care
of the validation of moves and decisions. However, it works hand in hand with the player class to make sure that the
 */
public class Board extends JPanel {
    int x1, x2, x3, y1, y2, y3;
    Die dice1 = new Die(340, 540);
    Die dice2 = new Die(410, 540);
    Player player1, player2;
    Piece removedPiece, addPiece; //Pieces can be added individually
    Stacks stacks; //stores the stacks representing spots on the board
    int startingY, startingX;
    int xOutlinedPiece, yOutlinedPiece;
    boolean outlinePiece = false;
    int turn = 1;
    int spacesLeftToMove = 0; //total number of spaces the player can move
    int numSpacesMoving; //spaces moving in one turn

    //checking conditions
    boolean condition1 = true;
    boolean condition2 = true;
    boolean condition3 = true;
    boolean playerHasHitPiece = false;
    boolean turnChosen = false; //for start of the game, turn is chosen
    boolean turnChange = false;
    String playerTurn; //string representing which player's turn it is
    String playerGoingFirst;
    int numDoubles = 0;
    public Board(){
        start(); //starts the game
    }

    public void paintComponent(Graphics g){
        setBackground(Color.black);
        g.setColor(Color.GRAY);
        super.paintComponent(g);

        /*
        The following if statements and for loops is to draw the pieces according to the stacks that they are in
         */
        startingX = 710; //starts at the first stack
        for(int i = 0; i < 24; i++){
            if(i > 11) {
                startingY = 20; //base of stack starts here
            }
            if(i <= 11) {
                startingY = 445; //base of stack starts here
            }
            //this loops stacks pieces on top of each other by incrementing the startingY of each piece
            for(int z = 0; z < stacks.getStacks()[i].size(); z++){
                Piece p = (Piece) stacks.getStacks()[i].get(z);
                p.setX(startingX);
                if(i > 11) {
                    p.setY(startingY += 30);
                }
                else {
                    p.setY(startingY -= 30);
                }

            }
            //Moves from stack to stack
            if(i > 11) {
                startingX += 50;
            }
            if(i < 11) {
                startingX -= 50;
            }
        }

        g.fillRect(100, 50, 700, 400); //gray background of the board
        g.setColor(Color.white);

        //The following code is to draw the score of each player in the panel
        g.drawString("Scoreboard: ", 650, 500);
        g.drawString("Player 1: "+ Integer.toString(player1.getScore()), 650, 530);
        g.drawString("Player 2: "+ Integer.toString(player2.getScore()), 650, 560);

        if(turnChange){
            g.drawString(playerTurn, 40, 570);
        }

        if(turnChosen){ //if the first roll is made a player is chosen to go first
            g.drawString(playerGoingFirst, 30, 570); //displays what player goes first
            turnChosen = false;
        }
        g.fillRect(820, 20, 150, 550); //draws the area holding the hit pieces

        //The following code draws all the triangles that represent spots that a player can move
        x1 = 750;
        x2 = 700;
        x3 = 725;
        y1 = 450;
        y2 = 450;
        y3 = 270;
        for(int i = 0; i < 2; i++){
            for(int z = 0; z < 12; z++){
                if((z%2 == 0 && i%2 == 0) || (i%2 != 0 && z%2 != 0)){
                    g.setColor(Color.red);
                }
                else{
                    g.setColor(Color.white);
                }
                //Coordinates are used for the representation of a triangle figure
                int triangleXPoints[] = {x1, x2, x3};
                int triangleYPoints[] = {y1, y2, y3};
                g.fillPolygon(triangleXPoints, triangleYPoints, 3);
                x1 -= 50;
                x2 -= 50;
                x3 -= 50;
            }
            x1 += 600;
            x2 += 600;
            x3 += 600;
            y1 -= 400;
            y2 -= 400;
            y3 -= 40;
        }

        //draws the end zones
        g.setColor(new Color(139,69,19));
        g.fillRect(100, 50, 50, 400);
        g.fillRect(750, 50, 50, 400);
        g.setColor(Color.gray);
        g.fillRect(100, 240, 50, 20);
        g.fillRect(750, 240, 50, 20);

        //draws the dice
        dice1.draw(g);
        dice2.draw(g);


        g.setColor(Color.black);
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.drawString("Hit Pieces:", 850, 50);
        g.drawLine(450, 20, 450, 450);

        int hitPiecesY = 60;
        int hitPiecesX = 880;
        //sets the coordinates for the hit pieces of player 1
        for(int i = 0; i < player1.hitPieces.size(); i++){
            player1.hitPieces.get(i).setX(hitPiecesX);
            player1.hitPieces.get(i).setY(hitPiecesY);
            hitPiecesY += 50;
        }
        //sets the coordinates for the hit pieces of player 2
        for(int i = 0; i < player2.hitPieces.size(); i++){
            player2.hitPieces.get(i).setX(hitPiecesX);
            player2.hitPieces.get(i).setY(hitPiecesY);
            hitPiecesY += 50;
        }
        //draws all the pieces of player 1 and 2
        for(int i = 0; i < player1.getPieces().length; i++){ //player 1 and 2 have the same number of pieces
            player1.getPieces()[i].draw(g);
            player2.getPieces()[i].draw(g);
        }
        g.setColor(Color.green);
        if(outlinePiece){ //if a piece is selected a green outline appears around the selected piece
            g.drawOval(xOutlinedPiece, yOutlinedPiece, 30, 30);
        }
        //shows the number of pieces off the board for each player
        g.drawString(Integer.toString(player2.getNumPiecesOffBoard()), 770, 100);
        g.drawString(Integer.toString(player1.getNumPiecesOffBoard()), 770, 400);
        if(winner()){ // if player wins
            g.setFont(new Font("Serif", Font.BOLD, 150));
            g.setColor(Color.BLUE);
            g.drawString("Game Over", 150, 300);
        }
    }

    //This method rolls the first dice
    public void rollDice1(){
        dice1.roll();
        repaint();
    }

    //Since the second die is rolled last each time, it rolls teh second die, plus calculates the number of spaces that player can move
    public void rollDice2(){
        dice2.roll();
        numDoubles = 0;
        if(dice1.getFaceValue() == dice2.getFaceValue()){ //if the player rolls a double they get twice as many moves
            spacesLeftToMove = 2 * (dice1.getFaceValue() + dice2.getFaceValue());
        }
        else {
            spacesLeftToMove = dice1.getFaceValue() + dice2.getFaceValue();
        }
        repaint();
    }

    //This method checks if there is a winner in the game
    public boolean winner() {
        boolean winner = false;
        if(getPlayer().checkWin()){ //calls method in player class
            getPlayer().addPoint(); //adds point to score
            winner = true;
        }
        return winner;
    }

    /*
    This method is responsible for returning whether the move is possible (using helper methods) and it essentially adds pieces to
    the appropriate stacks if the move is possible. Helper methods are used for the validation of the move and depending on whether a
    hit piece is being used, adjustments are made towards the stack that the hitPieces are in
     */
    public boolean selectedMoveIsValid(int initialSpot, int finalSpot){
        boolean moveStatus = false;
        boolean usingHitPiece = false; //represents if the piece that it is moving was hit
        Piece hitPiece;
        //The following code is to set the initial spot of a hit piece as an integer for validation reasons
        if(initialSpot == -1){
            if(getTurn() == 1){
                initialSpot = 24;
            }
            usingHitPiece = true;
        }
        if(checkValididtyOfMove(initialSpot, finalSpot)) {//Runs after selected piece. This decides if the piece could move to a specific spot the user pointed at.
            if(checkDiceUsage()) { //Checks if the player has not already used the die value
                moveStatus = true;
                //If a regular piece is hitting an opponent's piece the following code runs
                if (!usingHitPiece && stacks.getStacks()[finalSpot].size() == 1 && ((Piece) stacks.getStacks()[initialSpot].peek()).getColour() != ((Piece) stacks.getStacks()[finalSpot].peek()).getColour()) {
                    hitPiece = (Piece) stacks.getStacks()[finalSpot].pop();
                    //Changes turn to add piece to a stack of hit pieces of the opponent
                    changeTurn(getTurn());
                    getPlayer().addHitPiece(hitPiece);
                    changeTurn(getTurn());
                }
                //If a regular piece is being used, the piece is removed and added to the stacks of the board itself
                if(!usingHitPiece) {
                    removedPiece = (Piece) stacks.getStacks()[initialSpot].pop();
                    addPiece = (Piece) stacks.getStacks()[finalSpot].push(removedPiece);
                }
                //If a hit piece is being used
                if(usingHitPiece) {
                    //if the hit piece is returning in the game by hitting another piece
                    if (stacks.getStacks()[finalSpot].size() == 1 && getPlayer().hitPieces.peek().getColour() != ((Piece) stacks.getStacks()[finalSpot].peek()).getColour()) {
                        hitPiece = (Piece) stacks.getStacks()[finalSpot].pop();
                        //Changes turn to add piece to a stack of hit pieces of the opponent
                        changeTurn(getTurn());
                        getPlayer().addHitPiece(hitPiece);
                        changeTurn(getTurn());
                    }
                    removedPiece = getPlayer().getHitPieces().pop();
                    addPiece = (Piece) stacks.getStacks()[finalSpot].push(removedPiece);
                }
            }
        }
        return moveStatus;
    }

    //This method returns the dice with the lowered face value (for checking purpose)
    public Die lowerRolledDie(){
        if(dice1.getFaceValue() > dice2.getFaceValue()){
            return dice2;
        }
        else{
            return dice1;
        }
    }

    /*The method gets called when a player wants to take their piece off the board. It goes through checks to see
    if the player can take their piece off the board and if they can, the action is done physically
     */
    public boolean canGoOffBoard(int initialSpot){
        boolean moveIsPossible = false;
        if(getTurn() == 1) {
            int finalSpot = -1; //for checking purpose
            numSpacesMoving = initialSpot - finalSpot;
        }
        if(getTurn() == 2) {
            int finalSpot = 24; //for checking purpose
            numSpacesMoving = finalSpot - initialSpot;
        }
        if(getPlayer().piecesAreHome()){ //if all pieces are at the last 6 spots for the player
            //checks to see if the die value has not been used yet, or if the player's pieces are close enough the player can remove a piece if they don't have a piece in the spot equal to the die value
            if(checkDiceUsage() || getPlayer().piecesAreClose(lowerRolledDie().getFaceValue())){
                removedPiece = (Piece) stacks.getStacks()[initialSpot].pop();
                getPlayer().offBoardPiece(removedPiece); //player keeps track of pieces off board
                moveIsPossible = true;
                repaint();
            }
        }
        return moveIsPossible;
    }

    /*
    The following method takes the peek of the stack selected and sets the appropriate variables, so that the piece at the top
    of the stack is outlined. Depending on if it is a hit piece or not a hit piece, the method uses the stack selected (hit pieces
    have their own stack)
     */
    public void selectedPiece(int initialSpot){
        Piece topOfHits;
        if(initialSpot == -1){
            topOfHits = getPlayer().getHitPieces().get(0);
            xOutlinedPiece = topOfHits.getX();
            yOutlinedPiece = topOfHits.getY();
            outlinePiece = true;
            repaint();
        }
        else {
            if (!stacks.getStacks()[initialSpot].isEmpty()) {
                Piece topOfStack = (Piece) stacks.getStacks()[initialSpot].peek();
                if (topOfStack == getPlayer().movePieceCheck(topOfStack)) {
                        xOutlinedPiece = topOfStack.getX();
                        yOutlinedPiece = topOfStack.getY();
                        outlinePiece = true;
                        repaint();

                }
            }
        }
    }

    /*
    The following method is used to set-up the game, when a player wants to start a new game. It puts the pieces in the right
    stacks, so that when they are drawn it will look like the game has restarted
     */
    public void start(){
        player1 = new Player(Color.ORANGE);
        player2 = new Player(Color.black);
        stacks = new Stacks();

        //The loop empties the stacks
        for(int i = 0; i < stacks.getStacks().length; i++){
            for(int z = 0; z < stacks.getStacks()[i].size(); z++){
                stacks.getStacks()[i].remove(z);
            }
        }

        //The following four loops are to put both player1 and player2's pieces in the right stacks
        for(int i = 0; i < 2; i++) {
            stacks.getStacks()[23].push(player1.getPieces()[i]);
            stacks.getStacks()[0].push(player2.getPieces()[i]);
        }
        for(int i = 2; i < 7; i++) {
            stacks.getStacks()[11].push(player2.getPieces()[i]);
            stacks.getStacks()[12].push(player1.getPieces()[i]);
        }
        for(int i = 7; i < 10; i++) {
            stacks.getStacks()[16].push(player2.getPieces()[i]);
            stacks.getStacks()[7].push(player1.getPieces()[i]);
        }
        for(int i = 10; i < 15; i++) {
            stacks.getStacks()[5].push(player1.getPieces()[i]);
            stacks.getStacks()[18].push(player2.getPieces()[i]);
        }
        repaint(); //to draw the board with the pieces on it
    }

    /*
    The following method is in charge of checking whether a move is valid, by checking a series of if statements. By using the getPlayer() method,
    this code has become efficient in teh sense that the if statements no longer have to be duplicated for two turns and are instead checked through
    once. The method also checks to see if a hit piece can move
     */
    public boolean checkValididtyOfMove(int initialSpot, int finalSpot){ //If the stack meets the requirement for the piece to move there.
        boolean move = false;
        if(getTurn() == 1){
            numSpacesMoving = initialSpot - finalSpot;
        }
        if(getTurn() == 2){
            numSpacesMoving = finalSpot - initialSpot; //opposite direction compared to player 1
        }
        //If the number of spaces that the player wants to move fit the dice values
        if (getNumSpacesMoving() == dice1.getFaceValue() || getNumSpacesMoving() == dice2.getFaceValue() || getNumSpacesMoving() == (dice2.getFaceValue() + dice1.getFaceValue())) {
            //If it is dealing with a hit piece or the stack is not empty and less than 5 pieces are on it
            if (getPlayer().getHitPieces().size() != 0 || (!stacks.getStacks()[initialSpot].isEmpty() && stacks.getStacks()[finalSpot].size() < 5)) {
                //if it is dealing with a hit piece
                if (getPlayer().getHitPieces().size() != 0 || stacks.getStacks()[initialSpot].peek() == getPlayer().movePieceCheck((Piece) stacks.getStacks()[initialSpot].peek())) {
                    if ((stacks.getStacks()[finalSpot].size() != 0 && ((Piece) stacks.getStacks()[finalSpot].peek()).getColour() == getPlayer().getPieces()[0].getColour()) || stacks.getStacks()[finalSpot].size() == 0)  {
                            move = true;
                    }
                    else if(stacks.getStacks()[finalSpot].size() == 1 && ((Piece) stacks.getStacks()[finalSpot].peek()).getColour() != getPlayer().getPieces()[0].getColour()){
                            move = true;
                    }
                }
            }
        }
        return move;
    }

    //Returns the number of spaces in one move that the player wants to move a piece
    public int getNumSpacesMoving(){
        return numSpacesMoving;
    }

    /*
    The following method is in charge of keeping track of what dies are used and to set conditions to false if the die is already used. It
    also does this for doubles, but instead of being used once, die values can be used twice. If the die value can be used, the total sum of spaces
    that the player can move is subtracted by the move that is currently taking place
     */
    public boolean checkDiceUsage(){

        //If die one has not been used or has not been used twice (for doubles)
        if(getNumSpacesMoving() == dice1.getFaceValue() && condition1 && condition3){
            spacesLeftToMove -= getNumSpacesMoving();
            if(numDoubles < 5 && dice1.getFaceValue() == dice2.getFaceValue()){
                numDoubles++;
            }
            else {
                condition1 = false; //dice 1 face value can no longer be used
            }
            return true;
        }
        //If die two has not been used or has not been used twice (for doubles)
        else if(getNumSpacesMoving() == dice2.getFaceValue() && condition2 && condition3){
            spacesLeftToMove -= getNumSpacesMoving();
            if(numDoubles < 5 && dice1.getFaceValue() == dice2.getFaceValue()){ //for doubles
                numDoubles++;
            }
            else {
                condition2 = false; //dice 2 face value can not be used
            }
            return true;
        }
        //If the sum of die 1 and 2 has not been used once or twice(for doubles). When the sum of the dice is used, it prevents the two dice from being used seperately
        else if(getNumSpacesMoving() == (dice1.getFaceValue() + dice2.getFaceValue()) && condition3 && condition1 && condition2){
            spacesLeftToMove -= getNumSpacesMoving();
            if(numDoubles < 3 && dice1.getFaceValue() == dice2.getFaceValue()){ //for doubles
                numDoubles++;
                numDoubles++;
            }
            else {
                condition3 = false; //sum of die can no longer be used
            }
            return true;
        }
        else {
            return false;
        }
    }

    //returns an integer that represent which player's turn it is
    public int getTurn(){
        return  turn;
    }

    //This method changes the turn, depending on what the current turn is
    public void changeTurn(int currentTurn){
        if (currentTurn == 1){
            turn = 2;
            playerTurn = "Player 2's Turn";
        }
        else{
            turn = 1;
            playerTurn = "Player 1's Turn";
        }
        turnChange = true;
    }

    //Sets the checking conditions back to their initial state
    public void setConditions(){
        condition1 = true;
        condition2 = true;
        condition3 = true;
    }

    //Returns the current player's status towards whether they have a hit piece
    public boolean getPlayerHitPieceStatus(){
        if(getPlayer().hasHitPiece()){
            playerHasHitPiece = true;
        }
        else{
            playerHasHitPiece = false;
        }
        return playerHasHitPiece;
    }

    //Returns the amount of spaces that player can still move
    public int getSpacesLeftToMove(){
        return spacesLeftToMove;
    }

    //Returns the player that is currently playing
    public Player getPlayer(){
        if(getTurn() == 1){
            return player1;
        }
        else{
            return player2;
        }
    }

    /*This method is called at the beginning of a game, after the first roll. it is responsible for determining what player goes first,
     depending on the dice values (dice1 > dice2 or vice versa). If the dices are equal, odd doubles mean that player 2 goes first and even doubles means that player
     1 goes first
     */
    public void chooseTurn(){
        turnChosen = true;
        if(dice1.getFaceValue() > dice2.getFaceValue() || (dice1.getFaceValue() == dice2.getFaceValue() && dice1.getFaceValue() % 2 == 0)){
            playerGoingFirst = "Player 1 Goes First! Roll the Die to start your turn!";
            turn = 1;
        }
        else{
            playerGoingFirst = "Player 2 Goes First! Roll the Die to start your turn!";
            turn = 2;
        }
        repaint();
    }

    //This method sets the outlined piece to false, so that a piece is not highlighted when it is called
    public void setOutlinedPieceToFalse(){
        outlinePiece = false;
        repaint();
    }

    //This method sets the colour of player 1 or player 2's pieces depending on the colour options given for each player
    public void setColour(Color colour){
        if(colour == Color.black || colour == Color.blue || colour == Color.cyan) { //colours offered to player 2
            player2.setColourOfPieces(colour);
        }
        else{
            player1.setColourOfPieces(colour);
        }
        repaint();
    }
}
