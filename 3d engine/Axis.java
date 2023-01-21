import java.awt.*;

public class Axis {

    private double[][] points = new double[][]{ {0, 1, 0},
                                                {10, 1, 0},
                                                {0, -10, 0},
                                                {0, 1, 10}}; //viewport axis

    private double[][] projection_matrix = new double[][]{{1, 0, 0},
                                                          {0, 1, 0}}; //projection matrix

    private double[][] projected_Points = new double[points.length][2]; //the projected points

    private int scale = 100; //scale of the cube
    private double[][] rotY; //rotation matrix for the y axis  
    private double[][] rotX; //rotation matrix for the x axis
                           
                           

    public double[] project(double[][] point, double[][] matrix) { //project a point onto a 2D plane
        double[] projected = new double[2]; //the projected point
        double[] rotated = new double[3]; //the rotated point

        double angleX = Panel.angleX; //angle of rotation
        double angleY = Panel.angleY; //angle of rotation

        rotY = new double[][]{{Math.cos(angleY), 0, Math.sin(angleY)}, //create the rotation matrix for th
                      {0, 1, 0},
                      {-Math.sin(angleY), 0, Math.cos(angleY)}};; //rotation matrix for the y axis
        rotX = new double[][]{{1, 0, 0}, //create the rotation matrix for the x axis
                      {0, Math.cos(angleX), -Math.sin(angleX)},
                      {0, Math.sin(angleX), Math.cos(angleX)}};; //rotation matrix for the x axis

        //multiply the point by the rotation matrix and the scale
        rotated[0] = point[0][0] * scale * rotY[0][0] + point[0][1] * scale * rotY[0][1] + point[0][2] * scale * rotY[0][2];
        rotated[1] = point[0][0] * scale * rotY[1][0] + point[0][1] * scale * rotY[1][1] + point[0][2] * scale * rotY[1][2];
        rotated[2] = point[0][0] * scale * rotY[2][0] + point[0][1] * scale * rotY[2][1] + point[0][2] * scale * rotY[2][2];
        rotated[0] = rotated[0] * rotX[0][0] + rotated[1] * rotX[0][1] + rotated[2] * rotX[0][2];
        rotated[1] = rotated[0] * rotX[1][0] + rotated[1] * rotX[1][1] + rotated[2] * rotX[1][2];
        rotated[2] = rotated[0] * rotX[2][0] + rotated[1] * rotX[2][1] + rotated[2] * rotX[2][2];

        point[0] = rotated; //set the point to the rotated point
        projected[0] = point[0][0] * matrix[0][0] + point[0][1] * matrix[0][1] + point[0][2] * matrix[0][2]; //multiply the point by the projection matrix
        projected[1] = point[0][0] * matrix[1][0] + point[0][1] * matrix[1][1] + point[0][2] * matrix[1][2]; 

        return projected; //return the projected point
    }

    public void connect(Graphics g, int i, int j, double[][]p2) {
        for(int k = 0; k < points.length; k++) { //project the points
            projected_Points[k] = project(new double[][]{points[k]}, projection_matrix);
        }
        g.drawLine((int) p2[i][0] + 500, (int) p2[i][1] + 350, (int) p2[j][0] + 500, (int) p2[j][1] + 350); //draw the line between the points
    }

    public void draw(Graphics g) {
        for (int i = 0; i < points.length; i++) { //draw the front face vertices
            for(int j = i + 1; j < points.length; j++) { //draw the lines between the vertices
                if (points[i][0] == points[j][0] && points[i][1] == points[j][1] && points[i][2] != points[j][2]) { //if the x and y coordinates are the same but the z coordinates are different
                    g.setColor(Color.BLUE);
                    connect(g, i, j, projected_Points); //connect the points
                }
                if (points[i][0] == points[j][0] && points[i][2] == points[j][2] && points[i][1] != points[j][1]) { //if the x and z coordinates are the same but the y coordinates are different
                    g.setColor(Color.GREEN);
                    connect(g, i, j, projected_Points); //connect the points
                }
                if (points[i][1] == points[j][1] && points[i][2] == points[j][2] && points[i][0] != points[j][0]) { //if the y and z coordinates are the same but the x coordinates are different
                    g.setColor(Color.RED);
                    connect(g, i, j, projected_Points); //connect the points
                }
            }
        }
    }
}
