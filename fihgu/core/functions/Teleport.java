package fihgu.core.functions;

import fihgu.core.elements.CommandBase;

public class Teleport LoginCommand extends CommandBase{
	
	public static ComandBase instance;
	
	public Teleport(){
		name = "Teleport";
		instance = this;
	}
	
	public void teleportToPlayer(Player from, Player to){
		
	}
	
	public void teleportToLoc(Player who, int x, int y, int z){
		
	}
	
	public void teleportToLoc(Player who, int x, int z){
		
	}
	
	public void teleportToSpawn(Player who){
		
	}
}
