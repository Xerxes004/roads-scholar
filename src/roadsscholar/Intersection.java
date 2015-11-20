/**
 * This class abstracts the idea of an intersection.
 * 
 * @author Wesley Kelly, James Von Eiff
 * @version 1.0
 *
 * File: Intersection.java 
 * Created: 15 November 2015
 *
 * Copyright 2015 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 * 
 * Description: An intersection is defined by an integer value and has an
 * arbitrary number of roads attached to it.
 */

package roadsscholar;

import java.util.ArrayList;

public class Intersection 
{
    public Intersection(int intersection)
    {
        this.intersection = intersection;
        this.roads = new ArrayList<>();
    }

    private final int intersection;
    private final ArrayList<Road> roads;
    
    public int intersection() 
    {
        return this.intersection;
    }
    public void addRoad(Road newRoad)
    {
        this.roads.add(newRoad);
    }
}
