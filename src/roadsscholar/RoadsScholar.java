package roadsscholar;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


/**
 *
 * @author wes
 */


public class RoadsScholar
{
    
    private City[] cities = null;
    private Sign[] signs = null;
    private int numIntersect = 0;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
    }
    

    //Method to parse the input file
    private Road[] parseInput(String input)
    {
        BufferedReader in = null;
        Road allRoads[] = null; 
        try{
            in = new BufferedReader(new FileReader(input));
        } catch (Exception e) {
            System.out.println("File not found");
        }
        int numIn = 0;
        try {
            numIn = in.read();
            this.numIntersect = numIn;
            
            numIn = in.read();
            int numRoads = numIn;
            //Create array with correct size
            allRoads = new Road[numRoads];

            numIn = in.read();
            int numCity = numIn;
            //Create an array with the correct size
            this.cities = new City[numCity];
            
            for(int i = 0; i < numRoads; i++) 
            {
                numIn = in.read();
                int start = numIn;
                numIn = in.read();
                int end = numIn;
                char[] inBuffer = new char[1];
                in.read(inBuffer, 0, 1);
                double length = Double.valueOf(Character.toString(inBuffer[0]));

                allRoads[i] = new Road(start, end, length);
            }
            
            for(int i = 0; i < numCity; i++) 
            {
                int inter = in.read();
                String name = in.readLine();
                this.cities[i] = new City(inter, name);
            }
            
            int numSign = in.read();
            this.signs = new Sign[numSign];
            for(int i = 0; i < numSign; i++) 
            {
                numIn = in.read();
                int start = numIn;
                numIn = in.read();
                int end = numIn;
                char[] inBuffer = new char[1];
                in.read(inBuffer, 0, 1);
                double length = Double.valueOf(Character.toString(inBuffer[0]));
                
                this.signs[i] = new Sign(start, end, length);
            }
            
        } catch (IOException ex) {
            System.out.println("Incorrect input");
        }
        
        return allRoads;
    }
    

    public double[][] solve(String fileName)
    {
        Road roads[] = null;// parseInputFile(fileName);
        
        double adjacencyMatrix[][] = makeWeightedAdjacencyMatrix(roads);
        
        double best[][] = floydWarshall(adjacencyMatrix);
        
        return best;
    }
    
    private double[][] floydWarshall(double adjacencyMatrix[][])
    {
        int n = adjacencyMatrix.length;
        double best[][][] = new double[n][n][n];
        int path[][] = new int[n][n];
        for (int i = 0; i < adjacencyMatrix.length; i++)
        {
            for (int j = 0; i < adjacencyMatrix.length; i++)
            {
                path[i][j] = -1;
                
                for (int k = 0; k < adjacencyMatrix.length; k++)
                {
                    best[i][j][k] = -1;
                }
            }
        }
        
        best[0] = adjacencyMatrix;
        int numVertices = adjacencyMatrix.length;
        
        for (int k = 0; k < numVertices; k++)
        {
            for (int u = 0; u < numVertices; u++)
            {
                for (int v = 0; v < numVertices; v++)
                {
                    best[k][u][v] = best[k - 1][u][v];
                    
                    if ((best[k - 1][u][k] + best[k - 1][k][v]) < best[k][u][v])
                    {
                        best[k][u][v] = best[k - 1][u][k] + best[k - 1][k][v];
                        path[u][v] = k;
                    }
                }
            }
        }
        
        return best[n];
    }
    
    private double[][] makeWeightedAdjacencyMatrix(Road roads[])
    {
        // defaults to being filled with 0's by the Java language spec
        double adjacencyMatrix[][] = new double[roads.length][roads.length];
        
        for (Road road : roads)
        {
            adjacencyMatrix[road.start()][road.end()] = road.length();
            adjacencyMatrix[road.end()][road.start()] = road.length();
        }
        
        printMatrix(adjacencyMatrix);
        
        return adjacencyMatrix;
    }
    
    private void printMatrix(double matrix[][])
    {
        System.out.println("----");
        for (double j[] : matrix)
        {
            for (double i : j)
            {
                System.out.print(i);
            }
            System.out.println("");
        }
    }

}
