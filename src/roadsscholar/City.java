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
public class City
{
    public City(int intersection, String name)
    {
        this.intersection = intersection;
        this.name = name;
    }
    
    private final int intersection;
    private final String name;

    public int intersection()
    {
        return this.intersection;
    }
    public String name()
    {
        return this.name;
    }
}
