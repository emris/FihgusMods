package permission.tools;

import permission.CommonProxy;
import permission.element.PermissionOwner;
import core.events.TryCommandEvent;
import core.functions.Language;
import core.functions.Message;
import core.functions.PlayerManager;
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
