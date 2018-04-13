import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    Die dice1 = new Die(50, 500);
    Die dice2 = new Die(150, 500);
    public Board(){
    }
    public void paintComponent(Graphics g){
        setBackground(Color.red);
        super.paintComponent(g);
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
