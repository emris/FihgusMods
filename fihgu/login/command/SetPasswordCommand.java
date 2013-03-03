package login.command;

import login.CommonProxy;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import core.elements.CommandBase;
import core.functions.Language;
import core.functions.McColor;

public class SetPasswordCommand extends CommandBase
{
	public static CommandBase instance;
	public SetPasswordCommand()
	{
		name = "setpassword";
		usage = "register an account or reset password for a player.";
		opOnly = true;
		instance = this;
	}
	
	@Override
	public void processCommand(ICommandSender sender, String[] args) 
	{
		if(args.length != 2)
		{
			sender.sendChatToPlayer(McColor.darkRed + Language.translate("Argument mismatch, try:"));
			sender.sendChatToPlayer(McColor.green + Language.translate("/setpassword playerName password"));
			return;
		}
		
		if(CommonProxy.getPassword(args[0]) == null)
		{
			sender.sendChatToPlayer(McColor.darkRed + Language.translate("This name hasn't been registered."));
			return;
		}		
		
		for(int i = 0; i < CommonProxy.playerData.data.size(); i++)
		{
			String line = CommonProxy.playerData.data.get(i);
			if(line.split(" ")[0].equals(args[0]))
			{
				CommonProxy.playerData.data.set(i,args[0] + " " + args[1]);
				CommonProxy.playerData.save(true);
				sender.sendChatToPlayer(McColor.green + Language.translate(args[0] + "'s password has been changed."));
				return;
			}
		}
	}
}
