import javax.swing.*;
import java.awt.*;

public class Board extends JPanel {
    Die dice1 = new Die(50, 500);
    Die dice2 = new Die(150, 500);
    public void paintComponent(Graphics g){
        super.paintComponent(g);
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
