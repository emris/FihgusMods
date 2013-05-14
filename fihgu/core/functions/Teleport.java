package fihgu.core.functions;

import fihgu.core.elements.CommandBase;

public class Teleport LoginCommand extends CommandBase{
	
	public static ComandBase instance;
	
	public Teleport(){
		name = "Teleport";
		instance = this;
	}
	
	public void teleportToPlayer(EntityPlayerMP from, EntityPlayerMP to){
		ChunkCoordinates toWhere = to.getPlayerCoordinates();
		
		from.setPositionAndUpdate(toWhere.posX,toWhere.posY,toWhere.posZ);
	}
	
	public void teleportToLoc(EntityPlayerMP who, int x, int y, int z){
		who.setPositionAndUpdate(x,y,z);
	}
	
	public void teleportToLoc(EntityPlayerMP who, int x, int z){
		int y;
		
		
		who.setPositionAndUpdate(x,y,z);
	}
	
	public void teleportToSpawn(EntityPlayerMP who){
		
	}
}
