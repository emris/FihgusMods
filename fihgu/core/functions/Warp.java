package fihgu.core.functions;

import fihgu.core.elements.Location;
import fihgu.core.io.ConfigFile;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;



public class Warp {
	
	private String fileLoc = "./fihgu/teleport";
	private ConfigFile warps = new ConfigFile("warps.txt", fileLoc);
	private ConfigFile homes = new ConfigFile("homes.txt", fileLoc);
	
	public Warp(){
		
	}
	//////////////////////////////////////////////////////////////////////
	// Functions for teleporting player
	//////////////////////////////////////////////////////////////////////
	
	public boolean warpTo(EntityPlayerMP who, String name)
	{
		Location loc = this.getWarp(name);
		if(loc!=null){
			who.setPositionAndUpdate(loc.posX+0.5, loc.posZ+0.5, loc.posY);
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
	
	//////////////////////////////////////////////////////////////////////
	// Misc functions
	//////////////////////////////////////////////////////////////////////
	
}
