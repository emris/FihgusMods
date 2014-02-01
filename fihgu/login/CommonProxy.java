package fihgu.login;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetLoginHandler;
import net.minecraftforge.common.MinecraftForge;
import fihgu.core.functions.Language;
import fihgu.core.functions.McColor;
import fihgu.core.io.SaveFile;
import fihgu.login.commands.LoginCommand;
import fihgu.login.commands.LogoutCommand;
import fihgu.login.commands.RegisterCommand;
import fihgu.login.commands.SetPasswordCommand;
import fihgu.login.tools.EventHandler;

public class CommonProxy 
{
	public static HashMap<EntityPlayerMP,NetLoginHandler> waitMap = new HashMap<EntityPlayerMP,NetLoginHandler>();
	public static HashMap<String,String> loginIP = new HashMap<String,String>();
	public static SaveFile playerData = new SaveFile("playerData.dat", "./fihgu/login/");

	public void init() 
	{
		playerData.load();
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		registerCommands();
	}
	public void exit()
	{
		playerData.save(true);
	}
	private void registerCommands()
	{
		new LoginCommand().register();
		new RegisterCommand().register();
		new LogoutCommand().register();
		new SetPasswordCommand().register();
	}
	public static String getPassword(String playerName)
	{
		for(String line: playerData.data)
		{
			if(line.split(" ")[0].equals(playerName))
				return line.split(" ")[1];
		}
		return null;
	}
	public static void setPassword(EntityPlayerMP player, String password)
	{
		if(password.contains(" "))
		{
			player.addChatMessage(McColor.darkRed + Language.translate("Password must not contain space!"));
			return;
		}

		for(int i = 0; i < playerData.data.size(); i++)
		{
			String line = playerData.data.get(i);
			if(line.split(" ")[0].equals(player.username))
			{
				playerData.data.set(i,player.username + " " + password);
				player.addChatMessage(McColor.green + Language.translate("Your password has been changed."));
				playerData.save(true);
				return;
			}
		}
		playerData.data.add(player.username + " " + password);
		player.addChatMessage(McColor.green + Language.translate("You have been reigstered."));
		LoginCommand.login(player);
		playerData.save(true);
	}
}
