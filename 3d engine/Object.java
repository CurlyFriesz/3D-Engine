import java.io.*;
import java.util.*;
import java.awt.*;

public class Object {
    private Scanner file; //the file to read from
    private ArrayList<ArrayList<Double>> points = new ArrayList<ArrayList<Double>>(); //the points
    private ArrayList<ArrayList<Integer>> faces = new ArrayList<ArrayList<Integer>>(); //the faces

    public Object(String path) throws IOException{
        file = new Scanner(new BufferedReader(new FileReader(path))); //get the file path
    }

    public void read() throws IOException{ //read the obj file
        while(file.hasNext()) { //while there is another line
            String line = file.nextLine(); //get the next line
            if(line.startsWith("v ")) { //if the line starts with v
                String[] values = line.split(" "); //split the line into an array of values at the spaces
                ArrayList<Double> vertex = new ArrayList<Double>(); //create a new vertex array list
                for(int i = 1; i < values.length; i++) {
                    vertex.add(Double.parseDouble(values[i])); //add the values to the vertex array list
                }
                points.add(vertex); //add the vertex to the points array list
            }
            else if(line.startsWith("f ")) { //if the line starts with f
                String[] values = line.split(" "); //split the line into an array of values at the spaces
                ArrayList<Integer> face = new ArrayList<Integer>(); //create a new face array list
                for(int i = 0; i < values.length; i++) { //for each value in the array
                    for(int j = 0; j < values[i].length(); j++) {  //for each character in the value
                        if(values[i].charAt(j) == '/') { //if the character is a /
                            face.add(Integer.parseInt(values[i].substring(0, j))-1); //add the value before the / to the face array list
                            break; //break out of the loop
                        }
                    }
                }
                faces.add(face); //add the face to the faces array list
            }
        }
    }

    private double[][] projected_Points; //the projected points
    private double[][][] projected_Faces; //the projected faces

    private int scale = 100; //scale of the cube
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
      
      //multiply the point by the rotation matrix and scale
      rotated[0] = point[0][0] * scale * rotation_y[0][0] + point[0][1] * scale * rotation_y[0][1] + point[0][2] * scale * rotation_y[0][2];
      rotated[1] = point[0][0] * scale * rotation_y[1][0] + point[0][1] * scale * rotation_y[1][1] + point[0][2] * scale * rotation_y[1][2];
      rotated[2] = point[0][0] * scale * rotation_y[2][0] + point[0][1] * scale * rotation_y[2][1] + point[0][2] * scale * rotation_y[2][2];
      rotated[0] = rotated[0] * rotation_x[0][0] + rotated[1] * rotation_x[0][1] + rotated[2] * rotation_x[0][2];
      rotated[1] = rotated[0] * rotation_x[1][0] + rotated[1] * rotation_x[1][1] + rotated[2] * rotation_x[1][2];
      rotated[2] = rotated[0] * rotation_x[2][0] + rotated[1] * rotation_x[2][1] + rotated[2] * rotation_x[2][2];

      point[0] = rotated; //set the point to the rotated point
      projected[0] = point[0][0] * matrix[0][0] + point[0][1] * matrix[0][1] + point[0][2] * matrix[0][2]; //multiply the point by the matrix
      projected[1] = point[0][0] * matrix[1][0] + point[0][1] * matrix[1][1] + point[0][2] * matrix[1][2];

      return projected; //return the projected point
  }

    public void connect(Graphics g, double[][] p) {
      for(int i = 0; i < projected_Faces.length; i++) {
        for(int j = 0; j < faces.get(i).size(); j++) {
          projected_Faces[i][j] = p[faces.get(i).get(j)]; //set the projected faces to the projected points
        }
      }
      for(int i = 0; i < projected_Faces.length; i ++) {
        g.setColor(Color.BLACK);
        //draw the lines
        g.drawLine((int) projected_Faces[i][0][0] + 500, (int) projected_Faces[i][0][1] + 350, (int) projected_Faces[i][1][0] + 500, (int) projected_Faces[i][1][1] + 350);
        g.drawLine((int) projected_Faces[i][1][0] + 500, (int) projected_Faces[i][1][1] + 350, (int) projected_Faces[i][2][0] + 500, (int) projected_Faces[i][2][1] + 350);
        g.drawLine((int) projected_Faces[i][2][0] + 500, (int) projected_Faces[i][2][1] + 350, (int) projected_Faces[i][0][0] + 500, (int) projected_Faces[i][0][1] + 350);
      }
    }

    public void draw(Graphics g) {
      projected_Faces = new double[faces.size()][3][2]; //create the projected faces array
      projected_Points = new double[points.size()][2]; //create the projected points array
      for(int i = 0; i < points.size(); i++) {
        double[][] point = new double[1][3]; //create the point
        point[0] = new double[]{points.get(i).get(0), points.get(i).get(1), points.get(i).get(2)}; //set the point to the point in the points array
        double[] point1 = project(point, new double[][]{{-1, 0, 0}, {0, -1, 0}}); //project the point
        projected_Points[i] = point1; //set the projected point to the projected points array
        g.setColor(Color.BLACK);
        g.fillOval((int) point1[0] + 497, (int) point1[1] + 347, 6, 6); //draw a point at the first point
      }
      connect(g, projected_Points); //connect the points
    }
}
