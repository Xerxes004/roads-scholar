
package roadsscholar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author wes
 */
public class RoadsScholar
{
    public RoadsScholar()
    {
        this.cities = new City[0];
        this.signs = new Sign[0];
        this.numIntersects = 0;
        this.roads = new Road[0];
    }
    
    private City cities[];
    private Sign signs[];
    private int numIntersects;
    private Road roads[];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        RoadsScholar problem = new RoadsScholar();
        problem.solve("input.txt");

    }

    //Method to parse the input file
    private boolean parseInput(String input)
    {
        try
        {
            Scanner in = new Scanner(new File(input));
            
            if (in.hasNextInt())
            {
                this.numIntersects = in.nextInt();
            }
            else
            {
                return false;
            }
            
            if (in.hasNextInt())
            {
                this.roads = new Road[in.nextInt()];
            }
            else
            {
                return false;
            }
            
            if (in.hasNextInt())
            {
                this.cities = new City[in.nextInt()];
            }
            else
            {
                return false;
            }

            for (int i = 0; i < this.roads.length; i++)
            {
                int start = in.nextInt();
                int end = in.nextInt();
                double length = in.nextDouble();
                
                this.roads[i] = new Road(start, end, length);
            }

            for (int i = 0; i < this.cities.length; i++)
            {
                int intersectionNum = in.nextInt();
                String name = in.next();
                
                this.cities[i] = new City(intersectionNum, name);
            }

            if (in.hasNextInt())
            {
                this.signs = new Sign[in.nextInt()];
            }

            for (int i = 0; i < this.signs.length; i++)
            {
                int start = in.nextInt();
                int end = in.nextInt();
                double length = in.nextDouble();
                
                this.signs[i] = new Sign(start, end, length);
            }
        }
        catch (FileNotFoundException e)
        {
            System.out.println(e.getMessage());
        }

        return true;
    }

    public Double[][] solve(String fileName)
    {
        if (!parseInput(fileName))
        {
            return null;
        }

        Double adjacencyMatrix[][] = makeAdjacencyMatrix();
        Integer predMatrix[][] = makePredecessorMatrix(adjacencyMatrix);

        Double best[][] = floydWarshall(adjacencyMatrix);

        return best;
    }

    private Double[][] floydWarshall(Double adjacencyMatrix[][])
    {
        int n = adjacencyMatrix.length;
        Double best[][][] = new Double[n][n][n];
        Integer path[][] = new Integer[n][n];
        for (int i = 0; i < adjacencyMatrix.length; i++)
        {
            for (int j = 0; i < adjacencyMatrix.length; i++)
            {
                path[i][j] = null;

                for (int k = 0; k < adjacencyMatrix.length; k++)
                {
                    best[i][j][k] = null;
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

    private Double[][] makeAdjacencyMatrix()
    {
        Double matrix[][] = new Double[numIntersects][numIntersects];
        for (int i = 0; i < matrix.length; i++)
        {
            for (int j = 0; j < matrix.length; j++)
            {
                matrix[i][j] = null;
            }
        }
        
        System.out.println(roads.length + " roads");
        
        for (int intersection = 0; intersection < numIntersects; intersection++)
        {
            for (Road adjoiningRoad : this.roads)
            {
                if (intersection == adjoiningRoad.start())
                {
                    matrix[intersection][adjoiningRoad.end()] = adjoiningRoad.length();
                    matrix[adjoiningRoad.end()][intersection] = adjoiningRoad.length();
                }
            }
        }

        printMatrix("Adjecency", matrix);

        return matrix;
    }
    
    private Integer[][] makePredecessorMatrix(Double adjMatrix[][])
    {
        Integer pMatrix[][] = new Integer[adjMatrix.length][adjMatrix.length];
        
        for (int i = 0; i < pMatrix.length; i++)
        {
            for (int j = 0; j < pMatrix.length; j++)
            {
                pMatrix[i][j] = null;
                
                if (adjMatrix[i][j] != null)
                {
                    pMatrix[i][j] = j;
                }
            }
        }
        
        printMatrix("Predecessor", pMatrix);
        
        return pMatrix;
    }

    private void printMatrix(Integer matrix[][])
    {
        System.out.println("----");
        
        String format = "%-3s";
        System.out.printf(format, " ");
        for (int i = 0; i < matrix.length; i++)
        {
            System.out.printf(format, i);
        }
        System.out.println("");
        
        int k = 0;
        for (Integer j[] : matrix)
        {
            System.out.printf(format, k++);
            
            for (Integer i : j)
            {
                String s = "";
                s += (i == null) ? "-" : (int)Math.ceil(i);
                System.out.printf(format, s);
            }
            System.out.println("");
        }
    }
    
    private void printMatrix(Double matrix[][])
    {
        Integer intArray[][] = new Integer[matrix.length][matrix.length];
        for(int i = 0; i < intArray.length; i++)
        {
            for (int j = 0; j < intArray.length; j++)
            {
                intArray[i][j] = (matrix[i][j] != null) 
                                 ? (int)Math.ceil(matrix[i][j]) 
                                 : null;
            }
        }
        printMatrix(intArray);
    }
    
    private void printMatrix(String title, Double matrix[][])
    {
        System.out.println(title);
        printMatrix(matrix);
    }
    
    private void printMatrix(String title, Integer matrix[][])
    {
        System.out.println(title);
        printMatrix(matrix);
    }

}
