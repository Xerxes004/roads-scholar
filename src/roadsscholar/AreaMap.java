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

public class AreaMap
{
    public AreaMap(Road roads[], City cities[], Intersection intersections[], 
                   Sign signs[])
    {
        this.roads = roads;
        this.cities = cities;
        this.intersections = intersections;
        this.signs = signs;
    }
    
    private final Road roads[];
    private final City cities[];
    private final Intersection intersections[];
    private final Sign signs[];

}
