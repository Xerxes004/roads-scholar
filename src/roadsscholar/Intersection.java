/*
 * 
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: GameBoard.java Created: 27 October 2015
 *
 * Copyright Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 *
 * Description: 
 */

package roadsscholar;

public class Intersection 
{
    public Intersection(int intersection)
    {
        this.intersection = intersection;
    }

    public int intersection() 
    {
        return this.intersection;
    }
    
    private final int intersection;
}
