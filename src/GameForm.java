

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
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Board) gamePanel).rollDice1();
                ((Board) gamePanel).rollDice2();
            }
        });
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
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
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("");
        GameForm form = new GameForm();
        frame.add(form.backgammonPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setPreferredSize(new Dimension(1000, 600));
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        gamePanel = new Board();


    }
}
