package fihgu.core.elements;

import java.util.ArrayList;

import fihgu.core.functions.PlayerManager;
import fihgu.core.functions.Warp;
import fihgu.core.shortcut.Server;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemInWorldManager;
import net.minecraft.world.storage.SaveHandler;
import net.minecraftforge.common.DimensionManager;

public class Player 
{
	public String name;
	private EntityPlayerMP entity;
	
	public Player(String name)
	{
		this.name = name;
	}
	
	public Player(EntityPlayerMP player)
	{
		this.name = player.username;
		this.entity = player;
	}
	
	/**
	 * @return a list of groups this player is in.
	 */
	public ArrayList<Group> getGroups()
	{
		ArrayList<Group> list = new ArrayList<Group>();
		Group.loadAll();
		
		for(Group group:Group.groups)
			if(group.players.contains(this))
				list.add(group);
		
		return list;
	}
	
	public void msg(String line)
	{
		if(this.isOnline())
		{
			this.getEntity().sendChatToPlayer(line);
		}
	}
	
	/**
	 *check if player is online.
	 */
	public boolean isOnline()
	{
		return PlayerManager.getPlayer(name)!=null;
	}
	
	/**
	 * TEST REQUIRED
	 * 
	 * get the player's entity no matter he's online or not.
	 * 
	 */
	public EntityPlayerMP getEntity()
	{
		if(isOnline())
			entity = PlayerManager.getPlayer(name);
		else
		{
			ItemInWorldManager itemInWorldManager = new ItemInWorldManager(Server.getServer().worldServerForDimension(0));
			entity = new EntityPlayerMP(Server.getServer(), Server.getServer().worldServerForDimension(0), name, itemInWorldManager);
			
			Server.getConfigurationManager().readPlayerDataFromFile(entity);
		}
		
		return entity;
	}
	
	/**
	 * TEST REQUIRED
	 * save the player's current data to the hard drive
	 */
	public void save()
	{
		if(entity != null)
		{
			((SaveHandler)DimensionManager.getWorld(0).getSaveHandler()).writePlayerData(entity);
		}
	}
	
	@Override
	public boolean equals(Object O)
	{
		return (O instanceof Player) && ((Player)O).name.equals(name);
	}
	
	public void warp(Location loc)
	{
		Warp.warpTo(getEntity(), loc, false);
	}
	
	@Override
	public int hashCode()
	{
		return name.hashCode();
	}
}
