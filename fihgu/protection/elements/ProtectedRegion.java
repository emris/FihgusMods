package fihgu.protection.elements;

import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.elements.Region;
import fihgu.core.functions.Protection;

public class ProtectedRegion extends Region
{	
	public Member owner;
	public int id;
	public String name;
	public Member[] members;
	
	public boolean creeperDmg = false;
	public boolean mobSpawn = false;
	public boolean animalSpawn = true;
	public boolean destroy = true;
	public boolean build = true;
	public boolean useItems = true;
	public boolean accessBlocks = true;
	public boolean fireSpread = false;
	public boolean lavaFlow = false;
	public boolean waterFlow = false;
	public boolean pvp = false;
	
	public ProtectedRegion(Location loc1, Location loc2){
		this.point1 = loc1;
		this.point2 = loc2;
	}
	
}
