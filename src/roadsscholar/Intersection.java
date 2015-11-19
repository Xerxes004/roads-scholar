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

import java.util.ArrayList;

public class Intersection 
{
    public Intersection(int intersection)
    {
        this.intersection = intersection;
        this.roads = new ArrayList<>();
        this.roads.clear();
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
