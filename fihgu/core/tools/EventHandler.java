package fihgu.core.tools;

import fihgu.core.elements.Group;
import fihgu.core.elements.Player;
import fihgu.core.events.PlayerLoginEvent;
import fihgu.core.functions.Language;
import fihgu.core.functions.Message;
import fihgu.core.functions.PlayerManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class EventHandler 
{
	@ForgeSubscribe
	public void onTryCommand(PlayerLoginEvent e)
	{
		Player player = new Player(e.player);
		
		if(player.getGroups().size() <= 0)
			Group.getDefaultGroup().players.add(player);
		
		Group.saveAll();
	}
}
