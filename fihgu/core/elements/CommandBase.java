package fihgu.core.elements;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;
import fihgu.core.FPreloader;
import fihgu.core.functions.Language;
import fihgu.core.functions.Log;
import fihgu.core.functions.McColor;
import fihgu.core.functions.Message;
import fihgu.core.functions.PlayerManager;
import fihgu.core.shortcut.Server;

public class CommandBase implements ICommand
{
	public static String trueName;
	public String name;
	public String usage;
	public boolean opOnly = false;

	public void register()
	{
		name = FPreloader.commandConfig.get(name, name);
		Server.getCommandHandler().registerCommand(this);
	}

	/**
	 * Override this method to process things when command is used by console.
	 */
	public void processConsole(String[] args)
	{
		Log.logWarnning(Language.translate("You may not use this command in console."));
	}

	/**
	 * Override this method to process things when command is used by a player.
	 */
	public void processPlayer(EntityPlayerMP player, String[] args)
	{
		Message.warnPlayer(player, Language.translate("You may not use this command as a player."));
	}
	
	@Override
	public int compareTo(Object o) 
	{
		return this.getCommandName().charAt(0) - ((ICommand)o).getCommandName().charAt(0);
	}

	@Override
	public String getCommandName() 
	{
		return name;
	}

	@Override
	public String getCommandUsage(ICommandSender sender) 
	{
		return "/" + name + " " + usage;
	}

	@Override
	public List getCommandAliases() 
	{
		ArrayList<String> name = new ArrayList<String>();
		return name;
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) 
	{
		if(sender instanceof EntityPlayerMP)
			this.processPlayer((EntityPlayerMP)sender, args);
		else
			this.processConsole(args);
	}

	@Override
	public boolean canCommandSenderUseCommand(ICommandSender sender) 
	{
		//Console or RCON
		if(!(sender instanceof EntityPlayerMP))
			return true;

		if(opOnly && !PlayerManager.isOp(sender.getCommandSenderName()))
			return false;

		return true;
	}

	@Override
	public List addTabCompletionOptions(ICommandSender sender, String[] args) 
	{
		return null;
	}

	public void argumentMismatch(ICommandSender sender)
	{
		sender.sendChatToPlayer(ChatMessageComponent.createFromText(McColor.darkRed + Language.translate("Argument mismatch, try:")));
		sender.sendChatToPlayer(ChatMessageComponent.createFromText(McColor.green + name + usage));
	}

	@Override
	public boolean isUsernameIndex(String[] astring, int i)
	{
		return false;
	}
}
