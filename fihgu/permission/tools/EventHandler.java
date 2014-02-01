package fihgu.permission.tools;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.ForgeSubscribe;
import fihgu.core.elements.Player;
import fihgu.core.events.TryCommandEvent;
import fihgu.core.functions.Language;
import fihgu.core.functions.Message;
import fihgu.permission.element.PermissionOwner;

public class EventHandler 
{
	@ForgeSubscribe
	public void onTryCommand(TryCommandEvent e)
	{
		if(e.sender instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) e.sender;
			
			if(!new PermissionOwner(new Player(player)).canUse(e))
			{
				Message.warnPlayer(player, Language.translate("[fihgu's Permission Mod]: You don't have permission to use this command."));
				e.setCanceled(true);
			}
		}
	}
}
