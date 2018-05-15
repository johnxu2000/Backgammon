import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
/*
The Board class draws the dices and pieces at the same time also contains essential algorithms for the game to run.
 */
public class Board extends JPanel {
    int x1, x2, x3, y1, y2, y3;
    Die dice1 = new Die(340, 540);
    Die dice2 = new Die(410, 540);
    Player player1, player2;
    Stacks stacks = new Stacks();
    Piece removedPiece, addPiece; //Pieces can be added individually
    int startingY, startingX;
    int xOutlinedPiece, yOutlinedPiece;
    boolean outlinePiece = false;
    int turn = 1;
    boolean p1Victory = false;
    boolean p2Victory = false;
    int totalNumSpaces = 0;
    int numSpacesMoving;
    boolean condition1 = true;
    boolean condition2 = true;
    boolean condition3 = true;
    boolean player1HasHitPiece = false;
    boolean player2HasHitPiece = false;
    public Board(){
        player1 = new Player(Color.orange, startingX, startingY);
        player2 = new Player(Color.black, startingX, startingY);
        for(int i = 0; i < 2; i++) { //Putting the pieces on the board at the correct spots
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
        checkBoard();
    }

    public void paintComponent(Graphics g){
        setBackground(Color.black);
        g.setColor(Color.GRAY);
        super.paintComponent(g);
        startingX = 710;
        for(int i = 0; i < 24; i++){
            if(i > 11) {
                startingY = 20;
            }
            else {
                startingY = 445;
            }
            for(int z = 0; z < stacks.getStacks()[i].size(); z++){
                Piece p = (Piece) stacks.getStacks()[i].get(z);
                if(i > 11) {
                    p.setY(startingY += 30);
                    p.setX(startingX);
                }
                else {
                    p.setY(startingY -= 30);
                    p.setX(startingX);
                }

            }
            if(i > 11) {
                startingX += 50;
            }
            else if (i == 11){

            }
            else {
                startingX -= 50;
            }
        }
        g.fillRect(100, 50, 700, 400);
        g.setColor(Color.white);
        g.fillRect(820, 20, 150, 550);
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
        g.setColor(new Color(139,69,19));
        g.fillRect(100, 50, 50, 400);
        g.fillRect(750, 50, 50, 400);
        g.setColor(Color.gray);
        g.fillRect(100, 240, 50, 20);
        g.fillRect(750, 240, 50, 20);
        dice1.draw(g);
        dice2.draw(g);
        g.setColor(Color.black);
        g.setFont(new Font("Serif", Font.BOLD, 20));
        g.drawString("Hit Pieces:", 850, 50);
        g.drawLine(450, 20, 450, 450);

        g.setColor(Color.green);
        if(outlinePiece){
            g.drawOval(xOutlinedPiece, yOutlinedPiece, 30, 30);
        }
        int hitPiecesY = 60;
        int hitPiecesX = 880;
        for(int i = 0; i < player1.hitPieces.size(); i++){
            player1.hitPieces.get(i).setX(hitPiecesX);
            player1.hitPieces.get(i).setY(hitPiecesY);
            hitPiecesY += 50;
        }
        for(int i = 0; i < player2.hitPieces.size(); i++){
            player2.hitPieces.get(i).setX(hitPiecesX);
            player2.hitPieces.get(i).setY(hitPiecesY);
            hitPiecesY += 50;
        }
        for(int i = 0; i < player1.getPieces().length; i++){
            player1.getPieces()[i].draw(g);
            player2.getPieces()[i].draw(g);
        }
    }

    public void rollDice1(){
        dice1.roll();
        totalNumSpaces = dice1.getFaceValue() + dice2.getFaceValue();
        repaint();
    }
    public void rollDice2(){
        dice2.roll();
        totalNumSpaces = dice1.getFaceValue() + dice2.getFaceValue();
        repaint();
    }
    public void checkBoard() {
        while(!p1Victory && !p2Victory) {
            for (int i = 0; i < 24; i++) {
                if (stacks.getStacks()[i].isEmpty()) {
                    System.out.println("Coordinate " + i + " is empty.");
                } else if (stacks.getStacks()[i].peek() == player1) {
                    System.out.println("Coordinate " + i + " contains p1 pieces");
                    p1Victory = false;
                } else if (stacks.getStacks()[i].peek() == player2) {
                    System.out.println("Coordinate " + i + " contains p2 pieces");
                    p2Victory = false;
                } else p1Victory = true; p2Victory = true;
            }
        }
    }
    public boolean selectedStack(int initialSpot, int finalSpot){
        boolean moveStatus = false;
        boolean usingHitPiece = false;
        Piece hitPiece;
        if(initialSpot == -1){
            if(getTurn() == 1){
                initialSpot = 24;
            }
            usingHitPiece = true;
        }
        if(checkValididtyOfMove(initialSpot, finalSpot)) { //Runs after selected piece. This decides if the piece could move to a specific spot the user pointed at.
            if(checkDiceUsage()) {
                moveStatus = true;
                if (!usingHitPiece && stacks.getStacks()[finalSpot].size() == 1 && ((Piece) stacks.getStacks()[initialSpot].peek()).getColour() != ((Piece) stacks.getStacks()[finalSpot].peek()).getColour()) {
                    hitPiece = (Piece) stacks.getStacks()[finalSpot].pop();
                    if (getTurn() == 1) {
                        player2.addHitPiece(hitPiece);
                    } else {
                        player1.addHitPiece(hitPiece);
                    }
                }
                if(!usingHitPiece) {
                    removedPiece = (Piece) stacks.getStacks()[initialSpot].pop();
                    addPiece = (Piece) stacks.getStacks()[finalSpot].push(removedPiece);
                }
                if(usingHitPiece) {
                    if(getTurn() == 1){
                        if (stacks.getStacks()[finalSpot].size() == 1 && player1.hitPieces.peek().getColour() != ((Piece) stacks.getStacks()[finalSpot].peek()).getColour()) {
                            hitPiece = (Piece) stacks.getStacks()[finalSpot].pop();
                            player2.addHitPiece(hitPiece);
                        }
                        removedPiece = player1.getHitPieces().pop();
                        addPiece = (Piece) stacks.getStacks()[finalSpot].push(removedPiece);
                    }
                    else{
                        if (stacks.getStacks()[finalSpot].size() == 1 && player2.hitPieces.peek().getColour() != ((Piece) stacks.getStacks()[finalSpot].peek()).getColour()){
                            hitPiece = (Piece) stacks.getStacks()[finalSpot].pop();
                            player1.addHitPiece(hitPiece);
                        }
                        removedPiece = player2.getHitPieces().pop();
                        addPiece = (Piece) stacks.getStacks()[finalSpot].push(removedPiece);
                    }
                }
            }
        }
        return moveStatus;
    }

    public void selectedPiece(int initialSpot){
        Piece topOfHits;
        if(initialSpot == -1){
            if(turn == 1){
                topOfHits = player1.getHitPieces().get(0);
            }
            else{
                topOfHits = player2.getHitPieces().get(0);
            }
            xOutlinedPiece = topOfHits.getX();
            yOutlinedPiece = topOfHits.getY();
            outlinePiece = true;
            repaint();
        }
        else {
            if (!stacks.getStacks()[initialSpot].isEmpty()) {
                Piece topOfStack = (Piece) stacks.getStacks()[initialSpot].peek();
                if (turn == 1) {
                    if (topOfStack == player1.movePieceCheck(topOfStack)) {
                        xOutlinedPiece = topOfStack.getX();
                        yOutlinedPiece = topOfStack.getY();
                        outlinePiece = true;
                        repaint();

                    }
                }
                if (turn == 2) {
                    if (topOfStack == player2.movePieceCheck(topOfStack)) {
                        xOutlinedPiece = topOfStack.getX();
                        yOutlinedPiece = topOfStack.getY();
                        outlinePiece = true;
                        repaint();
                    }
                }
            }
        }
    }

    public boolean checkValididtyOfMove(int initialSpot, int finalSpot){ //If the stack meets the requirement for the piece to move there.
        boolean move = false;
        if(turn == 2) {
            if (finalSpot - initialSpot == dice1.getFaceValue() || finalSpot - initialSpot == dice2.getFaceValue() || finalSpot - initialSpot == (dice2.getFaceValue() + dice1.getFaceValue())) {
                System.out.println("hi");
                if (player2.getHitPieces().size() != 0 || !stacks.getStacks()[initialSpot].isEmpty()) {
                    System.out.println("hi");
                    if (player2.getHitPieces().size() != 0 || stacks.getStacks()[initialSpot].peek() == player2.movePieceCheck((Piece) stacks.getStacks()[initialSpot].peek())) {
                        System.out.println("hi");
                        if ((stacks.getStacks()[finalSpot].size() != 0 && ((Piece) stacks.getStacks()[finalSpot].peek()).getColour() == Color.black) || stacks.getStacks()[finalSpot].size() == 0)  {
                            numSpacesMoving = finalSpot - initialSpot;
                            System.out.println(numSpacesMoving);
                                move = true;
                        }
                        else if(stacks.getStacks()[finalSpot].size() == 1 && ((Piece) stacks.getStacks()[finalSpot].peek()).getColour() == Color.orange){
                            numSpacesMoving = finalSpot - initialSpot;
                                move = true;
                        }
                    }
                }
            }
        }
        if(turn == 1) {
            if (initialSpot - finalSpot == dice1.getFaceValue() || initialSpot - finalSpot == dice2.getFaceValue() || initialSpot - finalSpot == (dice2.getFaceValue() + dice1.getFaceValue())) {
                if (player1.getHitPieces().size() != 0 || !stacks.getStacks()[initialSpot].isEmpty()) {
                    if (player1.getHitPieces().size() != 0|| stacks.getStacks()[initialSpot].peek() == player1.movePieceCheck((Piece) stacks.getStacks()[initialSpot].peek())) {
                        if ((stacks.getStacks()[finalSpot].size() != 0 && ((Piece) stacks.getStacks()[finalSpot].peek()).getColour() == Color.orange) || stacks.getStacks()[finalSpot].size() == 0) {
                            System.out.println(numSpacesMoving);
                            numSpacesMoving = initialSpot - finalSpot;
                                move = true;
                        }
                        else if(stacks.getStacks()[finalSpot].size() == 1 && ((Piece) stacks.getStacks()[finalSpot].peek()).getColour() == Color.black){
                            numSpacesMoving = initialSpot - finalSpot;
                                move = true;
                        }
                    }
                }
            }
        }
        return move;
    }

    public int getNumSpacesMoving(){
        return numSpacesMoving;
    }

    public boolean checkDiceUsage(){
        if(getNumSpacesMoving() == dice1.getFaceValue() && condition1 && condition3){
            totalNumSpaces -= getNumSpacesMoving();
            condition1 = false;
            return true;
        }
        else if(getNumSpacesMoving() == dice2.getFaceValue() && condition2 && condition3){
            totalNumSpaces -= getNumSpacesMoving();
            condition2 = false;
            return true;
        }
        else if(getNumSpacesMoving() == (dice1.getFaceValue() + dice2.getFaceValue()) && condition3 && condition1 && condition2){
            totalNumSpaces -= getNumSpacesMoving();
            condition3 = false;
            return true;
        }
        else {
            return false;
        }
    }

    public int getTurn(){
        return  turn;
    }

    public void changeTurn(int currentTurn){
        if (currentTurn == 1){
            turn = 2;
        }
        else{
            turn = 1;
        }
    }

    public void setConditions(){
        condition1 = true;
        condition2 = true;
        condition3 = true;
    }

    public boolean getPlayer1HitPieceStatus(){
        if(player1.hasHitPiece()){
            player1HasHitPiece = true;
        }
        else{
            player1HasHitPiece = false;
        }
        return player1HasHitPiece;
    }
    public boolean getPlayer2HitPieceStatus(){
        if(player2.hasHitPiece()){
            player2HasHitPiece = true;
        }
        else{
            player2HasHitPiece = false;
        }
        return player2HasHitPiece;
    }

    public int getTotalNumSpaces(){
        return totalNumSpaces;
    }


    public void setOutlinedPieceToFalse(){
        outlinePiece = false;
        repaint();
    }
}
