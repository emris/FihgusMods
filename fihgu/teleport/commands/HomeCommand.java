package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.Warp;

public class HomeCommand extends CommandBase{
	private Warp warp;
	
	public HomeCommand()
	{
		warp = new Warp();
		name = "home";
		usage = Language.translate(" : Go to Home location.");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length > 0){
			player.sendChatToPlayer(Language.translate("Invalad command arguments."));
			player.sendChatToPlayer(Language.translate("Usage: /home"));
		} else if(args.length == 0) {
			if(warp.warpHome(player)){
				player.sendChatToPlayer(Language.translate("Warped home."));
			} else {
				player.sendChatToPlayer(Language.translate("You have not set home yet!"));
			}
		}
	}
}