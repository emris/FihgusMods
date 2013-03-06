package core.container;

import java.util.HashMap;
import java.util.Map;

import core.events.TryCommandEvent;
import core.shortcut.FML;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;

public class FCommandHandler extends CommandHandler
{
	public void executeCommand(ICommandSender par1ICommandSender, String par2Str)
    {		
        if (par2Str.startsWith("/"))
        {
            par2Str = par2Str.substring(1);
        }

        String[] var3 = par2Str.split(" ");
        String var4 = var3[0];
        
        //var3 = dropFirstString(var3)
        String[] var1 = new String[var3.length - 1];
        for (int var2 = 1; var2 < var3.length; ++var2)
        {
            var1[var2 - 1] = var3[var2];
        }
        
        var3 = var1;
        //////////////////////////////
        
        ICommand var5 = (ICommand)super.getCommands().get(var4);
        
        //int var6 = getUsernameIndex(var5, var3);
        int var6;
        
        if (var5 == null)
        {
        	var6 = -1;
        }
        else
        {
            for (int temp = 0; temp < var3.length; ++temp)
            {
                if (var5.isUsernameIndex(temp) && PlayerSelector.matchesMultiplePlayers(var3[temp]))
                {
                	var6 = temp;
                }
            }

            var6 = -1;
        }
		// ////////////////////////////////////

		try {
			if (var5 == null) 
			{
				throw new CommandNotFoundException();
			}

			if (!MinecraftForge.EVENT_BUS.post(new TryCommandEvent(par1ICommandSender, par2Str))) 
			{
				if (var5.canCommandSenderUseCommand(par1ICommandSender) || FML.isModLoaded("fihgu's Permission Mod")) 
				{
					CommandEvent event = new CommandEvent(var5,	par1ICommandSender, var3);
					if (MinecraftForge.EVENT_BUS.post(event)) 
					{
						if (event.exception != null) 
						{
							throw event.exception;
						}
						return;
					}

					if (var6 > -1) 
					{
						EntityPlayerMP[] var7 = PlayerSelector.matchPlayers(par1ICommandSender, var3[var6]);
						String var8 = var3[var6];
						EntityPlayerMP[] var9 = var7;
						int var10 = var7.length;

						for (int var11 = 0; var11 < var10; ++var11) 
						{
							EntityPlayerMP var12 = var9[var11];
							var3[var6] = var12.getEntityName();

							try 
							{
								var5.processCommand(par1ICommandSender, var3);
							} 
							catch (PlayerNotFoundException var14) 
							{
								par1ICommandSender.sendChatToPlayer("\u00a7c"+ par1ICommandSender.translateString(var14.getMessage(),var14.getErrorOjbects()));
							}
						}

						var3[var6] = var8;
					} 
					else 
					{
						var5.processCommand(par1ICommandSender, var3);
					}
				} 
				else 
				{
					par1ICommandSender.sendChatToPlayer("\u00a7cYou do not have permission to use this command.");
				}
			}
        }
        catch (WrongUsageException var15)
        {
            par1ICommandSender.sendChatToPlayer("\u00a7c" + par1ICommandSender.translateString("commands.generic.usage", new Object[] {par1ICommandSender.translateString(var15.getMessage(), var15.getErrorOjbects())}));
        }
        catch (CommandException var16)
        {
            par1ICommandSender.sendChatToPlayer("\u00a7c" + par1ICommandSender.translateString(var16.getMessage(), var16.getErrorOjbects()));
        }
        catch (Throwable var17)
        {
            par1ICommandSender.sendChatToPlayer("\u00a7c" + par1ICommandSender.translateString("commands.generic.exception", new Object[0]));
            var17.printStackTrace();
        }
    }
}
