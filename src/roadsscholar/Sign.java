/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roadsscholar;

import java.util.ArrayList;
import java.util.List;

public class Sign extends Road
{
    public Sign(int startPoint, int endPoint, Double length)
    {
        super(startPoint, endPoint, length);
        
        this.distanceInfo = new ArrayList<>();
    }
    
    private final List<CityDistanceTuple> distanceInfo;
    
    public void addInfo(String name, int distance)
    {
        for (int i = 0; i < distanceInfo.size(); i++)
        {
            if (distanceInfo.get(i).distance() > distance)
            {
                this.distanceInfo.add(i, new CityDistanceTuple(name, distance));
            }
        }
    }
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
        
        return info;
    }
}
