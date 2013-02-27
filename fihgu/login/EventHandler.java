package login;

import net.minecraftforge.event.ForgeSubscribe;
import core.events.PlayerLoginEvent;

public class EventHandler 
{
	@ForgeSubscribe
	public void onPlayerLogin(PlayerLoginEvent e)
	{
		e.setCanceled(true);
	}
}
