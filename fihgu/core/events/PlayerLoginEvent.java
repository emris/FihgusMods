package fihgu.core.events;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetLoginHandler;
import net.minecraftforge.event.Cancelable;
import net.minecraftforge.event.Event;

@Cancelable
public class PlayerLoginEvent extends Event
{
	public EntityPlayerMP player;
	public NetLoginHandler handler;
	
	public PlayerLoginEvent(EntityPlayerMP player, NetLoginHandler handler)
	{
		this.player = player;
		this.handler = handler;
	}
}
