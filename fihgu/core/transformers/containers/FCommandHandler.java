package fihgu.core.transformers.containers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fihgu.core.events.TryCommandEvent;
import fihgu.core.shortcut.FML;
import cpw.mods.fml.common.event.FMLLoadEvent;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

import net.minecraft.command.CommandException;
import net.minecraft.command.CommandHandler;
import net.minecraft.command.CommandNotFoundException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.PlayerSelector;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;

public class FCommandHandler extends CommandHandler
{
	public int executeCommand(ICommandSender par1ICommandSender, String par2Str)
    {
        par2Str = par2Str.trim();

        if (par2Str.startsWith("/"))
        {
            par2Str = par2Str.substring(1);
        }

        String[] astring = par2Str.split(" ");
        String s1 = astring[0];
        
        //astring = dropFirstString(astring);
        String[] var1 = new String[astring.length - 1];
        for (int var2 = 1; var2 < astring.length; ++var2)
        {
            var1[var2 - 1] = astring[var2];
        }
        
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
                {
                    i = temp;
                }
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
            && (icommand.canCommandSenderUseCommand(par1ICommandSender) || FML.isModLoaded("fihgu's Permission Mod")))
            {
                CommandEvent event = new CommandEvent(icommand, par1ICommandSender, astring);
                if (MinecraftForge.EVENT_BUS.post(event))
                {
                    if (event.exception != null)
                    {
                        throw event.exception;
                    }
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
                            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + par1ICommandSender.translateString(commandexception.getMessage(), commandexception.getErrorOjbects()));
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
                par1ICommandSender.sendChatToPlayer("" + EnumChatFormatting.RED + "You do not have permission to use this command.");
            }
        }
        catch (WrongUsageException wrongusageexception)
        {
            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + par1ICommandSender.translateString("commands.generic.usage", new Object[] {par1ICommandSender.translateString(wrongusageexception.getMessage(), wrongusageexception.getErrorOjbects())}));
        }
        catch (CommandException commandexception1)
        {
            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + par1ICommandSender.translateString(commandexception1.getMessage(), commandexception1.getErrorOjbects()));
        }
        catch (Throwable throwable)
        {
            par1ICommandSender.sendChatToPlayer(EnumChatFormatting.RED + par1ICommandSender.translateString("commands.generic.exception", new Object[0]));
            throwable.printStackTrace();
        }

        return j;
    }
	
	
	public List getPossibleCommands(ICommandSender par1ICommandSender)
    {
        ArrayList var2 = new ArrayList();

        for (Object key : super.getCommands().keySet())
        {
        	
            ICommand var4 = (ICommand)super.getCommands().get(key);

            if (var4.canCommandSenderUseCommand(par1ICommandSender) || (FML.isModLoaded("fihgu's Permission Mod")))
            {
            	if(!var2.contains(super.getCommands().get(key)))
                var2.add(var4);
            }
        }

        return var2;
    }
}
