package fihgu.core.functions;

import java.util.HashMap;

import fihgu.core.elements.Location;
import fihgu.core.io.ConfigFile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;


public class Warp 
{
	
	private String fileLoc = "./fihgu/teleport/";
	private ConfigFile warps = new ConfigFile("warps.txt", fileLoc);
	private ConfigFile homes = new ConfigFile("homes.txt", fileLoc);
	private static HashMap<EntityPlayerMP, Location> playerBackMap = new HashMap<EntityPlayerMP, Location>();
	
	//////////////////////////////////////////////////////////////////////
	// Functions for teleporting player
	//////////////////////////////////////////////////////////////////////
	
	/**
	 * TODO: implement dimension here :)
	 * 
	 * @param exact: use double when it's true;
	 * the basic player to location warp.
	 */
	public void warpTo(EntityPlayerMP player, Location loc, boolean exact)
	{		
		playerBackMap.put(player, new Location(player));
		
		if(!exact)
			player.setPositionAndUpdate(loc.x+0.5, loc.z, loc.y+0.5);
		else
			player.setPositionAndUpdate(loc.posX, loc.posZ, loc.posY);
	}
	
	/**
	 * @param name: the name of target warp point.
	 * player to warp point
	 */
	public boolean warpTo(EntityPlayerMP who, String name)
	{
		Location loc = this.getWarp(name);
		
		if(loc!=null)
		{
			warpTo(who,loc,false);
			return true;
		} 
		else 
		{
			return false;
		}
	}
	
	public boolean warpHome(EntityPlayerMP who)
	{
		Location loc = this.getHome(who);
		
		if(loc!=null)
		{
			warpTo(who,loc,false);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public boolean goBack(EntityPlayerMP player)
	{
		Location loc;
		if(playerBackMap.containsKey(player))
		{
			loc = playerBackMap.get(player);
			warpTo(player,loc,true);		
			return true;
		}
		else 
		{
			return false;
		}
	}
	//////////////////////////////////////////////////////////////////////
	// Functions for getting warp information
	//////////////////////////////////////////////////////////////////////

	
	
	//////////////////////////////////////////////////////////////////////
	// Functions for saving file information
	//////////////////////////////////////////////////////////////////////
	
	public boolean newWarp(Location loc, String name)
	{
		warps.load();
		int x,y,z,d;
		String toSave;
		if(!warps.containsKey(name))
		{
			x = loc.x;
			y = loc.z;
			z = loc.y;
			d = loc.dimension;
			toSave = x+","+y+","+z+","+d;
			warps.get(name, toSave);
			warps.save();
			return true;
		} 
		else
		{
			return false;
		}
	}
	
	public Location getWarp(String name)
	{
		warps.load();
		Location loc;
		int x,y,z,d;
		
		if(warps.containsKey(name))
		{
			
			String warp = warps.get(name);
			String[] warpSplit = warp.split(",");
			
			x=Integer.parseInt(warpSplit[0]);
			y=Integer.parseInt(warpSplit[2]);
			z=Integer.parseInt(warpSplit[1]);
			d=Integer.parseInt(warpSplit[3]);
			
			loc = new Location(x,y,z,d);
			
			return loc;
		}
		else 
		{
			return null;
		}
	}

	public boolean newHome(EntityPlayerMP who)
	{
		homes.load();
		Location loc = new Location(who);
		String toSave = loc.x+","+loc.z+","+loc.y+","+loc.dimension;
		if(!homes.containsKey(who.username))
		{
			homes.get(who.username, toSave);
			homes.save();
			setRespawn(who);
			return true;
		} 
		else 
		{
			homes.set(who.username, toSave);
			homes.save();
			setRespawn(who);
			return false;
		}
	}	
	
	public Location getHome(EntityPlayerMP who)
	{
		homes.load();
		Location loc;
		int x,y,z,d;
		
		if(homes.containsKey(who.username))
		{
			
			String warp = homes.get(who.username);
			String[] warpSplit = warp.split(",");
			
			x=Integer.parseInt(warpSplit[0]);
			y=Integer.parseInt(warpSplit[2]);
			z=Integer.parseInt(warpSplit[1]);
			d=Integer.parseInt(warpSplit[3]);
			
			loc = new Location(x,y,z,d);
			
			return loc;
		} 
		else 
		{
			return null;
		}
	}
	
	//////////////////////////////////////////////////////////////////////
	// Misc functions
	//////////////////////////////////////////////////////////////////////
	
	public void setRespawn(EntityPlayerMP who)
	{
		who.setSpawnChunk(new ChunkCoordinates(who.chunkCoordX,who.chunkCoordY,who.chunkCoordZ), true);
	}
}
