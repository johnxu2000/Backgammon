import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    int x1, x2, x3, y1, y2, y3;
    Die dice1 = new Die(340, 540);
    Die dice2 = new Die(410, 540);
    public Board(){
    }
    public void paintComponent(Graphics g){
        setBackground(Color.black);
        g.setColor(Color.GRAY);
        super.paintComponent(g);
        g.fillRect(100, 50, 600, 400);
        g.setColor(Color.white);
        x1 = 650;
        x2 = 600;
        x3 = 625;
        y1 = 450;
        y2 = 450;
        y3 = 270;
        for(int i = 0; i < 2; i++){
            for(int z = 0; z < 10; z++){
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
            x1 += 500;
            x2 += 500;
            x3 += 500;
            y1 -= 400;
            y2 -= 400;
            y3 -= 40;
        }
        g.setColor(new Color(139,69,19));
        g.fillRect(100, 50, 50, 400);
        g.fillRect(650, 50, 50, 400);
        dice1.draw(g);
        dice2.draw(g);
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
