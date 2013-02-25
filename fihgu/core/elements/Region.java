package core.elements;

public class Region 
{
	public Location point1,point2;
	
	public Region(Location point1, Location point2)
	{
		this.point1 = point1;
		this.point2 = point2;
	}
	
	protected Region()
	{
	}
	
	/**
	 * @return if the region contains the given location
	 */
	public boolean contains(Location location)
	{
		if(location.dimension != point1.dimension)
			return false;
		
		if(point1.x < point2.x)
		{
			if(location.x < point1.x || location.x > point2.x)
				return false;
		}
		else
		{
			if(location.x > point1.x || location.x < point2.x)
				return false;
		}
		
		if(point1.y < point2.y)
		{
			if(location.y < point1.y || location.y > point2.y)
				return false;
		}
		else
		{
			if(location.y > point1.y || location.y < point2.y)
				return false;
		}
		
		if(point1.z < point2.z)
		{
			if(location.z < point1.z || location.z > point2.z)
				return false;
		}
		else
		{
			if(location.z > point1.z || location.z < point2.z)
				return false;
		}
		
		return true;
	}
	
	/**
	 * @return if the region contains part of the argument region.
	 */
	public boolean contains(Region region)
	{
		return contains(region.point1) || contains(region.point2);
	}
	
	/**
	 * @return if the region contains all of the argument region.
	 */
	public boolean containsFull(Region region)
	{
		return contains(region.point1) && contains(region.point2);
	}
}
