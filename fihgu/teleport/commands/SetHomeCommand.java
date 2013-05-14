package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.Warp;

public class SetHomeCommand extends CommandBase{
	private Warp warp;
	
	public SetHomeCommand()
	{
		warp = new Warp();
		name = "sethome";
		usage = Language.translate(" : Set or update Home at your location.");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length > 0){
			player.sendChatToPlayer(Language.translate("Invalad command arguments."));
			player.sendChatToPlayer(Language.translate("Usage: /sethome"));
		} else if(args.length == 0) {
			if(warp.newHome(player)){
				player.sendChatToPlayer(Language.translate("Your home has been set to your location!"));
			} else {
				player.sendChatToPlayer(Language.translate("Your home has been updated to your location!"));
			}
		}
	}
}