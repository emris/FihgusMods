package fihgu.core.functions;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;



public class Warp {
	
	public Warp(){
		
	}
	//////////////////////////////////////////////////////////////////////
	public void teleportToPlayer(EntityPlayerMP from, EntityPlayerMP to){
		ChunkCoordinates toWhere = to.getPlayerCoordinates();
		
		from.setPositionAndUpdate(toWhere.posX,toWhere.posY,toWhere.posZ);
	}
	
	public void teleportToLoc(EntityPlayerMP who, int x, int y, int z){
		who.setPositionAndUpdate(x,y,z);
	}
	
	public void teleportToLoc(EntityPlayerMP who, int x, int z){
		int y = 0;
		
		
		who.setPositionAndUpdate(x,y,z);
	}
	
	public void teleportToSpawn(EntityPlayerMP who){
		
	}
	//////////////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////////////
	
	
	
}
