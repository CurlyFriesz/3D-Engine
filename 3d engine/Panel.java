import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class Panel extends JPanel implements ActionListener, KeyListener, MouseListener{
    private boolean keys[];
    private Cube cube = new Cube();
    private Pyramid pyr = new Pyramid();
    private Object obj;
    private Grid grid = new Grid();
    private Axis axis = new Axis();
    private Pick load = new Pick(); 
    public static int width = 1000;
    public static int height = 700;
    public static double angleX = 0.5;
    public static double angleY = 0.5;
    public static int shape = 0;
    private int CUBE = 1;
    private int PYRAMID = 2;
    private int CUST = 3;
    private Timer timer;

    public Panel(){
        keys = new boolean[KeyEvent.KEY_LAST+1];
        setPreferredSize(new Dimension(width, height));
        timer = new Timer(10, this);
        setFocusable(true);
        requestFocus();
        addKeyListener(this);
        addMouseListener(this);
        timer.start();
    }

    public void update(){
        cube.rotate();
        pyr.rotate();
        move();
        repaint();
    }

    public void move() {
        if(keys[KeyEvent.VK_W]) {
            angleX += 0.01;
        }
        if(keys[KeyEvent.VK_S]) {
            angleX -= 0.01;
        }
        if(keys[KeyEvent.VK_A]) {
            angleY -= 0.01;
        }
        if(keys[KeyEvent.VK_D]) {
            angleY += 0.01;
        }
        if(angleX > 2*Math.PI) {
            angleX = 0;
        }
        if(angleX < 0) {
            angleX = 2*Math.PI;
        }
        if(angleY > 2*Math.PI) {
            angleY = 0;
        }
        if(angleY < 0) {
            angleY = 2*Math.PI;
        }
    }
    
    @Override
	public void keyReleased(KeyEvent ke){
		int key = ke.getKeyCode();
		keys[key] = false;
	}	
	
	@Override
	public void keyPressed(KeyEvent ke){
		int key = ke.getKeyCode();
		keys[key] = true;
	}
	
	@Override
	public void keyTyped(KeyEvent ke){}

    @Override
    public void actionPerformed(ActionEvent e){
        update();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        double mouseX = e.getX(); //get mouse coordinates
        double mouseY = e.getY(); //get mouse coordinates
        load.choose(mouseX, mouseY); //load object based on which button is clicked
        if(load.path != null){
            try {
                obj = new Object(load.path); //create object
                obj.read(); //read obj file
            } catch (IOException e1) {
                e1.printStackTrace(); //catch error
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
    public void paintComponent(Graphics g){
        g.setColor(Color.DARK_GRAY);
        g.fillRect(0, 0, 1000, 700);
        grid.draw(g);
        axis.draw(g);
        load.draw(g);
        if(shape == CUBE) {
            cube.draw(g);
        }
        if(shape == PYRAMID) {
            pyr.draw(g);
        }
        if(shape == CUST) {
            obj.draw(g);
        }
    }
}
