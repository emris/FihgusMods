package fihgu.core.transformers.containers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.command.CommandException;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.CommandNotFoundException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerSelector;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import fihgu.core.elements.Player;
import fihgu.core.events.TryCommandEvent;
import fihgu.core.shortcut.FML;

public class FCommandHandler extends CommandHandler
{
	@Override
	public int executeCommand(ICommandSender par1ICommandSender, String par2Str)
	{
		par2Str = par2Str.trim();

		if (par2Str.startsWith("/"))
			par2Str = par2Str.substring(1);

		String[] astring = par2Str.split(" ");
		String s1 = astring[0];

		//astring = dropFirstString(astring);
		String[] var1 = new String[astring.length - 1];
		for (int var2 = 1; var2 < astring.length; ++var2)
			var1[var2 - 1] = astring[var2];

		astring = var1;
		/////////////////////////////////////

		ICommand icommand = (ICommand)super.getCommands().get(s1);

		//int i = this.getUsernameIndex(icommand, astring);
		int i;
		if (icommand == null)
		{
			i = -1;
		}
		else
		{
			for (int temp = 0; temp < astring.length; ++temp)
			{
				if (icommand.isUsernameIndex(astring, temp) && PlayerSelector.matchesMultiplePlayers(astring[temp]))
					i = temp;
			}
			i = -1;
		}
		//////////////////////////////////////////////////

		int j = 0;

		try
		{
			if (icommand == null)
			{
				throw new CommandNotFoundException();
			}

			if (!MinecraftForge.EVENT_BUS.post(new TryCommandEvent(par1ICommandSender, par2Str))
				&& (icommand.canCommandSenderUseCommand(par1ICommandSender) || FML.isModLoaded("fihgus_permission_mod")))
			{
				CommandEvent event = new CommandEvent(icommand, par1ICommandSender, astring);
				if (MinecraftForge.EVENT_BUS.post(event))
				{
					if (event.exception != null)
						throw event.exception;
					return 1;
				}

				if (i > -1)
				{
					EntityPlayerMP[] aentityplayermp = PlayerSelector.matchPlayers(par1ICommandSender, astring[i]);
					String s2 = astring[i];
					EntityPlayerMP[] aentityplayermp1 = aentityplayermp;
					int k = aentityplayermp.length;

					for (int l = 0; l < k; ++l)
					{
						EntityPlayerMP entityplayermp = aentityplayermp1[l];
						astring[i] = entityplayermp.getEntityName();

						try
						{
							icommand.processCommand(par1ICommandSender, astring);
							++j;
						}
						catch (CommandException commandexception)
						{
							par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.RED + commandexception.getMessage()));
						}
					}
					astring[i] = s2;
				}
				else
				{
					icommand.processCommand(par1ICommandSender, astring);
					++j;
				}
			}
			else
			{
				if(!FML.isModLoaded("fihgus_permission_mod"))
					par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "You do not have permission to use this command."));
			}
		}
		catch (WrongUsageException wrongusageexception)
		{
			par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.generic.usage", new Object[] {par1ICommandSender.getCommandSenderName()}).setColor(EnumChatFormatting.RED));
		}
		catch (CommandException commandexception1)
		{
			par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions(commandexception1.getMessage(), commandexception1.getErrorOjbects()));
		}
		catch (Throwable throwable)
		{
			par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions("commands.generic.exception", new Object[0]));
			throwable.printStackTrace();
		}

		return j;
	}

	@Override
	public List getPossibleCommands(ICommandSender sender)
	{
		ArrayList arraylist = new ArrayList();
		Iterator iterator = super.getCommands().keySet().iterator();
		while (iterator.hasNext())
		{
			Object next = iterator.next();

			if(true)
			{
				ICommand icommand = (ICommand)super.getCommands().get(next);

				if(FML.isModLoaded("fihgus_permission_mod") && sender instanceof EntityPlayerMP)
				{
					Player player = new Player(sender.getCommandSenderName());
					fihgu.permission.element.PermissionOwner owner = new fihgu.permission.element.PermissionOwner(player);
					if(owner.canUse(new TryCommandEvent(sender, icommand.getCommandName())))
					{
						if(!arraylist.contains(icommand))
							arraylist.add(icommand);
					}
				}
				else if (icommand.canCommandSenderUseCommand(sender))
				{
					arraylist.add(icommand);
				}
			}
		}
		return arraylist;
	}
}
