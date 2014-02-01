package fihgu.core.functions;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.Location;

public class Teleport
{
	private static HashMap<EntityPlayerMP, Location> playerBackMap = new HashMap<EntityPlayerMP, Location>();

	/**
	 * Send player to location.
	 * @param exact: use doubles when it's true;
	 */
	public static void warp(EntityPlayerMP player, Location loc, boolean exact)
	{
		playerBackMap.put(player, new Location(player));
		int dimension = loc.dimension;

		if (dimension != player.dimension)
			player.travelToDimension(dimension);

		if (!exact)
		{
			player.setPositionAndUpdate(loc.x + 0.5, loc.z, loc.y + 0.5);
		} else
		{
			player.setPositionAndUpdate(loc.posX, loc.posZ, loc.posY);
		}
	}

	/**
	 * @param name: the name of target warp point. player to warp point
	 */
	public static void warp(EntityPlayerMP player, EntityPlayerMP target)
	{
		Location loc = new Location(target);
		warp(player, loc, false);
	}

	public static boolean goBack(EntityPlayerMP player)
	{
		Location loc;
		if (playerBackMap.containsKey(player))
		{
			loc = playerBackMap.get(player);
			warp(player, loc, true);
			return true;
		} 
		else
		{
			return false;
		}
	}
}
