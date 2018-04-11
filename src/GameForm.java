import javax.swing.*;

public class GameForm {
    private JPanel gamePanel;
    private JPanel menuPanel;
    private JPanel Board;

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
        gamePanel = new Board();
        // TODO: place custom component creation code here
    }
}
