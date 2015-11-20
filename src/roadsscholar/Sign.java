/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
