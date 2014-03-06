package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChunkCoordinates;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.functions.Language;
import fihgu.core.functions.Teleport;

public class SpawnCommand extends CommandBase
{
	public SpawnCommand()
	{
		name = "spawn";
		usage = Language.translate(" : teleport to server's spawn location");
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		ChunkCoordinates spawn = player.getServerForPlayer().getSpawnPoint();
		int topBlock = player.getServerForPlayer().getTopSolidOrLiquidBlock(spawn.posX, spawn.posZ);
		Teleport.warp(player, new Location(spawn.posX,topBlock+1,spawn.posZ,0), false);
	}
}
