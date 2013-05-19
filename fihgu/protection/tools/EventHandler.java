package fihgu.protection.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.functions.Protection;
import fihgu.protection.elements.ProtectedRegion;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;

public class EventHandler
{
	private static List<Player> watch;
	public static String name;
	private HashMap<Player, Location[]> locations = new HashMap<Player, Location[]>();
	
	public EventHandler()
	{
		watch = new ArrayList<Player>();
	}

	@ForgeSubscribe
	public void onPlayerInteract(PlayerInteractEvent event)
	{
		Player player = new Player(event.entityPlayer);
		Location loc = new Location(player.getEntity());
		
		if (this.isWatching(player))
		{
			player.msg("Watching.");
			addPlayer(player);
			{
				player.msg("Player does not have two locations.");
				if (getA(player) == null)
				{
					player.msg("Player has not set A");
					setLocationA(player);
					player.msg("Location 1 added: x=" + loc.posX + " y="
							+ loc.posZ + " z=" + loc.posY);
				}
				else if (getB(player) == null)
				{
					player.msg("Player has not set B");

					player.msg("Location 2 added: x=" + loc.posX + " y="
							+ loc.posZ + " z=" + loc.posY);
					this.makeProtection(player);
				}
			}
		}
	}
	
	private boolean hasTwoLocations(Player player){
		return this.locations.get(player)[0] != null && this.locations.get(player)[0] != null;
	}
	
	private void addPlayer(Player player)
	{
		if(!this.locations.containsKey(player))
		{
			this.locations.put(player, new Location[2]);
		}
	}
	
	private void setLocationA(Player player){
		this.locations.get(player)[0] = new Location(player.getEntity());
	}
	
	private void setLocationB(Player player){
		this.locations.get(player)[1] = new Location(player.getEntity());
	}
	
	private Location getA(Player player)
	{
		return this.locations.get(player)[0];
	}
	
	private Location getB(Player player)
	{
		return this.locations.get(player)[1];
	}
	
	public static void watchPlayer(Player player)
	{
		if (!watch.contains(player)){
			watch.add(player);
		}
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
	
	private void makeProtection(Player player)
	{
		ProtectedRegion region = new ProtectedRegion(name, getA(player), getB(player));
		region.save();
		Protection.addProtection(region);
		player.msg("Protection " + name);
		this.stopWatching(player);
	}
}
