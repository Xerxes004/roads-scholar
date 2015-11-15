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
public class Road
{
    public Road(int startPoint, int endPoint, float length)
    {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.length = length;
    }
    
    private final int startPoint;
    private final int endPoint;
    private final float length;
    
    public int start()
    {
        return this.startPoint;
    }
    public int end()
    {
        return this.endPoint;
    }  
    public float length()
    {
        return this.length;
    } 
}
