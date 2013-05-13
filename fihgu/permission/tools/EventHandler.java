package fihgu.permission.tools;

import fihgu.permission.CommonProxy;
import fihgu.permission.element.PermissionOwner;
import fihgu.core.events.TryCommandEvent;
import fihgu.core.functions.Language;
import fihgu.core.functions.Message;
import fihgu.core.functions.PlayerManager;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class EventHandler 
{
	@ForgeSubscribe
	public void onTryCommand(TryCommandEvent e)
	{
		if(e.sender instanceof EntityPlayerMP)
		{
			EntityPlayerMP player = (EntityPlayerMP) e.sender;
			if(!CommonProxy.get(new PermissionOwner(player.username)).checkPermission(e))
			{
				Message.warnPlayer(player, Language.translate("[fihgu's Permission Mod]: You don't have permission to use this command."));
				e.setCanceled(true);
			}
		}
	}
}
