package fihgu.login.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.login.CommonProxy;

public class RegisterCommand extends CommandBase
{
	public static CommandBase instance;
	public RegisterCommand()
	{
		trueName = "register";
		name = "register";
		usage = Language.translate(" <Password> <Password>: register an account on this server.");
		instance = this;
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		if(CommonProxy.getPassword(player.username) != null)
		{
			player.addChatMessage(McColor.darkRed + Language.translate("This name has already been registered."));
			return;
		}

		if(args.length != 2)
		{
			player.addChatMessage(McColor.darkRed + Language.translate("Argument mismatch, try:"));
			player.addChatMessage(McColor.green + Language.translate("/register password repeat_password"));
			return;
		}

		if(!args[0].equals(args[1]))
		{
			player.addChatMessage(McColor.darkRed + Language.translate("Two passwords does not match!"));
			return;
		}
		else
		{
			CommonProxy.setPassword(player, args[1]);
		}
	}
}
