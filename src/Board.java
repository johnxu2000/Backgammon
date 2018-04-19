import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    int x1, x2, x3, y1, y2, y3;
    Die dice1 = new Die(340, 540);
    Die dice2 = new Die(410, 540);
    Player player1, player2;
    public Board(){
        player1 = new Player(Color.orange, 710, 55);
        player2 = new Player(Color.black, 710, 415);
    }
    public void paintComponent(Graphics g){
        setBackground(Color.black);
        g.setColor(Color.GRAY);
        super.paintComponent(g);
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
}
