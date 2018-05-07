import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board extends JPanel {
    int x1, x2, x3, y1, y2, y3;
    Die dice1 = new Die(340, 540);
    Die dice2 = new Die(410, 540);
    Player player1, player2;
    Stacks stacks = new Stacks();
    Piece removedPiece, addPiece;
    ArrayList <Piece> hitPieces = new ArrayList<Piece>();
    int startingY, startingX;
    int xOutlinedPiece, yOutlinedPiece;
    boolean outlinePiece = false;
    int turn = 1;
    public Board(){
        player1 = new Player(Color.orange, startingX, startingY);
        player2 = new Player(Color.black, startingX, startingY);
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

        for(int i = 0; i < player1.getPieces().length; i++){
            player1.getPieces()[i].draw(g);
            player2.getPieces()[i].draw(g);
        }
        g.setColor(Color.green);
        if(outlinePiece){
            g.drawOval(xOutlinedPiece, yOutlinedPiece, 30, 30);
        }
        int hitPiecesY = 10;
        for(int i = 0; i < hitPieces.size(); i++){
            hitPieces.get(i).setX(880);
            hitPieces.get(i).setY(hitPiecesY += 50);
            hitPieces.get(i).draw(g);
        }
    }

    public void rollDice1(){
        dice1.roll();
        repaint();
    }
    public void rollDice2(){
        dice2.roll();
        repaint();
    }
    public void checkBoard() {
        boolean p1Victory = false;
        boolean p2Victory = false;
        for (int i = 0; i < 24; i++) {
            if (stacks.getStacks()[i].isEmpty()) {
                System.out.println("Coordinate " + i + " is empty.");
            } else if (stacks.getStacks()[i].peek() == player1) {
                System.out.println("Coordinate " + i + " contains p1 pieces");
                p1Victory = false;
            } else if (stacks.getStacks()[i].peek() == player2) {
                System.out.println("Coordinate " + i + " contains p2 pieces");
                p2Victory = false;
            }
        }

    }


    public boolean selectedStack(int initialSpot, int finalSpot){
        boolean moveStatus = false;
        Piece hitPiece;
        if(checkValididtyOfMove(initialSpot, finalSpot)) {
            if (stacks.getStacks()[finalSpot].size() == 1 && ((Piece) stacks.getStacks()[initialSpot].peek()).getColour() != ((Piece) stacks.getStacks()[finalSpot].peek()).getColour()) {
                hitPiece = (Piece) stacks.getStacks()[finalSpot].pop();
                hitPieces.add(hitPiece);
            }
            moveStatus = true;
            removedPiece = (Piece) stacks.getStacks()[initialSpot].pop();
            addPiece = (Piece) stacks.getStacks()[finalSpot].push(removedPiece);
            if(turn == 1){
                turn = 2;
            }
            else
                turn = 1;
            repaint();
        }
        return moveStatus;
    }

    public void selectedPiece(int initialSpot){
        if(!stacks.getStacks()[initialSpot].isEmpty()) {
            Piece topOfStack = (Piece)stacks.getStacks()[initialSpot].peek();
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

    public boolean checkValididtyOfMove(int initialSpot, int finalSpot){
        boolean move = false;
        if(turn == 2) {
            if (finalSpot - initialSpot == dice1.getFaceValue() || finalSpot - initialSpot == dice2.getFaceValue() || finalSpot - initialSpot == (dice2.getFaceValue() + dice1.getFaceValue())) {
                if (!stacks.getStacks()[initialSpot].isEmpty()) {
                    if (stacks.getStacks()[initialSpot].peek() == player2.movePieceCheck((Piece) stacks.getStacks()[initialSpot].peek())) {
                        move = true;
                    }
                }
            }
        }
        if(turn == 1){
            if (initialSpot - finalSpot == dice1.getFaceValue() || initialSpot - finalSpot == dice2.getFaceValue() || initialSpot - finalSpot == (dice2.getFaceValue() + dice1.getFaceValue())) {
                if (!stacks.getStacks()[initialSpot].isEmpty()) {
                    if (stacks.getStacks()[initialSpot].peek() == player1.movePieceCheck((Piece) stacks.getStacks()[initialSpot].peek())) {
                        move = true;
                    }
                }
            }
        }
        return move;
    }

    public void playerHasHitPiece(){
        if(hitPieces.size() != 0){

        }
    }


    public void setOutlinedPiece(){
        outlinePiece = false;
    }
}
