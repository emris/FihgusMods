package fihgu.core.transformers.containers;

import java.io.IOException;
import java.net.Socket;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import fihgu.core.events.PlayerLoginEvent;
import fihgu.core.shortcut.Server;

public class FNetLoginHandler extends NetLoginHandler
{
	public FNetLoginHandler(MinecraftServer par1MinecraftServer,
			Socket par2Socket, String par3Str) throws IOException 
	{
		super(par1MinecraftServer, par2Socket, par3Str);
	}
	///Minecraft/src/net/minecraft/network/NetLoginHandler.java
	public void completeConnection(String var1)
	{

		if (var1 != null)
		{
			super.raiseErrorAndDisconnect(var1);
		}
		else
		{
			EntityPlayerMP var2 = Server.getConfigurationManager().createPlayerForUser(super.clientUsername);
			if(!MinecraftForge.EVENT_BUS.post(new PlayerLoginEvent(var2,this)))
			{
				if (var2 != null)
					Server.getConfigurationManager().initializeConnectionToPlayer(super.myTCPConnection, var2);
			}
		}
		super.connectionComplete = true;
	}
}
