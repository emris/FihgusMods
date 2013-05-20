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
	public List<Member> members;

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

	public ProtectedRegion(String name)
	{
		this.name = name;
		members = new ArrayList<Member>();
		file = new SaveFile(name + ".txt", "./fihgu/protection/regions/");
		file.load();

		list = file.data;
		file.save(false);
	}

	private void populate()
	{
		list.clear();
		list.add("Owner=" + owner.getName());
		list.add("Members={" + getMembers() + "}");
		list.add("Location=" + this.point1.toString() + ":"
				+ this.point2.toString());
		list.add("CreeperDamage=" + creeperDmg);
		list.add("MobSpawn=" + mobSpawn);
		list.add("AnimalSpawn=" + animalSpawn);
		list.add("Destroy=" + destroy);
		list.add("Build=" + build);
		list.add("UseItems=" + useItems);
		list.add("AccessBlocks=" + accessBlocks);
		list.add("FireSpead=" + fireSpread);
		list.add("LavaFlow=" + lavaFlow);
		list.add("WaterFlow=" + waterFlow);
		list.add("PvP=" + pvp);
	}

	public String getMembers()
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < members.size(); i++)
		{
			sb.append(members.get(i).getName());
			if (i < members.size() && members.size() > 0)
				sb.append(",");
		}
		return sb.toString();
	}

	public void addLocationA(Location loc)
	{
		this.point1 = loc;
	}

	public void addLocationB(Location loc)
	{
		this.point2 = loc;
	}

	public Location getLocationA()
	{
		String locations = file.getSingleData("Location");
		String[] locSplit = locations.split(":");
		String[] locationA = locSplit[0].split(" ");

		return new Location(Integer.parseInt(locationA[0]),
				Integer.parseInt(locationA[1]), Integer.parseInt(locationA[2]),
				Integer.parseInt(locationA[3]));
	}

	public Location getLocationB()
	{
		String locations = file.getSingleData("Location");
		String[] locSplit = locations.split(":");
		String[] locationB = locSplit[1].split(" ");

		return new Location(Integer.parseInt(locationB[0]),
				Integer.parseInt(locationB[1]), Integer.parseInt(locationB[2]),
				Integer.parseInt(locationB[3]));
	}

	public void addMember(Player player)
	{
		members.add(new Member(player, this));
	}

	public Member getOwner()
	{
		return owner;
	}

	public void save()
	{
		this.populate();
		file.save(false);
	}

	public void load()
	{
		file.load();
		owner = new Member(new Player(file.getString("Owner")), this);
		point1 = getLocationA();
		point2 = getLocationB();
		creeperDmg = file.getBoolean("CreeperDamage");
		mobSpawn = file.getBoolean("MobSpawn");
		animalSpawn = file.getBoolean("AnimalSpawn");
		destroy = file.getBoolean("Destroy");
		build = file.getBoolean("Build");
		useItems = file.getBoolean("UseItems");
		accessBlocks = file.getBoolean("AccessBlocks");
		fireSpread = file.getBoolean("FireSpead");
		lavaFlow = file.getBoolean("LavaFlow");
		waterFlow = file.getBoolean("WaterFlow");
		pvp = file.getBoolean("PvP");
	}
}
