import javax.swing.JFrame;

public class Checkers extends JFrame {

    public Checkers() {
        
        setTitle("Checkers");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(false); 
        add(new Board());
        setVisible(true);
    }
    public static void main(String[] args) {
        new Checkers();
    }
}