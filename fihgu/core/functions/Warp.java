package fihgu.core.functions;

import java.util.HashMap;

import fihgu.core.elements.Location;
import fihgu.core.io.ConfigFile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;



public class Warp {
	
	private String fileLoc = "./fihgu/teleport/";
	private ConfigFile warps = new ConfigFile("warps.txt", fileLoc);
	private ConfigFile homes = new ConfigFile("homes.txt", fileLoc);
	private Warp previous = null;
	private HashMap<EntityPlayerMP, Location> playerBackMap = new HashMap<EntityPlayerMP, Location>();
	
	public Warp(){
		
	}
	//////////////////////////////////////////////////////////////////////
	// Functions for teleporting player
	//////////////////////////////////////////////////////////////////////
	
	public boolean warpTo(EntityPlayerMP who, String name)
	{
		Location loc = null;
		loc = this.getWarp(name);
		
		if(loc!=null){
			playerBackMap.put(who, new Location(who));
			who.setPositionAndUpdate(loc.posX+0.5, loc.posZ, loc.posY+0.5);
			return true;
		} else {
			return false;
		}
	}
	public boolean warpHome(EntityPlayerMP who)
	{
		Location loc = null;
		loc = this.getHome(who);
		
		if(loc!=null){
			playerBackMap.put(who, new Location(who));
			who.setPositionAndUpdate(loc.posX+0.5, loc.posZ, loc.posY+0.5);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean goBack(EntityPlayerMP player){
		Location loc;
		if(playerBackMap.containsKey(player)){
			loc = playerBackMap.get(player);
			playerBackMap.put(player, new Location(player));
			player.setPositionAndUpdate(loc.posX,loc.posZ,loc.posY);
			return true;
		} else {
			return false;
		}
	}
	//////////////////////////////////////////////////////////////////////
	// Functions for getting warp information
	//////////////////////////////////////////////////////////////////////

	
	
	//////////////////////////////////////////////////////////////////////
	// Functions for saving file information
	//////////////////////////////////////////////////////////////////////
	
	public boolean newWarp(Location loc, String name){
		warps.load();
		int x,y,z,d;
		String toSave;
		if(!warps.containsKey(name)){
			x = loc.x;
			y = loc.z;
			z = loc.y;
			d = loc.dimension;
			toSave = x+","+y+","+z+","+d;
			warps.get(name, toSave);
			warps.save();
			return true;
		} else{
			return false;
		}
	}
	
	public Location getWarp(String name){
		warps.load();
		Location loc;
		int x,y,z,d;
		
		if(warps.containsKey(name)){
			
			String warp = warps.get(name);
			String[] warpSplit = warp.split(",");
			
			x=Integer.parseInt(warpSplit[0]);
			y=Integer.parseInt(warpSplit[2]);
			z=Integer.parseInt(warpSplit[1]);
			d=Integer.parseInt(warpSplit[3]);
			
			loc = new Location(x,y,z,d);
			
			return loc;
		} else {
			return null;
		}
	}

	public boolean newHome(EntityPlayerMP who){
		homes.load();
		Location loc = new Location(who);
		String toSave = loc.x+","+loc.z+","+loc.y+","+loc.dimension;
		if(!homes.containsKey(who.username)){
			homes.get(who.username, toSave);
			homes.save();
			setRespawn(who);
			return true;
		} else {
			homes.set(who.username, toSave);
			homes.save();
			setRespawn(who);
			return false;
		}
	}	
	
	public Location getHome(EntityPlayerMP who){
		homes.load();
		Location loc;
		int x,y,z,d;
		
		if(homes.containsKey(who.username)){
			
			String warp = homes.get(who.username);
			String[] warpSplit = warp.split(",");
			
			x=Integer.parseInt(warpSplit[0]);
			y=Integer.parseInt(warpSplit[2]);
			z=Integer.parseInt(warpSplit[1]);
			d=Integer.parseInt(warpSplit[3]);
			
			loc = new Location(x,y,z,d);
			
			return loc;
		} else {
			return null;
		}
	}
	
	//////////////////////////////////////////////////////////////////////
	// Misc functions
	//////////////////////////////////////////////////////////////////////
	
	public void setRespawn(EntityPlayerMP who){
		who.setSpawnChunk(new ChunkCoordinates(who.chunkCoordX,who.chunkCoordY,who.chunkCoordZ), true);
	}
}
