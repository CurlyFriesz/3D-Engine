import javax.swing.*;

public class Engine extends JFrame{
    Panel panel = new Panel();

    public Engine(){
        super("3D engine");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panel);
        pack();
        setVisible(true);
    }
    
    public static void main(String[] args){
        new Engine();
    }
}


