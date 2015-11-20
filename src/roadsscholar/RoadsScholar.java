
package roadsscholar;

import java.io.File;
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
        this.solution = null;
    }

    private City cities[];
    private Sign signs[];
    private int numIntxns;
    private Road roads[];
    private RSSolution solution;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        RoadsScholar problem = new RoadsScholar();

        String signInfo = problem.solve("input.txt");

        System.out.println(signInfo);

        problem.printMatrix("Best answer", problem.answer());
        problem.printMatrix("Final Predecessor Matrix", problem.predMatrix());
    }

    public Double[][] answer()
    {
        return this.solution.answer();
    }

    public Integer[][] predMatrix()
    {
        return this.solution.predMatrix();
    }

    /**
     * Parses the file specified. This method assumes that the input file is
     * correct
     *
     * @param fileName the name of the input file
     * @return whether the method failed or not
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
        // lazy catch statement because computer science is hard
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * Solves the input file.
     *
     * @param fileName name of input file to solve
     * @return the sign
     */
    public String solve(String fileName)
    {
        if (!parseInput(fileName))
        {
            return null;
        }

        Double adjacencyMatrix[][] = makeAdjacencyMatrix();
        Integer predMatrix[][] = makePredecessorMatrix(adjacencyMatrix);

        this.solution = floydWarshall(adjacencyMatrix, predMatrix);

        return findSignInfo(adjacencyMatrix, predMatrix);
    }

    private RSSolution floydWarshall(Double[][] adjMatrix,
                                     Integer[][] predMatrix)
    {
        Double best[][][] = new Double[2][this.numIntxns][this.numIntxns];
        Integer pred[][][] = new Integer[2][this.numIntxns][this.numIntxns];

        best[0] = adjMatrix;
        best[1] = adjMatrix;

        pred[0] = predMatrix;
        pred[1] = predMatrix;

        int solnIndex = 0;

        for (int k = 0; k < this.numIntxns; k++)
        {
            for (int u = 0; u < this.numIntxns; u++)
            {
                for (int v = 0; v < this.numIntxns; v++)
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
                        else if ((best[j][u][k] + best[j][k][v]) < best[i][u][v])
                        {
                            best[i][u][v] = best[j][u][k] + best[j][k][v];
                            pred[i][u][v] = k;
                        }

                        // Stores the last i value so that the correct answers 
                        // can be sent back to the user.
                        if (v == this.numIntxns - 1)
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
     * @return the adjacency matrix of roads Note: this method works only for
     * undirected graphs
     */
    private Double[][] makeAdjacencyMatrix()
    {
        Double[][] matrix = new Double[this.numIntxns][this.numIntxns];

        for (int i = 0; i < this.numIntxns; i++)
        {
            for (int j = 0; j < this.numIntxns; j++)
            {
                // if the indices are the same the distance is 0.0.
                // otherwise, it's infinite.
                matrix[i][j] = (i != j) ? null : 0.0;
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

    private String findSignInfo(Double[][] distances, Integer[][] shortest)
    {
        for (Sign sign : this.signs)
        {
            for (City city : this.cities)
            {
                if (sign.start() != city.intersection())
                {
                    int intersect = city.intersection();
                    boolean pathEnd = false;
                    while (!pathEnd)
                    {
                        if (shortest[sign.start()][intersect] == sign.end())
                        {
                            Double totalDist = distances[sign.start()][city.intersection()] - sign.length();
                            long rounded = Math.round(totalDist);
                            sign.addInfo(city.name(), (int) rounded);
                            pathEnd = true;
                        }
                        else if (shortest[sign.start()][intersect] == sign.start())
                        {
                            pathEnd = true;
                        }
                        else
                        {
                            intersect = shortest[sign.start()][intersect];
                        }
                    }
                }
            }
        }

        String signInfo = "";

        for (Sign sign : this.signs)
        {
            signInfo += sign.getInfo();
        }

        return signInfo;
    }
}
