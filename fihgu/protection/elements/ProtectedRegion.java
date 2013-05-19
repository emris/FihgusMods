package fihgu.protection.elements;

import java.util.ArrayList;
import java.util.List;

import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.elements.Region;
import fihgu.core.functions.Protection;
import fihgu.core.io.SaveFile;

public class ProtectedRegion extends Region
{	
	public SaveFile file;
	
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
	
	private List<String> list;
	
	public ProtectedRegion(String name, Location loc1, Location loc2){
		this.name = name;
		this.point1 = loc1;
		this.point2 = loc2;
		file = new SaveFile(name,"./fihgu/protection/");
		list = file.data;
	}
	
	private void populate(){
		list.clear();
		list.add("CreeperDamage="+creeperDmg);
		list.add("MobSpawn="+mobSpawn);
		list.add("AnimalSpawn="+animalSpawn);
		list.add("Destroy="+destroy);
		list.add("Build="+build);
		list.add("UseItems="+useItems);
		list.add("AccessBlocks="+accessBlocks);
		list.add("FireSpead="+fireSpread);
		list.add("LavaFlow="+lavaFlow);
		list.add("WaterFlow="+waterFlow);
		list.add("PvP="+pvp);
	}
	
	public void save(){
		this.populate();
		file.save(false);
	}
	
	public void load(){
		file.load();
	}
}
