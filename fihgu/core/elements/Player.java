package fihgu.core.elements;

import fihgu.core.functions.PlayerManager;
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
}
