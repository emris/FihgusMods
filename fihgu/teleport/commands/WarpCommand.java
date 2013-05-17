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
		sender = player;	
		if(args.length < 1 || args.length > 1)
		{
			player.sendChatToPlayer(Language.translate("Invalad command arguments."));
			player.sendChatToPlayer(Language.translate("Usage: /warp <Warp Name | Player>"));
		} 
		else if(args.length == 1) 
		{
			EntityPlayerMP player2 = PlayerManager.getPlayer(args[0], true);
			
			if(player2 != null)
			{
				player.sendChatToPlayer(Language.translate("Request sent to ")  + player2.username + "!");
				player2.sendChatToPlayer(player.username + Language.translate(" has send you a Warp request!"));
				player2.sendChatToPlayer(Language.translate("you can use /y to accept or /n to deny."));
				
				new Request(new Player(player2), 30)
				{
					@Override
					public void accepted()
					{
						sender.sendChatToPlayer(player + " has accepted!");
						warp.warpTo(sender, player.getEntity());
						player.msg("Warpped to " + sender.username);
					}
				};
			}
			else if(warp.warpTo(player, args[0]))
			{
				player.sendChatToPlayer(Language.translate("Warped to " + args[0] + "."));			
			}
			else if(PlayerManager.getPossiblePlayer(args[0]) != null)
			{
				player2 = PlayerManager.getPossiblePlayer(args[0]);
				
				player.sendChatToPlayer(Language.translate("Request sent to ")  + player2.username + "!");
				player2.sendChatToPlayer(player.username + Language.translate(" has send you a Warp request!"));
				player2.sendChatToPlayer(Language.translate("you can use /y to accept or /n to deny."));
				
				new Request(new Player(player2), 30)
				{
					@Override
					public void accepted()
					{
						sender.sendChatToPlayer(player + " has accepted!");
						warp.warpTo(sender, player.getEntity());
						player.msg("Warpped to " + sender.username);
					}
				};
			}
			else 
			{
				player.sendChatToPlayer(Language.translate("Warp " + args[0] + " does not exist!"));
			}
		}
	}
}
