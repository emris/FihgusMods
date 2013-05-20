package fihgu.core.functions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.protection.elements.Member;
import fihgu.protection.elements.ProtectedRegion;

public class Protection
{

	private static List<ProtectedRegion> protections = new ArrayList<ProtectedRegion>();
	private static ProtectedRegion region;
	private static String fileLoc = "./fihgu/protection/";

	public static void addProtection(ProtectedRegion region)
	{
		protections.add(region);
	}

	public static void populate()
	{
		File folder = new File(fileLoc + "regions");
		if (!folder.exists())
			folder.mkdirs();

		File[] listOfFiles = folder.listFiles();

		for (File file : listOfFiles)
		{
			region = new ProtectedRegion(file.getName().replace(".txt", ""));
			protections.add(region);
		}
	}

	public static boolean exists(String name)
	{
		for (ProtectedRegion p : protections)
		{
			if (p.name.equalsIgnoreCase(name))
			{
				return true;
			}
		}
		return false;
	}

	public static String[] getNameList()
	{
		String[] names = new String[protections.size()];

		for (int i = 0; i < protections.size(); i++)
		{
			names[i] = protections.get(i).name;
		}
		return names;
	}

	public boolean isAnOwner(Player player)
	{
		for (ProtectedRegion region : protections)
		{
			if (region.owner.getName().equals(player.name))
				return true;
		}
		return false;
	}

	public boolean isMemberOf(Player player, String regionName)
	{
		for (ProtectedRegion region : protections)
		{
			if (region.name.equalsIgnoreCase(regionName)
					&& (region.members.contains(new Member(player, region)) || region.owner
							.getName().equals(player.name)))
				return true;
		}
		return false;
	}

	public ProtectedRegion getRegion(Player player)
	{
		for (ProtectedRegion region : protections)
		{
			if (region.members.contains(new Member(player, region)))
			{
				return region;
			}
		}
		return null;
	}

	public ProtectedRegion isOwnerOf(Player player)
	{
		for (ProtectedRegion region : protections)
		{
			if (region.owner.getName().equals(player.name))
				return region;
		}
		return null;
	}
}
