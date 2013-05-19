package fihgu.protection;

import fihgu.core.shortcut.Forge;
import fihgu.protection.commands.LockCommand;
import fihgu.protection.tools.EventHandler;
import fihgu.teleport.commands.WarpCommand;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy 
{
	public void init() 
	{
		Forge.registerEventHandler(new EventHandler());
		new LockCommand().register();
	}
}
