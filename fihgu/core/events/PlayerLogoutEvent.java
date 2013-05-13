package fihgu.core.events;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class PlayerLogoutEvent extends Event
{
	public EntityPlayerMP player;
	public PlayerLogoutEvent(EntityPlayerMP player)
	{
		this.player = player;
	}
}
