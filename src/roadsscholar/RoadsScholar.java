package roadsscholar;

public class RoadsScholar
{
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
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
