/**
 * This class abstracts the idea of Sign, which inherits from Road.
 * 
 * @author Wesley Kelly
 * @version 1.0
 *
 * File: Sign.java 
 * Created: 15 November 2015
 *
 * Copyright 2015 Cedarville University, its Computer Science faculty, and the
 * authors. All rights reserved.
 * 
 * Description: A Sign stores CityDistanceTuples as its data. Signs inherit
 * from Roads because they live on top of roads and have a start and end point,
 * the only difference being that a Sign has a different length than the Road
 * it is on, and also stores information.
 */

package roadsscholar;

import java.util.ArrayList;

public class Sign extends Road
{
    public Sign(int startPoint, int endPoint, Double length)
    {
        super(startPoint, endPoint, length);
        this.distanceInfo = new ArrayList<>();
    }
    
    private final ArrayList<CityDistanceTuple> distanceInfo;
    
    /**
     * Adds city and distance info to a sign, insertion sorting on the way in.
     * 
     * @param name the name of the city being added to the sign
     * @param distance the distance to the named city
     */
    public void addInfo(String name, int distance)
    {
        // if the sign is empty, add it directly
        if (this.distanceInfo.isEmpty())
        {
            this.distanceInfo.add(new CityDistanceTuple(name, distance));
        }
        // if there are cities already on the sign, insertion sort
        else
        {
            int size = distanceInfo.size();
            for (int i = 0; i < size; i++)
            {
                if (distanceInfo.get(i).distance() > distance)
                {
                    this.distanceInfo.add
                        (i, new CityDistanceTuple(name, distance));
                }
                // if the distances are the same, compare the strings
                else if (distanceInfo.get(i).distance() == distance)
                {
                    int comp = distanceInfo.get(i).name().compareTo(name);
                    
                    if (comp == -1)
                    {
                        this.distanceInfo.add
                            (i, new CityDistanceTuple(name, distance));
                    } 
                    else if (comp == 1)
                    {
                        this.distanceInfo.add
                            (i + 1, new CityDistanceTuple(name, distance));
                    }
                    // else they're the same and we don't need to re-insert it
                }
                else if (i == distanceInfo.size() - 1)
                {
                    this.distanceInfo.add
                        (new CityDistanceTuple(name, distance));
                }
            }
        }
    }
    
    /**
     * Gets the sign info as a string.
     * @return the sign info
     */
    public String getInfo()
    {
        String info = "";
        
        for (CityDistanceTuple c : this.distanceInfo)
        {
            info += c.name();
            for (int i = 0; i < 20 - c.name().length(); i++)
            {
                info += " ";
            }
            info += c.distance() + "\n";
        }
        
        return info + "\n";
    }
}
