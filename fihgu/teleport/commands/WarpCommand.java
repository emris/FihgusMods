package fihgu.teleport.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.elements.Location;
import fihgu.core.elements.Player;
import fihgu.core.elements.Request;
import fihgu.core.functions.Language;
import fihgu.core.functions.PlayerManager;
import fihgu.core.functions.Warp;

public class WarpCommand extends CommandBase{
	Warp warp;
	private EntityPlayerMP sender;
	
	public WarpCommand()
	{
		warp = new Warp();
		name = "warp";
		usage = Language.translate(" <Warp Name>: Teleport to a set Warp");
	}
	
	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		EntityPlayerMP player2 = PlayerManager.getPlayer(args[0], true);
		if(args.length < 1 || args.length > 1)
		{
			player.sendChatToPlayer(Language.translate("Invalad command arguments."));
			player.sendChatToPlayer(Language.translate("Usage: /warp <Warp Name | Player>"));
		} else if(args.length == 1) {
			if(player2 != null)
			{
				sender = player;
				new Request(new Player(player2), 30){
					@Override
					public void accepted(){
						warp.warpTo(sender, player.getEntity());
						sender.sendChatToPlayer(player + " has accepted!");
						player.msg("Warpped to " + sender.username);
					}
				};
			}
			else if(warp.warpTo(player, args[0]))
			{
				player.sendChatToPlayer(Language.translate("Warped to " + args[0] + "."));			
			}
			else 
			{
				player.sendChatToPlayer(Language.translate("Warp " + args[0] + " does not exist!"));
			}
		}
	}
}
