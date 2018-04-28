import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    int x1, x2, x3, y1, y2, y3;
    Die dice1 = new Die(340, 540);
    Die dice2 = new Die(410, 540);
    Player player1, player2;
    Stacks stacks = new Stacks();
    Piece removedPiece, addPiece;
    int starting1Y, starting2Y;
    int turn = 1;
    public Board(){
        starting1Y = 20;
        starting2Y = 445;
        player1 = new Player(Color.orange, 710, starting1Y);
        player2 = new Player(Color.black, 710, starting2Y);
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
            stacks.getStacks()[17].push(player2.getPieces()[i]);
        }

        for(int i = 0; i < 24; i++){
            if(i > 11)
                starting1Y = 20;
            else
                starting1Y = 445;
            for(int z = 0; z < stacks.getStacks()[i].size(); z++){
                Piece p = (Piece) stacks.getStacks()[i].get(z);
                if(i > 11)
                    p.setY(starting1Y += 30);
                else
                    p.setY(starting1Y -=30);

            }
        }
        checkBoard();
    }

    public void paintComponent(Graphics g){
        setBackground(Color.black);
        g.setColor(Color.GRAY);
        super.paintComponent(g);
        for(int i = 0; i < 24; i++){
            if(i > 11)
                starting1Y = 20;
            else
                starting1Y = 445;
            for(int z = 0; z < stacks.getStacks()[i].size(); z++){
                Piece p = (Piece) stacks.getStacks()[i].get(z);
                if(i > 11)
                    p.setY(starting1Y += 30);
                else
                    p.setY(starting1Y -=30);

            }
        }
        g.fillRect(100, 50, 700, 400);
        g.setColor(Color.white);
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
        g.drawLine(450, 20, 450, 450);

        for(int i = 0; i < player1.getPieces().length; i++){
            player1.getPieces()[i].draw(g);
            player2.getPieces()[i].draw(g);
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
    public void checkBoard(){
        for (int i = 0; i < 24; i++ )
            if (!stacks.getStacks()[i].isEmpty()) {
                System.out.println("Coordinate " + i + " is empty.");
            }
    }

    public boolean selectedStack(int initialSpot, int finalSpot){
        if(finalSpot - initialSpot == dice1.getFaceValue() || finalSpot - initialSpot == dice2.getFaceValue() || finalSpot - initialSpot == dice2.getFaceValue()){
            if(stacks.getStacks()[initialSpot].isEmpty()){
                return false;
            }
            else {
                System.out.println("hi");
                removedPiece = (Piece) stacks.getStacks()[initialSpot].pop();
                addPiece = (Piece) stacks.getStacks()[finalSpot].push(removedPiece);
                if(turn == 2){
                    player2.movePiece(initialSpot, finalSpot, removedPiece);
                    turn = 1;
                }
                else{
                    player1.movePiece(initialSpot, finalSpot, removedPiece);
                    turn = 2;
                }
                repaint();
                return true;
            }
        }
        else{
            return  false;
        }
    }
}
