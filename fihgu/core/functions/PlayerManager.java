package fihgu.core.functions;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.shortcut.Server;

public class PlayerManager 
{
	public static String[] getPlayerList()
	{	
		return Server.getConfigurationManager().getAllUsernames();
	}
	
	/**
	 * @param name name of the player
	 * @param ignoreCase ignore case or not.
	 */
	public static EntityPlayerMP getPlayer(String name, boolean ignoreCase)
	{
		EntityPlayerMP player = null;
		
		for(String tempName : getPlayerList())
		{
			if(ignoreCase)
			{
				if(tempName.equalsIgnoreCase(name))
					player = Server.getConfigurationManager().getPlayerForUsername(tempName);
			}
			else
			{
				if(tempName.equals(name))
					player = Server.getConfigurationManager().getPlayerForUsername(tempName);
			}
		}
		
		return player;
	}
	
	/**
	 * same as getPlayer(name,false);
	 */
	public static EntityPlayerMP getPlayer(String name)
	{
		return getPlayer(name,false);
	}
	
	/**
	 * Search though all the connected player with the given info.
	 * 
	 * will return the player first if the info is the player's full name.
	 * will return null if the info is contained by two or more names.(unless full name)
	 * 
	 * @param info a piece of the name of the player you looking for
	 * @return the player's EntityMP or null(not found)
	 */
	public static EntityPlayerMP getPossiblePlayer(String info)
	{
		EntityPlayerMP player = null;
		
		player = getPlayer(info);
		if(player != null)
			return player;
		
		player = getPlayer(info,false);
		if(player != null)
			return player;
		
		boolean isUnique = true;
		for(String name : getPlayerList())
		{
			if(name.toLowerCase().contains(info.toLowerCase()))
			{
				if(player == null)
					player = getPlayer(name);
				else
					isUnique = false;
			}
		}
		
		if(isUnique)
			return player;
		else
			return null;
	}
	
	/**
	 * return if the player is a op or not.
	 * ignore name case.
	 */
	public static boolean isOp(String playerName)
	{
		return Server.getConfigurationManager().getOps().contains(playerName.toLowerCase());
	}
	
	public static boolean isOp(EntityPlayerMP player)
	{
		return isOp(player.username.toLowerCase());
	}
}
