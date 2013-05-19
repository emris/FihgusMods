package fihgu.core.functions;

import java.util.HashMap;

import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.protection.elements.ProtectedRegion;

public class Protection
{
	private HashMap<Player, Location[]> locations;
	private ProtectedRegion pr;

	public Protection()
	{
		locations = new HashMap<Player, Location[]>();
	}

	public void addLocationA(Player player, Location loc)
	{
		if (!this.isProtecting(player))
		{
			this.locations.put(player, new Location[2]);
		}
		this.locations.get(player)[0] = loc;
	}

	public void addLocationB(Player player, Location loc)
	{
		if (!isProtecting(player))
		{
			this.locations.put(player, new Location[2]);
		}
		this.locations.get(player)[1] = loc;
	}

	public boolean isProtecting(Player player)
	{
		if (this.locations.containsKey(player))
			return true;
		else
			return false;
	}

	public boolean hasSetTwoLocations(Player player)
	{
		if (this.isProtecting(player))
		{
			if (this.locations.get(player)[0] != null
					&& this.locations.get(player)[1] != null)
			{
				return true;
			} else
			{
				return false;
			}
		} else
		{
			return false;
		}
	}

	public Location getA(Player player){
		return this.locations.get(player)[0];
	}

	public Location getb(Player player){
		return this.locations.get(player)[1];
	}
	
	public void makeProtection(Player player)
	{
			this.pr = new ProtectedRegion(this.getA(player), this.getb(player));
	}
}
