import java.awt.*;

public class Pyramid {
    private double[][] points = new double[][]{{-1, 1, 1},
                                                {0, -1, 0},
                                                {-1, 1, -1},
                                                {1, 1, 1},
                                                {1, 1, -1}}; //5 points for a basic pyramid

    private int[][] faces = new int[][]{{0,1,2},
                                        {2,1,4},
                                        {4,1,3},
                                        {3,1,0},
                                        {4,0,2},
                                        {4,3,0}}; //6 faces for a basic pyramid
    
    private double[][] projection_matrix = new double[][]{{1, 0, 0},
                                                          {0, 1, 0}}; //projection matrix
                                      
    private double[][] projected_Points = new double[points.length][2]; //the projected points
    private double[][][] projected_Faces = new double[faces.length][3][2]; //the projected faces

    private int scale = 100; //scale of the cube
    private double[][] rotation_x; //rotation matrix for the x axis
    private double[][] rotation_y; //rotation matrix for the y axis

    public void rotate() {
      double angleX = Panel.angleX; //angle of rotation
      double angleY = Panel.angleY; //angle of rotation
      rotation_x = new double[][]{{1, 0, 0}, //create the rotation matrix for the x axis
                                  {0, Math.cos(angleX), -Math.sin(angleX)},
                                  {0, Math.sin(angleX), Math.cos(angleX)}};
      rotation_y = new double[][]{{Math.cos(angleY), 0, Math.sin(angleY)}, //create the rotation matrix for the y axis
                                  {0, 1, 0},
                                  {-Math.sin(angleY), 0, Math.cos(angleY)}};
    }

    public double[] project(double[][] point, double[][] matrix) { //project a point onto a 2D plane
      double[] projected = new double[2]; //the projected point
      double[] rotated = new double[3]; //the rotated point
      
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

    public void connect(Graphics g, double[][] p) {
      for(int i = 0; i < faces.length; i++) { 
        for(int j = 0; j < faces[i].length; j++) {
          projected_Faces[i][j] = p[faces[i][j]]; //get all the points to connect for each face
        }
      }
      for(int i = 0; i < projected_Faces.length; i ++) { //draw the faces
        g.setColor(Color.BLACK);
        g.drawLine((int) projected_Faces[i][0][0] + 500, (int) projected_Faces[i][0][1] + 350, (int) projected_Faces[i][1][0] + 500, (int) projected_Faces[i][1][1] + 350); //draw the first line
        g.drawLine((int) projected_Faces[i][1][0] + 500, (int) projected_Faces[i][1][1] + 350, (int) projected_Faces[i][2][0] + 500, (int) projected_Faces[i][2][1] + 350); //draw the second line
        g.drawLine((int) projected_Faces[i][2][0] + 500, (int) projected_Faces[i][2][1] + 350, (int) projected_Faces[i][0][0] + 500, (int) projected_Faces[i][0][1] + 350); //draw the third line
      }
    }

    public void draw(Graphics g) {
      for (int i = 0; i < points.length; i++) { //draw the front face vertices
        double[] point1 = project(new double[][]{points[i]}, projection_matrix); //project the point onto the 2D plane
        projected_Points[i] = point1;
        g.setColor(Color.BLACK);
        g.fillOval((int) point1[0] + 497, (int) point1[1] + 347, 6, 6); //draw a point at the first point
      }
      connect(g, projected_Points); //connect the points
    }
}
