/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roadsscholar;

/**
 *
 * @author wes
 */
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
