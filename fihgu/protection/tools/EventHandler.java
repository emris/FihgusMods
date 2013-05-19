package fihgu.protection.tools;

import java.util.ArrayList;
import java.util.List;

import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.functions.Protection;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class EventHandler
{
	private List<Player> watch;
	private Protection protection;

	public EventHandler(Protection protection)
	{
		watch = new ArrayList<Player>();
		this.protection = protection;
	}

	@ForgeSubscribe
	public void onPlayerInteract(PlayerInteractEvent event)
	{

		Player player = new Player(event.entityPlayer);
		Location loc = new Location(player.getEntity());

		if (this.isWatching(player))
		{
			if (!protection.hasSetTwoLocations(player))
			{
				if (protection.getA(player) != null){
					protection.addLocationA(player, loc);
					player.msg("Location 1 added: x=" + loc.posX + " y=" + loc.posZ + " z=" + loc.posY);
				}
				else if (protection.getb(player) != null){
					protection.addLocationB(player, loc);
					player.msg("Location 2 added: x=" + loc.posX + " y=" + loc.posZ + " z=" + loc.posY);
					this.makeProtection(player);
				}
			}
		}
	}

	public void watchPlayer(Player player)
	{
		if (!this.watch.contains(player))
			watch.add(player);
	}

	public void stopWatching(Player player)
	{
		if (this.watch.contains(player))
			watch.remove(player);
	}

	public boolean isWatching(Player player)
	{
		if (this.watch.contains(player))
			return true;
		else
			return false;
	}
	
	private void makeProtection(Player player){
		protection.makeProtection(player);
		player.msg("Protection " + protection.getName());
		this.stopWatching(player);
	}
}
