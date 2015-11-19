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
    
    public float[][] solve(String fileName)
    {
        Road roads[] = null;// parseInputFile(fileName);
        
        float adjacencyMatrix[][] = makeWeightedAdjacencyMatrix(roads);
        
        float best[][] = floydWarshall(adjacencyMatrix);
        
        return best;
    }
    
    private float[][] floydWarshall(float adjacencyMatrix[][])
    {
        int n = adjacencyMatrix.length;
        float best[][][] = new float[n][n][n];
        int path[][] = new int[n][n];
        
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
    
    private float[][] makeWeightedAdjacencyMatrix(Road roads[])
    {
        // defaults to being filled with 0's by the Java language spec
        float adjacencyMatrix[][] = new float[roads.length][roads.length];
        
        for (Road road : roads)
        {
            adjacencyMatrix[road.start()][road.end()] = road.length();
            adjacencyMatrix[road.end()][road.start()]++;
        }
        
        printMatrix(adjacencyMatrix);
        
        return adjacencyMatrix;
    }
    
    private void printMatrix(float matrix[][])
    {
        System.out.println("----");
        for (float j[] : matrix)
        {
            for (float i : j)
            {
                System.out.print(i);
            }
            System.out.println("");
        }
    }
}
