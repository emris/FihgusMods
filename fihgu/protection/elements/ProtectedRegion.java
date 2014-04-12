package fihgu.protection.elements;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.elements.Region;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.io.SaveFile;

public class ProtectedRegion
{
	public static ArrayList<ProtectedRegion> protectedRegions = new ArrayList<ProtectedRegion>();
	public static HashMap<Player,Location> watchlist = new HashMap<Player,Location>();
	public static HashMap<Player,String> namelist = new HashMap<Player,String>();

	public String name;
	public Region region;
	public Player owner;
	public ArrayList<String> sharedPlayer = new ArrayList<String>();


	public ProtectedRegion(String name, Player owner, Region region)
	{
		this.name = name;
		this.owner = owner;
		this.region = region;
	}

	public static boolean watch(PlayerInteractEvent e)
	{
		Player player = new Player(e.entityPlayer);
		Location blockLocation = new Location(e.x,e.y,e.z,e.entityPlayer.dimension);

		ProtectedBlock blockCheck = ProtectedBlock.isProtected(blockLocation);
		ProtectedRegion regionCheck = ProtectedRegion.isProtected(blockLocation);

		if(watchlist.containsKey(player))
		{
			Location location = watchlist.get(player);
			watchlist.remove(player);
			//after using /lock regionName:
			if(blockCheck != null)
			{
				player.msg(McColor.darkRed + Language.translate("This block is already locked by ") + McColor.aqua + blockCheck.owner.name + McColor.darkRed + ".");
				return true;
			}
			else if(regionCheck != null)
			{
				player.msg(McColor.darkRed + Language.translate("This block is already locked by ") + McColor.aqua + regionCheck.owner.name + McColor.darkRed + ".");
				return true;
			}
			else
			{
				if(location == null)
				{
					watchlist.put(player, blockLocation);
					player.msg(McColor.aqua + "(" + blockLocation + ")" + McColor.green + Language.translate(" set as Point 1."));
					player.msg(McColor.green + Language.translate("Please click on another block to define Point 2."));
				}
				else
				{
					Location point1 = location;
					Location point2 = blockLocation;
					ProtectedRegion protectedRegion = new ProtectedRegion(namelist.get(player),player,new Region(point1, point2));

					blockCheck = ProtectedBlock.isProtected(protectedRegion.region);
					if(blockCheck != null)
					{
						player.msg(McColor.darkRed + Language.translate("Part of this region is already locked by ") + McColor.aqua + blockCheck.owner.name + McColor.darkRed + ".");
						return true;
					}

					player.msg(McColor.aqua + protectedRegion.name + McColor.green + Language.translate(" has been created."));
					protectedRegions.add(protectedRegion);
					protectedRegion.save();
				}
			}
			return true;
		}
		return false;
	}

	public static ProtectedRegion isProtected(Location location)
	{
		for(ProtectedRegion temp : protectedRegions)
		{
			if(temp.region.contains(location))
				return temp;
		}
		return null;
	}

	public static ProtectedRegion isProtected(Region region)
	{
		for(ProtectedRegion temp : protectedRegions)
		{
			if(temp.region.contains(region))
				return temp;
		}
		return null;
	}

	public static void loadAll()
	{
		protectedRegions.clear();

		File dir = new File("./fihgu/protection/region/");
		dir.mkdirs();
		File[] files = dir.listFiles();

		if(files != null && files.length > 0)
			for(File file : files)
			{
				try
				{
					Scanner scan = new Scanner(file);
					String name = scan.nextLine();
					String owner = scan.nextLine();
					String point1 = scan.nextLine();
					String point2 = scan.nextLine();
					ProtectedRegion temp = new ProtectedRegion(name,new Player(owner),new Region(new Location(point1),new Location(point2)));
					while(scan.hasNext())
					{
						temp.sharedPlayer.add(scan.nextLine());
					}
					protectedRegions.add(temp);
					scan.close();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
	}

	public static void saveAll()
	{
		for(ProtectedRegion region : protectedRegions)
		{
			SaveFile file = new SaveFile(region.name+".txt", "./fihgu/protection/region/");
			file.data.add(region.name);
			file.data.add(region.owner.name);
			file.data.add(region.region.point1.toString());
			file.data.add(region.region.point2.toString());
			for(String name : region.sharedPlayer)
			{
				file.data.add(name);
			}
			file.save(false);
		}
	}

	public void save()
	{
		SaveFile file = new SaveFile(name+".txt", "./fihgu/protection/region/");
		file.data.add(name);
		file.data.add(owner.name);
		file.data.add(region.point1.toString());
		file.data.add(region.point2.toString());
		for(String name1 : sharedPlayer)
		{
			file.data.add(name1);
		}
		file.save(false);
	}

	public boolean canAccess(Player player)
	{
		if(player.name.equals(owner.name))
			return true;
		for(String share : this.sharedPlayer)
		{
			if(share.equals(player.name))
				return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o)
	{
		if(o instanceof ProtectedRegion)
			return name.equals(((ProtectedRegion)o).name);
		return false;
	}

	@Override
	public int hashCode()
	{
		return name.hashCode();
	}

}
