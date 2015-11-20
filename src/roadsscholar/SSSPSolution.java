/**
 * This class encapsulates the solution given by the Floyd-Warshall algorithm
 * to the All Source Shortest Path problem.
 * 
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: SSSPSolution.java 
 * Created: 15 November 2015
 *
 * Copyright 2015 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 * 
 * Description: This encapsulates the result of the Floyd-Warshall algorithm.
 * This class is templated because I don't use templates enough in my
 * computer science classes.
 */

package roadsscholar;

public class SSSPSolution
{
    public SSSPSolution(Double[][] answer, Integer[][] predMatrix)
    {
        this.answer = answer;
        this.predMatrix = predMatrix;
    }
    
    private final Double[][] answer;
    private final Integer[][] predMatrix;
    
    public Double[][] answer()
    {
        return this.answer;
    }
    public Integer[][] predMatrix()
    {
        return this.predMatrix;
    }
}
