package fihgu.core.elements;

import java.io.File;
import java.util.ArrayList;

import fihgu.core.io.SaveFile;

/**
 * a group of players defined for later use.
 * ex. 
 * warp a group of player to you
 * apply permission to a group of player at once
 * 
 * avoid loading the group too often, for that there may be a lot of players.
 */
public class Group 
{
	public static ArrayList<Group> groups = new ArrayList<Group>();
	
	public String name;
	public ArrayList<Player> players = new ArrayList<Player>();
	public SaveFile saveFile;
	
	public Group(String name)
	{
		this.name = name;
		saveFile = new SaveFile("group_" + name + ".txt","./fihgu/core/groups/");
		
		if(saveFile.exists())
		{
			saveFile.load();
			for(String line:saveFile.data)
			{
				players.add(new Player(line));
			}
		}
	}
	
	public static void loadAll()
	{
		File dir = new File("./fihgu/core/groups/");
		if(dir.exists())
		{
			File[] list = dir.listFiles();
		
			for(File file:list)
			{
				if(file.getName().startsWith("group_") && file.getName().endsWith(".txt"));
				groups.add(new Group(file.getName().substring(6,file.getName().length()-4)));
			}
		}
	}
	
	public static void saveAll()
	{
		
	}
	
	public void save()
	{
		saveFile.data.clear();
		
		for(Player player:players)
		{
			saveFile.data.add(player.name);
		}
		
		saveFile.save(false);
	}
}
