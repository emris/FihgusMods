package fihgu.login.commands;

import net.minecraft.entity.player.EntityPlayerMP;
import fihgu.core.elements.CommandBase;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.login.CommonProxy;

public class LogoutCommand extends CommandBase
{
	public static CommandBase instance;
	public LogoutCommand()
	{
		trueName = "logout";
		name = "logout";
		usage = Language.translate(": logout from this computer.");
		instance = this;
	}

	@Override
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		CommonProxy.loginIP.remove(player.username);
		player.playerNetServerHandler.kickPlayerFromServer(McColor.green + Language.translate("You have been logged out."));
	}
}
