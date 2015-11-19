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
