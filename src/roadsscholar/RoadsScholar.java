/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package roadsscholar;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wes
 */
public class RoadsScholar
{
    
    private City[] cities = null;
    private Sign[] signs = null;
    private int numIntersect = 0;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
    }
    
    //Method to parse the input file
    private Road[] parseInput(String input)
    {
        BufferedReader in = null;
        Road allRoads[] = null; 
        try{
            in = new BufferedReader(new FileReader(input));
        } catch (Exception e) {
            System.out.println("File not found");
        }
        int numIn = 0;
        try {
            numIn = in.read();
            this.numIntersect = numIn;
            
            numIn = in.read();
            int numRoads = numIn;
            //Create array with correct size
            allRoads = new Road[numRoads];

            numIn = in.read();
            int numCity = numIn;
            //Create an array with the correct size
            this.cities = new City[numCity];
            
            for(int i = 0; i < numRoads; i++) 
            {
                numIn = in.read();
                int start = numIn;
                numIn = in.read();
                int end = numIn;
                char[] inBuffer = new char[1];
                in.read(inBuffer, 0, 1);
                Double length = Double.valueOf(Character.toString(inBuffer[0]));

                allRoads[i] = new Road(start, end, length);
            }
            
            for(int i = 0; i < numCity; i++) 
            {
                int inter = in.read();
                String name = in.readLine();
                this.cities[i] = new City(inter, name);
            }
            
            int numSign = in.read();
            this.signs = new Sign[numSign];
            for(int i = 0; i < numSign; i++) 
            {
                numIn = in.read();
                int start = numIn;
                numIn = in.read();
                int end = numIn;
                char[] inBuffer = new char[1];
                in.read(inBuffer, 0, 1);
                Double length = Double.valueOf(Character.toString(inBuffer[0]));
                
                this.signs[i] = new Sign(start, end, length);
            }
            
        } catch (IOException ex) {
            System.out.println("Incorrect input");
        }
        
        return allRoads;
    }
    
}
