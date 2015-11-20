/**
 * This class encapsulates a road.
 * 
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: Road.java 
 * Created: 15 November 2015
 *
 * Copyright 2015 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 * 
 * Description: This class encapsulates a road. Roads have a start intersection,
 * end intersection, and length, with accessor methods for each.
 */

package roadsscholar;

public class Road
{
    public Road(int startIntxn, int endIntxn, Double length)
    {
        this.startPoint = startIntxn;
        this.endPoint = endIntxn;
        this.length = length;
    }
    
    private final int startPoint;
    private final int endPoint;
    private final Double length;
    
    public int start()
    {
        return this.startPoint;
    }
    public int end()
    {
        return this.endPoint;
    }  
    public double length()
    {
        return this.length;
    } 
}
