package fihgu.core.events;

import fihgu.core.elements.Location;
import net.minecraft.world.Explosion;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class BlockExplodedEvent extends Event
{
	public Location location;
	public Explosion explosion;
	
	public BlockExplodedEvent(Location location, Explosion explosion)
	{
		this.location = location;
		this.explosion = explosion;
	}
}
