package core.shortcut;

import net.minecraft.command.CommandHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ServerConfigurationManager;

public class Server 
{
	public static MinecraftServer getServer()
	{
		return MinecraftServer.getServer();
	}
	
	public static ServerConfigurationManager getConfigurationManager()
	{
		return getServer().getConfigurationManager();
	}
	
	public static boolean isDedicatedServer()
	{
		return getServer().isDedicatedServer();
	}
	
	public static CommandHandler getCommandHandler()
	{
		return (CommandHandler)getServer().getCommandManager();
	}
}
