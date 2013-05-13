package fihgu.core.functions;

import fihgu.core.shortcut.Server;

public class Log 
{
	public static void log(String line)
	{
		if(Server.getServer() != null)
			Server.getServer().logInfo(line);
	}
	
	public static void logCore(String line)
	{
		if(Server.getServer() != null)
			Server.getServer().logInfo("[fihgu's Core Mod]: " + line);
	}
	
	public static void logWarnning(String line)
	{
		if(Server.getServer() != null)
			Server.getServer().logWarning(line);
	}
}
