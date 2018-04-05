import javax.swing.*;

public class Backgammon {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Backgammon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        BackgammonBoard bb = new BackgammonBoard();
        frame.getContentPane().add(bb);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }
}
