package fihgu.protection;

import fihgu.core.shortcut.Forge;
import fihgu.protection.commands.LockCommand;
import fihgu.protection.commands.ShareCommand;
import fihgu.protection.commands.UnlockCommand;
import fihgu.protection.commands.UnshareCommand;
import fihgu.protection.elements.ProtectedBlock;
import fihgu.protection.elements.ProtectedRegion;
import fihgu.protection.tools.EventHandler;

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
