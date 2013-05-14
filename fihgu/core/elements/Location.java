package fihgu.core.elements;

import net.minecraft.entity.player.EntityPlayerMP;

/**
 * contain a chunk location x,y,z
 * and a exact position posX,posY,posZ
 *
 * z and posZ represents height.
 */
public class Location 
{
	public int x,y,z;
	public double posX,posY,posZ;
	public int dimension;
	
	public Location(int x, int y, int z, int dimension)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		
		this.dimension = dimension;
	}
	
	public Location(double posX, double posY, double posZ, int dimension)
	{
		init(posX, posY, posZ, dimension);
	}
	
	/**
	 * construct the location the player is currently at
	 */
	public Location(EntityPlayerMP player)
	{
		init(player.posX, player.posZ, player.posY, player.dimension);
	}
	
	/**
	 * construct a location from another location's toString().
	 */
	public Location(String info)
	{
		String[] part = info.split(" ");
		
		init(Double.parseDouble(part[0]), Double.parseDouble(part[1]), Double.parseDouble(part[2]), Integer.parseInt(part[3]));
	}
	
	private void init(double posX, double posY, double posZ, int dimension)
	{
		this.x = toChunkLocation(posX);
		this.y = toChunkLocation(posY);
		this.z = toChunkLocation(posZ);
		
		this.posX = posX;
		this.posY = posY;
		this.posZ = posZ;
		
		this.dimension = dimension;
	}

	
	/**
	 * floor then cast to int.
	 */
	public static int toChunkLocation(double pos)
	{
		return (int)Math.floor(pos);
	}
	
	public String toString()
	{
		return posX + " " + posY + " " + posZ + " " + dimension;
	}
}
