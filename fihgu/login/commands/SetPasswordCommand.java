package fihgu.login.commands;

import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.functions.PlayerManager;
import fihgu.login.CommonProxy;

public class SetPasswordCommand extends CommandBase
{
	public static CommandBase instance;
	public SetPasswordCommand()
	{
		trueName = "setpassword";
		name = "setpassword";
		usage = Language.translate(" [PlayerName] <Passowrd>: register an account or reset password for a player.");
		//opOnly = true;
		instance = this;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) 
	{	
		if(sender instanceof EntityPlayerMP && args.length == 1)
		{
			setPassword(sender.getCommandSenderName(),args[0]);
			sender.sendChatToPlayer(ChatMessageComponent.createFromText(McColor.green + sender.getCommandSenderName() + Language.translate("'s password has been changed to ") + args[0]));
			return;
		}

		if(args.length != 2)
		{
			sender.sendChatToPlayer(ChatMessageComponent.createFromText(McColor.darkRed + Language.translate("Argument mismatch, try:")));
			sender.sendChatToPlayer(ChatMessageComponent.createFromText(McColor.green + Language.translate("/setpassword playerName password")));
			return;
		}

		if(!(sender instanceof EntityPlayerMP && PlayerManager.isOp(sender.getCommandSenderName())))
		{
			sender.sendChatToPlayer(ChatMessageComponent.createFromText(McColor.darkRed + Language.translate("You may not change others' passwords.")));
		}

		if(CommonProxy.getPassword(args[0]) == null)
		{
			sender.sendChatToPlayer(ChatMessageComponent.createFromText(McColor.darkRed + Language.translate("This name hasn't been registered.")));
			return;
		}		

		setPassword(args[0],args[1]);
		sender.sendChatToPlayer(ChatMessageComponent.createFromText(McColor.green + args[0] + Language.translate("'s password has been changed to ") + args[1]));
	}

	public static void setPassword(String name, String password)
	{
		for(int i = 0; i < CommonProxy.playerData.data.size(); i++)
		{
			String line = CommonProxy.playerData.data.get(i);
			if(line.split(" ")[0].equals(name))
			{
				CommonProxy.playerData.data.set(i,name + " " + password);
				CommonProxy.playerData.save(true);
				return;
			}
		}
		CommonProxy.playerData.data.add(name + " " + password);
		CommonProxy.playerData.save(true);
	}
}
