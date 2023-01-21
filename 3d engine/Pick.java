import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class Pick{
    private JFileChooser chooser; //file chooser
    private FileNameExtensionFilter filter = new FileNameExtensionFilter("OBJ files", "obj"); //file filter
    private Rectangle btn1 = new Rectangle(10, 10, 100, 50); 
    private Rectangle btn2 = new Rectangle(120, 10, 100, 50);
    private Rectangle btn3 = new Rectangle(230, 10, 100, 50);
    public String path; //path to the file

    public Pick() {
        chooser = new JFileChooser(); //create a new file chooser
    }

    public void choose(double x, double y) {
        chooser.setFileFilter(filter); //set the file filter
        if(btn1.contains(x,y)) { //if the import button is clicked
            int returnVal = chooser.showOpenDialog(null); //open the file chooser
            if(returnVal == JFileChooser.APPROVE_OPTION) { //if a file is selected
                path = chooser.getSelectedFile().getPath(); //get the path to the file
                Panel.shape = 3; //set the shape to 3
            }
        }
        if(btn2.contains(x,y)) { //if the cube button is clicked
            Panel.shape = 1; //set the shape to 1
        }
        if(btn3.contains(x,y)) { //if the prism button is clicked
            Panel.shape = 2; //set the shape to 2
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        //draw the buttons
        g.fillRect((int)btn1.getX(), (int)btn1.getY(), (int)btn1.getWidth(), (int)btn1.getHeight());
        g.fillRect((int)btn2.getX(), (int)btn2.getY(), (int)btn2.getWidth(), (int)btn2.getHeight());
        g.fillRect((int)btn3.getX(), (int)btn3.getY(), (int)btn3.getWidth(), (int)btn3.getHeight());
        g.setColor(Color.BLACK);
        g.setFont(new Font("Roboto", Font.BOLD, 20));
        //draw the text
        g.drawString("IMPORT", 20, 40);
        g.drawString("CUBE", 140, 40);
        g.drawString("PRISM", 240, 40);
    }
}