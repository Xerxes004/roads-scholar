
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
        this.numIntxns = 0;
        this.roads = new Road[0];
    }

    private City cities[];
    private Sign signs[];
    private int numIntxns;
    private Road roads[];

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        RoadsScholar problem = new RoadsScholar();
        RSSolution solution = problem.solve("input.txt");
        problem.findSignInfo(solution.answer(), solution.predMatrix());
        for(int i = 0; i < problem.signs.length; i ++)
        {
            System.out.println(problem.signs[i].getInfo());
        }
        
        problem.printMatrix("Best answer", solution.answer());
        problem.printMatrix("Final Predecessor Matrix", solution.predMatrix());
    }

    /**
     * Parses the file specified.
     * @param fileName the name of the input file
     * @return whether the method failed or not
     * Note: this method assumes that the input file is correct
     */
    private boolean parseInput(String fileName)
    {
        try
        {
            Scanner in = new Scanner(new File(fileName));

            this.numIntxns = in.nextInt();
            this.roads = new Road[in.nextInt()];
            this.cities = new City[in.nextInt()];
            
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
        // lazy catch statement
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * 
     * @param fileName
     * @return 
     */
    public RSSolution solve(String fileName)
    {
        if (!parseInput(fileName))
        {
            return null;
        }

        Double adjacencyMatrix[][] = makeAdjacencyMatrix();
        Integer predMatrix[][] = makePredecessorMatrix(adjacencyMatrix);

        RSSolution solution = floydWarshall(adjacencyMatrix, predMatrix);
        
        return solution;
    }

    private RSSolution floydWarshall(Double[][] adjMatrix, Integer[][] predMatrix)
    {
        int n = adjMatrix.length;
        Double best[][][] = new Double[2][n][n];
        Integer pred[][][] = new Integer[2][n][n];

        best[0] = adjMatrix;
        best[1] = adjMatrix;

        pred[0] = predMatrix;
        pred[1] = predMatrix;

        int numVertices = adjMatrix.length;
        int solnIndex = 0;

        for (int k = 0; k < numVertices; k++)
        {
            for (int u = 0; u < numVertices; u++)
            {
                for (int v = 0; v < numVertices; v++)
                {
                    // we only need to keep two matrices in memory and swap
                    // between them. i and j alternate.
                    int i = k % 2;
                    int j = (i == 0) ? 1 : 0;

                    best[i][u][v] = best[j][u][v];

                    // null represents infinity
                    if (best[i][u][k] != null && best[i][k][v] != null)
                    {
                        // if this spot is infinite, anything is better
                        if (best[i][u][v] == null)
                        {
                            best[i][u][v] = best[j][u][k] + best[j][k][v];
                            pred[i][u][v] = k;
                        }
                        else if 
                            ((best[j][u][k] + best[j][k][v]) < best[i][u][v])
                        {
                            best[i][u][v] = best[j][u][k] + best[j][k][v];
                            pred[i][u][v] = k;
                        }

                        // Stores the last i value so that the correct answers 
                        // can be sent back to the user.
                        if (v == numVertices - 1)
                        {
                            solnIndex = i;
                        }
                    }
                }
            }
        }

        return new RSSolution(best[solnIndex], pred[solnIndex]);
    }

    /**
     * 
     * @return the adjacency matrix of roads
     * Note: this method works only for undirected graphs
     */
    private Double[][] makeAdjacencyMatrix()
    {
        Double[][] matrix = new Double[this.numIntxns][this.numIntxns];
        
        for (Double[] matrix1 : matrix)
        {
            for (int j = 0; j < matrix.length; j++)
            {
                matrix1[j] = null;
            }
        }

        for (int intxn = 0; intxn < this.numIntxns; intxn++)
        {
            for (Road road : this.roads)
            {
                if (intxn == road.start())
                {
                    matrix[intxn][road.end()] = road.length();
                    matrix[road.end()][intxn] = road.length();
                }
                if (intxn == road.end())
                {
                    matrix[intxn][road.end()] = 0.0;
                    matrix[road.end()][intxn] = 0.0;
                }
            }
        }

        return matrix;
    }

    /**
     * Makes a predecessor matrix from an adjacency matrix.
     *
     * @param aMatrix the adjacency matrix of the graph
     * @return the predecessor matrix of the adjacency matrix
     */
    private Integer[][] makePredecessorMatrix(Double[][] aMatrix)
    {
        Integer pMatrix[][] = new Integer[aMatrix.length][aMatrix.length];

        for (int i = 0; i < pMatrix.length; i++)
        {
            for (int j = 0; j < pMatrix.length; j++)
            {
                pMatrix[i][j] = null;

                if (aMatrix[i][j] != null)
                {
                    pMatrix[i][j] = i;
                }
                if (i == j)
                {
                    pMatrix[i][j] = 0;
                }
            }
        }

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
                s += (i == null) ? "-" : (int) Math.ceil(i);
                System.out.printf(format, s);
            }
            System.out.println("");
        }
        System.out.println("");
    }

    private void printMatrix(Double matrix[][])
    {
        Integer intArray[][] = new Integer[matrix.length][matrix.length];
        for (int i = 0; i < intArray.length; i++)
        {
            for (int j = 0; j < intArray.length; j++)
            {
                intArray[i][j] = (matrix[i][j] != null) ?
                     (int) Math.ceil(matrix[i][j]) :
                     null;
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

    private void findSignInfo(Double[][] distances, Integer[][] shortest) 
    {
        for(int i = 0; i < this.signs.length; i++)
        {
            Sign current = this.signs[i];
            for(int j = 0; j < this.cities.length; j++)
            {
                int cityNum = this.cities[j].intersection();
                if(current.start() != cityNum) 
                {
                    if(shortest[current.start()][cityNum] == current.end())
                    {
                        Double totalDist = distances[current.start()][cityNum] - current.length();
                        long rounded = Math.round(totalDist);
                        this.signs[i].addInfo(this.cities[j].name(), (int)rounded);
                    }
                }
            }
        }
    }
}
