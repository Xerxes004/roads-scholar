/**
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: RSSolution.java Created: 27 October 2015
 *
 * Copyright Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: This class encapsulates an answer to the Roads Scholar problem
 * and allows the user to get access to the elements.
 */

package roadsscholar;

public class RSSolution
{
    private final Double[][] answer;
    private final Integer[][] predMatrix;

    public RSSolution(Double[][] answer, Integer[][] predMatrix)
    {
        this.answer = answer;
        this.predMatrix = predMatrix;
    }
    
    public Double[][] answer()
    {
        return this.answer;
    }

    public Integer[][] predMatrix()
    {
        return this.predMatrix;
    }
}
