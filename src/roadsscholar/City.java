/**
 * This class abstracts the idea of City, which inherits from an intersection.
 * 
 * @author Wesley Kelly, James Von Eiff
 * @version 1.0
 *
 * File: City.java 
 * Created: 15 November 2015
 *
 * Copyright 2015 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 * 
 * Description: A City inherits from Intersection because a city is always
 * place at an intersection. The only difference is that a City has a name,
 * and an intersection doesn't.
 */

package roadsscholar;

public class City extends Intersection
{
    public City(int intersection, String name)
    {
        super(intersection);
        this.name = name;
    }
    
    private final String name;
    
    public String name()
    {
        return this.name;
    }
}
