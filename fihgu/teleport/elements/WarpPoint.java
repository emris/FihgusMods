package fihgu.teleport.elements;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.Location;
import fihgu.core.io.SaveFile;

public class WarpPoint
{
	public String name;
	public Location location;
	
	private static SaveFile warpPointsSaveFile = new SaveFile("warpPoints.txt","./fihgu/teleport/");
	private static SaveFile homesSaveFile = new SaveFile("homes.txt","./fihgu/teleport/");
	
	public static ArrayList<WarpPoint> warpPoints = new ArrayList<WarpPoint>();
	public static ArrayList<WarpPoint> homes = new ArrayList<WarpPoint>();
	
	public WarpPoint(EntityPlayerMP player, String name)
	{
		this.name = name;
		location = new Location(player);
	}
	
	public WarpPoint(String name, Location location)
	{
		this.name = name;
		this.location = location;
	}
	
	public static WarpPoint getHome(String name)
	{
		WarpPoint target = new WarpPoint(name,null);
		if(homes.contains(target))
			return homes.get(homes.indexOf(target));
		return null;
	}
	
	public static WarpPoint getWarpPoint(String name)
	{
		WarpPoint target = new WarpPoint(name,null);
		if(warpPoints.contains(target))
		{
			return warpPoints.get(warpPoints.indexOf(target));
		}
		return null;
	}
	
	public static void setHome(EntityPlayerMP player)
	{
		Location location = new Location(player);
		WarpPoint home = new WarpPoint(player.username, location);
		
		if(homes.contains(home))
			homes.remove(home);
		homes.add(home);
		
		if(location.dimension == 0)
			location.setSpawn(player);
		
		saveAll();
	}
	
	public static void setWarpPoint(EntityPlayerMP player, String name)
	{
		Location location = new Location(player);
		WarpPoint warpPoint = new WarpPoint(name, location);
		
		if(warpPoints.contains(warpPoint))
			warpPoints.remove(warpPoint);
		warpPoints.add(warpPoint);
		
		saveAll();
	}
	
	/**
	 * @return true on success, false when there's no WarpPoint with this name.
	 */
	public static boolean delWarpPoint(String name)
	{
		WarpPoint warpPoint = new WarpPoint(name, null);
		if(warpPoints.contains(warpPoint))
		{
			warpPoints.remove(warpPoint);
			saveAll();
			return true;
		}
		return false;
	}
	
	public static void loadAll()
	{
		warpPointsSaveFile.load();
		warpPoints.clear();
		for(String info : warpPointsSaveFile.data)
			warpPoints.add(new WarpPoint(info));
		
		homesSaveFile.load();
		homes.clear();
		for(String info : homesSaveFile.data)
			homes.add(new WarpPoint(info));
	}
	
	public static void saveAll()
	{
		warpPointsSaveFile.clear();
		for(WarpPoint warpPoint : warpPoints)
			warpPointsSaveFile.data.add(warpPoint.toString());
		warpPointsSaveFile.save(false);
		
		homesSaveFile.clear();
		for(WarpPoint home : homes)
			homesSaveFile.data.add(home.toString());
		homesSaveFile.save(false);
	}
	
	/**
	 * used to rebuild from string
	 */
	public WarpPoint(String info) 
	{
		try
		{
			this.name = info.substring(0,info.indexOf("("));
			String locationInfo = info.substring(info.indexOf("(") + 1, info.indexOf(")"));
			this.location = new Location(locationInfo);
		}
		catch(Exception e)
		{
			System.err.println("Exception on attemping to rebuild WarpPoint from String.");
			name = "Error";
			location = new Location(0,0,0,0);
		}
	}
	
	/**
	 * return a describing String that can be used to rebuild a WarpPoint
	 */
	public String toString()
	{
		if(location == null)
			return "";
		
		return name + "(" + location.toString() + ")";
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof WarpPoint)
			return name.equals(((WarpPoint)o).name);
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}
	
	/**
	 * dummy constructor used to create a empty instance
	 */
	private WarpPoint(String name, Object dummy)
	{
		this.name = name;
	}
}
