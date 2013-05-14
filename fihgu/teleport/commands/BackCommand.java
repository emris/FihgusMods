package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.Warp;

public class BackCommand extends CommandBase{
	private Warp warp;
	
	public BackCommand()
	{
		warp = new Warp();
		name = "back";
		usage = Language.translate(" : Warp back to your previous location.");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(args.length > 0){
			player.sendChatToPlayer(Language.translate("Invalad command arguments."));
			player.sendChatToPlayer(Language.translate("Usage: /back"));
		} else if(args.length == 0) {
			if(warp.goBack(player)){
				player.sendChatToPlayer(Language.translate("Warped back to previous location."));
			} else {
				player.sendChatToPlayer(Language.translate("You have not warped anywhere!"));
			}
		}
	}
}