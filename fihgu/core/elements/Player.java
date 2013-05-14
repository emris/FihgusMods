package fihgu.core.elements;

import net.minecraft.entity.player.EntityPlayerMP;

public class Player 
{
	public String name;
	public EntityPlayerMP entity;
	
	public Player(String name)
	{
		this.name = name;
	}
	
	/**
	 * TODO:check if player is online.
	 */
	public boolean isOnline()
	{
		return false;
	}
	
	/**
	 * TODO: return player entity if he is online.
	 * load data n construct a player entity from saved data if he's not.
	 * return null if no player is found.
	 * 
	 * so you can access player inventory even if he is offline.
	 */
	public EntityPlayerMP getEntity()
	{
		return entity;
	}
}
