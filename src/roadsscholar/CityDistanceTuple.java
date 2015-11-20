/**
 * This class encapsulates a city name and a distance, and is used to store
 * information in a Sign.
 * 
 * @author Wesley Kelly, James Von Eiff
 * @version 1.0
 *
 * File: CityDistanceTuple.java 
 * Created: 15 November 2015
 *
 * Copyright 2015 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 */

package roadsscholar;

public class CityDistanceTuple
{
    public CityDistanceTuple(String name, int distance)
    {
        this.name = name;
        this.distance = distance;
    }
    
    private final String name;
    private final int distance;
    
    public String name()
    {
        return this.name;
    }
    public int distance()
    {
        return this.distance;
    }
}
