package core.functions;

import core.shortcut.Server;

public class Log 
{
	public static void log(String line)
	{
		Server.getServer().logInfo(line);
	}
	
	public static void logCore(String line)
	{
		Server.getServer().logInfo("[fihgu's Core Mod]: " + line);
	}
	
	public static void logWarnning(String line)
	{
		Server.getServer().logWarning(line);
	}
}
