

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class GameForm{
    private JPanel gamePanel;
    private JPanel backgammonPanel;
    private JPanel menuPanel;
    private JButton roll;

    public GameForm(){
        roll.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ((Board) gamePanel).rollDice1();
                ((Board) gamePanel).rollDice2();
            }
        });
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("");
        GameForm form = new GameForm();
        frame.add(form.gamePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        gamePanel = new Board();


    }
}
