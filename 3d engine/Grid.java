import java.awt.*;

public class Grid {
    private double[][] points = new double[][]{{10, 1, 10},
                                               {10, 1, -10},
                                               {8, 1, 10},
                                               {8, 1, -10},
                                               {6, 1, 10},
                                               {6, 1, -10},
                                               {4, 1, 10},
                                               {4, 1, -10},
                                               {2, 1, 10},
                                               {2, 1, -10},
                                               {0, 1, 10},
                                               {0, 1, -10},
                                               {-2, 1, 10},
                                               {-2, 1, -10},
                                               {-4, 1, 10},
                                               {-4, 1, -10},
                                               {-6, 1, 10},
                                               {-6, 1, -10},
                                               {-8, 1, 10},
                                               {-8, 1, -10},
                                               {-10, 1, 10},
                                               {-10, 1, -10},
                                               {-10, 1, -10},
                                               {10, 1, -10},
                                               {-10, 1, -8},
                                               {10, 1, -8},
                                               {-10, 1, -6},
                                               {10, 1, -6},
                                               {-10, 1, -4},
                                               {10, 1, -4},
                                               {-10, 1, -2},
                                               {10, 1, -2},
                                               {-10, 1, 0},
                                               {10, 1, 0},
                                               {-10, 1, 2},
                                               {10, 1, 2},
                                               {-10, 1, 4},
                                               {10, 1, 4},
                                               {-10, 1, 6},
                                               {10, 1, 6},
                                               {-10, 1, 8},
                                               {10, 1, 8},
                                               {-10, 1, 10},
                                               {10, 1, 10}}; //grid points
    
    private double[][] projection_matrix = new double[][]{{1, 0, 0},
                                                          {0, 1, 0}}; //projection matrix
                          
    private double[][] projected_Points = new double[points.length][2]; //the projected points

    private int scale = 100; //scale of the cube
    double angleX = Panel.angleX; //angle of rotation
    double angleY = Panel.angleY; //angle of rotation
    private double[][] rotation_x; //rotation matrix for the x axis
    private double[][] rotation_y; //rotation matrix for the y axis

    public double[] project(double[][] point, double[][] matrix) { //project a point onto a 2D plane
        double[] projected = new double[2]; //the projected point
        double[] rotated = new double[3]; //the rotated point

        double angleX = Panel.angleX; //angle of rotation
        double angleY = Panel.angleY; //angle of rotation
        rotation_x = new double[][]{{1, 0, 0}, //create the rotation matrix for the x axis
                             {0, Math.cos(angleX), -Math.sin(angleX)},
                             {0, Math.sin(angleX), Math.cos(angleX)}};; //rotation matrix for the x axis
        rotation_y = new double[][]{{Math.cos(angleY), 0, Math.sin(angleY)}, //create the rotation matrix for the y
                             {0, 1, 0},
                             {-Math.sin(angleY), 0, Math.cos(angleY)}};; //rotation matrix for the y axis
                            
        //multiply the point by the rotation matrix and the scale
        rotated[0] = point[0][0] * scale * rotation_y[0][0] + point[0][1] * scale * rotation_y[0][1] + point[0][2] * scale * rotation_y[0][2];
        rotated[1] = point[0][0] * scale * rotation_y[1][0] + point[0][1] * scale * rotation_y[1][1] + point[0][2] * scale * rotation_y[1][2];
        rotated[2] = point[0][0] * scale * rotation_y[2][0] + point[0][1] * scale * rotation_y[2][1] + point[0][2] * scale * rotation_y[2][2];
        rotated[0] = rotated[0] * rotation_x[0][0] + rotated[1] * rotation_x[0][1] + rotated[2] * rotation_x[0][2];
        rotated[1] = rotated[0] * rotation_x[1][0] + rotated[1] * rotation_x[1][1] + rotated[2] * rotation_x[1][2];
        rotated[2] = rotated[0] * rotation_x[2][0] + rotated[1] * rotation_x[2][1] + rotated[2] * rotation_x[2][2];

        point[0] = rotated; //set the point to the rotated point
        projected[0] = point[0][0] * matrix[0][0] + point[0][1] * matrix[0][1] + point[0][2] * matrix[0][2]; //multiply the point by the projection matrix
        projected[1] = point[0][0] * matrix[1][0] + point[0][1] * matrix[1][1] + point[0][2] * matrix[1][2];

        return projected; //return the projected point
    }

    public void connect(Graphics g, int i, int j, double[][]p2) {
        for(int k = 0; k < points.length; k++) { //project all the points
            projected_Points[k] = project(new double[][]{points[k]}, projection_matrix);
        }
        g.drawLine((int) p2[i][0] + 500, (int) p2[i][1] + 350, (int) p2[j][0] + 500, (int) p2[j][1] + 350); //draw the lines
    }

    public void draw(Graphics g) {
        g.setColor(Color.GRAY);
        for(int i = 0; i < points.length; i+=2) {
            connect(g, i, i+1, projected_Points); //connect the points
        }
    }
}
