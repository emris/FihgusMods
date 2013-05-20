package fihgu.protection;

import fihgu.core.shortcut.Forge;
import fihgu.protection.commands.*;
import fihgu.protection.elements.ProtectedBlock;
import fihgu.protection.elements.ProtectedRegion;
import fihgu.protection.tools.EventHandler;
import fihgu.teleport.commands.WarpCommand;
import net.minecraftforge.common.MinecraftForge;

public class CommonProxy 
{
	public void init() 
	{
		Forge.registerEventHandler(new EventHandler());
		
		new LockCommand().register();
		new UnlockCommand().register();
		new ShareCommand().register();
		new UnshareCommand().register();
		
		ProtectedBlock.loadAll();
		ProtectedRegion.loadAll();
	}
	
	public void exit()
	{
		ProtectedBlock.saveAll();
		ProtectedRegion.saveAll();
	}
}
