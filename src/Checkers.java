import javax.swing.JFrame;

public class Checkers extends JFrame {

    public Checkers() {
        add(new Board());
        setTitle("Checkers");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(806, 629);
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }
    public static void main(String[] args) {
        new Checkers();
    }
}