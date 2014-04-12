package fihgu.core.functions;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.Location;
import net.minecraft.util.MovingObjectPosition;

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
			player.setPositionAndUpdate(loc.x + 0.5, loc.y, loc.z + 0.5);
		else
			player.setPositionAndUpdate(loc.posX, loc.posY, loc.posZ);
	}

	/**
	 * @param name: the name of target warp point. player to warp point
	 */
	public static void warp(EntityPlayerMP player, EntityPlayerMP target)
	{
		Location loc = new Location(target);
		warp(player, loc, false);
	}

	/**
	 * Send player to location he is looking at.
	 * @param player
	 */
	public static void jump(EntityPlayerMP player)
	{
		Location loc = new Location(player, "jump");
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
