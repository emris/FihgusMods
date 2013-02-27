package login;

import java.util.HashMap;

import login.command.*;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy 
{
	public static HashMap<EntityPlayerMP,NetLoginHandler> waitMap = new HashMap<EntityPlayerMP,NetLoginHandler>();
	public void init() 
	{
		MinecraftForge.EVENT_BUS.register(new EventHandler());
		registerCommands();
	}
	private void registerCommands()
	{
		new LoginCommand().register();
	}
}
