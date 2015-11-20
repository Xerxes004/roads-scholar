/**
 * This class solves the Roads Scholar problem.
 * 
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: RoadsScholar.java 
 * Created: 15 November 2015
 *
 * Copyright 2015 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 * 
 * Description: This class uses the Floyd-Warshall algorithm to solve the SSSP
 * problem. The answer to the solve method returns the signs being place on the
 * map.
 */

package roadsscholar;

import java.io.File;
import java.util.Scanner;

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
    private SSSPSolution solution;

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
     * Solves the input file.
     *
     * @param fileName name of input file to solve
     * @return a String version of the sign info generated from the problem,
     * null if the input file fails.
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
    
     /**
     * Prints a matrix with a title. Generic because it saves work from
     * overloading.
     * 
     * @param <J> the type of element to print
     * @param title title of the matrix
     * @param matrix matrix of J[n][n]
     */
    public <J> void printMatrix(String title, J matrix[][])
    {
        System.out.println(title + "\n----");
        printMatrix(matrix);
    }
    
    /**
     * Prints out an array to the console.
     * 
     * @param <T> the type of element to print
     * @param matrix matrix of T[n][k]
     */
    public <T> void printMatrix(T matrix[][])
    {
        int longestString = 0;
        
        for (T[] m : matrix)
        {
            for (T j : m)
            {
                if (j.toString().length() > longestString)
                {
                    longestString = j.toString().length();
                }
            }
        }
        String matrixLength = "";
        matrixLength += matrix.length;
        
        if (longestString < matrixLength.length())
        {
            longestString = matrix.length;
        }
        
        // limit the length of a string to 4 characters
        longestString = (longestString > 4) ? 4 : longestString;
        
        String format = "%-" + (longestString + 1) + "." + longestString + "s";
        
        System.out.printf(format, " ");
        for (int i = 0; i < matrix.length; i++)
        {
            System.out.printf(format, i);
        }
        System.out.println("");

        int k = 0;

        for (T j[] : matrix)
        {
            System.out.printf(format, k++);

            for (T i : j)
            {
                String s = "";
                s += (i == null) ? "-" : i;
                System.out.printf(format, s);
            }
            System.out.println("");
        }
        System.out.println("");
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
     * An implementation of the Floyd-Warshall algorithm.
     * 
     * @param aMatrix the initial Double[][] adjacency matrix of the graph
     * @param pMatrix the initial Integer[][] predecessor matrix of the graph
     * @return the SSSPSolution form of the solution to the inputs
     */
    private SSSPSolution floydWarshall(Double[][] aMatrix, Integer[][] pMatrix)
    {
        Double  best[][][] = new  Double[2][this.numIntxns][this.numIntxns];
        Integer pred[][][] = new Integer[2][this.numIntxns][this.numIntxns];

        best[0] = aMatrix;
        best[1] = aMatrix;

        pred[0] = pMatrix;
        pred[1] = pMatrix;

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

                    // note that null represents infinity
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
                        if (v == this.numIntxns - 1)
                        {
                            solnIndex = i;
                        }
                    }
                }
            }
        }

        return new SSSPSolution(best[solnIndex], pred[solnIndex]);
    }

    /**
     * Makes an adjacency matrix using the internally stored roads.
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

        // populate the matrix
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
    
    /**
     * Populates each sign with its correct information.
     * 
     * @param dists the array of shortest distances
     * @param pMatrix the predecessor matrix
     * @return sign info for all signs in the problem
     */
    private String findSignInfo(Double[][] dists, Integer[][] pMatrix)
    {
        for (Sign sign : this.signs)
        {
            for (City city : this.cities)
            {
                if (sign.start() != city.intersection())
                {
                    int intxn = city.intersection();
                    boolean pathEnd = false;
                    
                    while (!pathEnd)
                    {
                        // if we find the last element, add the info to the sign
                        if (pMatrix[sign.start()][intxn] == sign.end())
                        {
                            Double totalDist = 
                                dists[sign.start()][city.intersection()] - sign.length();
                            double rounded = Math.round(totalDist);
                            sign.addInfo(city.name(), (int) rounded);
                            pathEnd = true;
                        }
                        // if we find ourself, we're done
                        else if (pMatrix[sign.start()][intxn] == sign.start())
                        {
                            pathEnd = true;
                        }
                        // if we're not done, check the next intersection
                        else
                        {
                            intxn = pMatrix[sign.start()][intxn];
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
